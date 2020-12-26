#!/bin/sh

currDir=$(dirname "$0")
baseDir="$currDir/.."
springStaticDir="$baseDir/src/main/resources/static"

# build frontend
cd "$baseDir/react-spa"
npm run build

# serve react app with spring
rm -rf "$springStaticDir"
mkdir -p "$springStaticDir"
mv build/* "$springStaticDir"
rm -rf build

# run spring boot app
cd "$baseDir"
./mvnw spring-boot:run
