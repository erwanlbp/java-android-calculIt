#!/usr/bin/env bash

echo
echo "--- Build APK"
./gradlew assemble

versionCode=$(grep -oP 'versionCode \K.*' ./app/build.gradle)
versionName=$(grep -oP 'versionName "\K.*"' ./app/build.gradle | grep -o '[^"].*[^"]')
apkName="CalculIt-$versionCode-$versionName.apk"

echo
echo "--- Move the APK to ./$apkName"
cp ./app/build/outputs/apk/app-release-unsigned.apk ./$apkName

echo