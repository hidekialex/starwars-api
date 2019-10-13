mvn -f starwars/ clean install

docker run -p 8081:8000 -d --name dynamo amazon/dynamodb-local

aws --endpoint-url=http://localhost:8081 dynamodb create-table --cli-input-json file://create-table.json

java -jar starwars/target/starwars-0.0.1-SNAPSHOT.jar

