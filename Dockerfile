FROM oneclick:admin
COPY plugins.txt /usr/share/jenkins/plugins.txt
#RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt
#RUN /usr/local/bin/install-plugins.sh  maven:2.13  git:3.0.0
#RUN /usr/local/bin/install-plugins.sh  /usr/share/jenkins/plugins.txt
USER root
#RUN chown -R jenkins:jenkins /var/jenkins_home/.ssh
#COPY id_rsa.pub /var/jenkins_home/.ssh/
#RUN chown -R jenkins:jenkins /var/jenkins_home/.ssh

#RUN mkdir /var/jenkins_home/groovyscripts
COPY rar.groovy /var/jenkins_home/workspace/METAJOB/rar.groovy
RUN chown -R jenkins:jenkins /var/jenkins_home/workspace/METAJOB
COPY permission.groovy /usr/share/jenkins/permission.groovy
COPY jenkins-cli.jar /usr/share/jenkins/jenkins-cli.jar
#RUN java -jar /usr/share/jenkins/jenkins-cli.jar -s http://localhost:8080 groovy /usr/share/jenkins/permission.groovy --username admin --password password
