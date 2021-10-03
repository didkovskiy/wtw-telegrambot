#!/bin/bash

git pull

mvn clean
mvn package

docker-compose stop

export BOT_USERNAME=$1
export BOT_TOKEN=$2
export API_KEY=$3
export BOT_DB_USERNAME='wtwtb_db_user'
export BOT_DB_PASSWORD='wtwtb_db_password'

docker-compose up --build -d