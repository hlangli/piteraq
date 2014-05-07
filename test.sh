#!/bin/bash

MSG="Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
MSG_MD5="APpcifPIi4G/1eghsDFlaa8="
MSG_SHA1="Ga+ipKN0Yse5QKbExhNj1Jw6NfQ="
MSG_SHA_256="LHw9XyRPGkAGmjIiQhXgz5tCSFyZ2A81fXbwBjWcehg="
MSG_SHA_512="APQdkryfwRV6DROH5n89CJO3D3A509RtgRW1B51FrWARWTmMecKBaB4toJv32fjCO0HRoKPFtSin8nNZM6Q1MZQ="
MSG_Keccak_224="ALdWY+0XbfVxy2Q/Mgpli9KwawVcDJpak0czHx4="
MSG_Keccak_256="AIvgzy3zPRfKHi2asCyL+7BR9E5qu4DD4Gq1WqNmG279"
MSG_Keccak_384="VqWOS1MgmvpbEy8rb1k2NDkxLIthwH9k2TzUlfB47yWmEKFTQDA7n3ULe/9XLbu6"
MSG_Keccak_512="ANRPBqT85mY/9ThRUYY8QFRvNaWcaJFpmXnHgnLmKDV80Y8Y/vEXj3RNb51Q+6u/gM1mppMQ7xJ3e2Ch5WDTeQM="

DEBUG=false
if [ "$1" == "-d" ]; then
	DEBUG=true
fi

function debug() {
	if [ ${DEBUG} == true ]; then
		echo $* >&2
	fi
}

function piteraq() {
	EXEC="java dk.langli.piteraq.Piteraq $*"
	# debug "${EXEC}"
	$EXEC
}

function escape() {
	echo "$*"|sed "s/\"/\'/g"
}

function json() {
	echo $1|json_pp -json_opt pretty,canonical
}

function hash() {
	DIGEST=$1
	CMP_HASH_VAR=MSG_${DIGEST}
	CMP_HASH=${!CMP_HASH_VAR}
	echo -n "${DIGEST/_/-} ${CMP_HASH}"
	HASH="`echo -n "${MSG}"|piteraq -d ${DIGEST/_/-}`"
	HASH="`jsonval h \"${HASH}\"`"
	VERIFY=FAIL
	if [ "${CMP_HASH}" == "${HASH}" ]; then
		VERIFY=OK
	fi
	echo " ${VERIFY}"
}

function verify() {
	MESSAGE=$1
	SIGNATURE=$2
	INFO=$3
	OK="${4:-OK}"
	FAIL="${5:-FAIL}"
	echo -n "${INFO}"
	echo -n "${MESSAGE}"|piteraq -v "${SIGNATURE}" > /dev/null
	echo " `exitcode $? ${OK} ${FAIL}`"
}

function signature() {
	PRIVATE_KEY="$1"
	PUBLIC_KEY="$2"
	DIGEST="$3"
	# Get hash
	HASH="`echo -n "${MSG}"|piteraq -d ${DIGEST}`"
	debug "Hash `json ${HASH}`"
	HASH="`escape ${HASH}`"
	# Get a false hash
	NONHASH="`echo -n "${MSG}A"|piteraq -d ${DIGEST}`"
	NONHASH="`escape ${NONHASH}`"
	# Generate signature
	SIGNATURE="`echo -n \"${HASH}\"|piteraq -s \"${PRIVATE_KEY}\"`"
	debug "Signature `json ${SIGNATURE}`"
	SIGNATURE="`escape ${SIGNATURE}`"
	# Verify signature
	verify "${HASH}" "${SIGNATURE}" "${DIGEST} SIGNATURE VERIFICATION"
	# Verify signature with wrong input (is supposed to fail)
	verify "${NONHASH}" "${SIGNATURE}" "${DIGEST} SIGNATURE NONVERIFICATION" FAIL OK
}

function exitcode() {
	EXIT_CODE=`expr $1 + 2`
	echo ${!EXIT_CODE}
}

function jsonval {
	echo $2*|sed "s/^.*\"h\":\"\([^\"]*\).*\$/\1/g"
}

pushd "`dirname $0`" > /dev/null
	IFS=" "
	if [ ! -d target/dependency ]; then
		echo -n "Compiling..."
		COMPILE="`mvn clean compile dependency:copy-dependencies`"
		EXIT_CODE=$?
		if [ ${EXIT_CODE} != 0 ]; then
			echo " FAIL"
			echo ""
			echo "${COMPILE}"
			exit ${EXIT_CODE}
		else
			echo " OK"
		fi
	fi
	export CLASSPATH=target/classes
	for file in target/dependency/*.jar; do
		export CLASSPATH=$CLASSPATH:$file
	done

	# Hash input with SHA1
	hash MD5
	hash SHA1
	hash SHA_256
	hash SHA_512
	hash Keccak_224
	hash Keccak_256
	hash Keccak_384
	hash Keccak_512

	# Generate private key
	PRIVATE_KEY="`piteraq -g 1024`"
	debug "PrivateKey `json ${PRIVATE_KEY}`"
	PRIVATE_KEY="`escape ${PRIVATE_KEY}`"
	# Get public key from private key
	PUBLIC_KEY="`piteraq -p \"${PRIVATE_KEY}\"`"
	debug "PublicKey `json ${PUBLIC_KEY}`"
	PUBLIC_KEY="`escape ${PUBLIC_KEY}`"

	signature "${PRIVATE_KEY}" "${PUBLIC_KEY}" MD5
	signature "${PRIVATE_KEY}" "${PUBLIC_KEY}" SHA1
	signature "${PRIVATE_KEY}" "${PUBLIC_KEY}" SHA-256
	signature "${PRIVATE_KEY}" "${PUBLIC_KEY}" SHA-512
	signature "${PRIVATE_KEY}" "${PUBLIC_KEY}" Keccak-224
	signature "${PRIVATE_KEY}" "${PUBLIC_KEY}" Keccak-256
	signature "${PRIVATE_KEY}" "${PUBLIC_KEY}" Keccak-384
	signature "${PRIVATE_KEY}" "${PUBLIC_KEY}" Keccak-512

	# Blinding
	ALICE="`piteraq -g 1024`"
	ALICE="`escape ${ALICE}`"
	ALICE_PUB="`piteraq -p \"${ALICE}\"`"
	BOB="`piteraq -g 1024`"
	BOB="`escape ${BOB}`"
	BOB_PUB="`piteraq -p \"${BOB}\"`"

	# New blinding factor
	FACTOR="`piteraq -r \"${ALICE_PUB}\"`"
	debug "BlindingFactor ${FACTOR}"
	# Get hash
	HASH="`echo -n "${MSG}"|piteraq -d ${DIGEST}`"
	debug "Hash `json ${HASH}`"
	HASH="`escape ${HASH}`"
	# Blind hash
	BLIND="`echo \"${HASH}\"|piteraq -b \"${ALICE_PUB}\" \"${FACTOR}\"`"
	debug "Blind ${BLIND}"
	# Sign blind
	SIGNED_BLIND="`echo \"${BLIND}\"|piteraq -s \"${BOB}\"`"
	debug "SignedBlind `json ${SIGNED_BLIND}`"
	SIGNED_BLIND="`escape ${SIGNED_BLIND}`"
	verify "${BLIND}" "${SIGNED_BLIND}" "SIGNED BLIND VERIFICATION"
	BLIND_SIGNATURE="`echo \"${SIGNED_BLIND}\"|piteraq -s \"${ALICE}\"`"
	debug "BlindSignature `json ${BLIND_SIGNATURE}`"
	BLIND_SIGNATURE="`escape ${BLIND_SIGNATURE}`"
	verify "${BLIND}" "${BLIND_SIGNATURE}" "BLIND SIGNATURE VERIFICATION"
	UNBLINDED_SIGNATURE="`echo \"${BLIND_SIGNATURE}\"|piteraq -u \"${ALICE_PUB}\" \"${FACTOR}\" \"${HASH}\"`"
	debug "UnblindedSignature `json ${UNBLINDED_SIGNATURE}`"
	UNBLINDED_SIGNATURE="`escape ${UNBLINDED_SIGNATURE}`"
	# Verify unblinded signature
	verify "${HASH}" "${UNBLINDED_SIGNATURE}" "UNBLINDED SIGNATURE VERIFICATION"
	
popd > /dev/null

