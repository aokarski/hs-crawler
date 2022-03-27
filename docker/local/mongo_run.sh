#!/bin/bash

docker run -d -p 27017:27017 --name mongo -v mongo-data:/home/adek/developer/temp/local_mongo_docker_storage -e MONGODB_INITDB_ROOT_USERNAME=adek -e MONGODB_INITDB_ROOT_PASSWORD=mongopass mongo:latest
