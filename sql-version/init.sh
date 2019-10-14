#!/bin/bash

mvn -f starwars/ clean install

docker run -e MYSQL_ROOT_PASSWORD=supersecret -e MYSQL_DATABASE=starwars -p 3305:3306 -d --name mysql mysql:5.7

sleep 4.0

java -jar starwars/target/starwars-0.0.1-SNAPSHOT.jar
