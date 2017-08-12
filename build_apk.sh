#!/usr/bin/env bash

echo
echo "--- Build APK"
gradleAssemble="$(./gradlew assembleRelease)"
checkBuildFailed=$(echo $gradleAssemble | grep "BUILD FAILED")
if [ -n "$checkBuildFailed" ]; then
	exit 1
fi

pathToVersion="./app/build.gradle"

versionCode=$(grep -oP 'versionCode \K.*' $pathToVersion)
versionName=$(grep -oP 'versionName "\K.*"' $pathToVersion | grep -o '[^"].*[^"]')
apkName="CalculIt-$versionCode-$versionName.apk"

incAppVersion=$1
if [ "$incAppVersion" == "incAppVersion" ]; then
	echo 
	echo "--- Increment App Version"
	echo -n "New version code ? (current: $versionCode) "
	read newVersionCode
	sed -i "s/versionCode ${versionCode}/versionCode ${newVersionCode}/g" $pathToVersion

	echo -n "New version name ? (current: $versionName) "
	read newVersionName
	sed -i "s/versionName \"${versionName}\"/versionName \"${newVersionName}\"/g" $pathToVersion

	apkName="CalculIt-$newVersionCode-$newVersionName.apk"

	echo
	echo "--- Re-build the app"
	gradleAssemble="$(./gradlew assembleRelease)"
	checkBuildFailed=$(echo $gradleAssemble | grep "BUILD FAILED")
	if [ -n "$checkBuildFailed" ]; then
		exit 1
	fi
fi

echo
echo "--- Remove previous app"
rm -f *.apk

echo
echo "--- Move the APK to ./$apkName"
cp ./app/build/outputs/apk/app-release.apk ./$apkName

echo
