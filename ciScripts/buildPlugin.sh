#!/bin/bash
git add .
git stash
git checkout master
git pull origin master
./gradlew clean buildPlugin
git add .
git stash
git stash clear
