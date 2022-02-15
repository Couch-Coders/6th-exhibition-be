#!/bin/sh
# shellcheck disable=SC2164
cd ../couch-exhibition
npm install
npm run build
# shellcheck disable=SC2103
cd ..
cp -r couch-exhibition/build/* src/main/resources/