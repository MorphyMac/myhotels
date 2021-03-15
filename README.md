## rabbit-mq for config-server support
docker run -d -p 15672:15672 -p 5672:5672 -p 5671:5671 --hostname my-rabbitmq --name my-rabbitmq-container rabbitmq:3-management


## Sonar
docker container run -d -p 9000:9000 --name sonarserver sonarqube

mvn clean install

mvn sonar:sonar