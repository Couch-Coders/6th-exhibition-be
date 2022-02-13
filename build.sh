#!/bin/sh
cd ../couch-exhibition
npm install
npm run build
cd ..
cp -r couch-exhibition/build/* src/main/resources/static/