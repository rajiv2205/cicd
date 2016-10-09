#! /bin/bash
CONTAINER_NAME=$1
USERNAME=$2
PASSWORD=$3
#docker start agitated_davinci
#docker build -t oneclick:adminv1 .
docker run -p 8082:8080 -p 50020:50000 --name $CONTAINER_NAME -d oneclick:adminv1
CONTAINER_IP=`docker inspect --format '{{ .NetworkSettings.IPAddress }}' $CONTAINER_NAME`
sleep 60
java -jar jenkins-cli.jar -s http://$CONTAINER_IP:8080 groovy permission.groovy $USERNAME $PASSWORD --username admin --password admin@123
echo "*****Admin User created*****"
java -jar jenkins-cli.jar -s http://$CONTAINER_IP:8080 create-job METAJOB < meta.xml.erb --username admin --password admin@123
java -jar jenkins-cli.jar -s http://$CONTAINER_IP:8080 build METAJOB --username admin --password admin@123
echo "jobs created"

