#!/bin/bash

docker run -e MYSQL_ROOT_PASSWORD=supersecret -e MYSQL_DATABASE=starwars -p 3305:3306 -d --name mysql mysql:5.7


