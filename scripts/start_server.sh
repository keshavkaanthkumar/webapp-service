#!/bin/bash
cd /home/ubuntu/webapp
sudo chown tomcat webapp-0.0.1-SNAPSHOT.jar
sudo chmod 500 webapp-0.0.1-SNAPSHOT.jar
cd /etc/systemd/system
sudo touch tomcat.service
sudo chmod 777 tomcat.service
echo '[Unit]' >> tomcat.service
echo 'Description=Apache Tomcat Web Application Container' >> tomcat.service
echo 'After=syslog.target network.target' >> tomcat.service
echo '[Service]' >> tomcat.service
echo 'Type=forking' >> tomcat.service
echo 'Environment=JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64' >> tomcat.service
echo 'Environment=\"JAVA_OPTS=-Djava.awt.headless=true -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv4Stack=true -Djava.net.preferIPv4Addresses=true\"' >> tomcat.service
echo 'ExecStart=/home/ubuntu/webapp/webapp-0.0.1-SNAPSHOT.jar' >> tomcat.service
echo 'ExecStop=/bin/kill -15 $MAINPID' >> tomcat.service
echo 'User=tomcat' >> tomcat.service
echo 'Group=tomcat' >> tomcat.service
echo 'UMask=0007' >> tomcat.service
echo 'RestartSec=10' >> tomcat.service
echo 'Restart=always' >> tomcat.service
echo '[Install]' >> tomcat.service
echo 'WantedBy=multi-user.target' >> tomcat.service
sudo systemctl daemon-reload
sudo systemctl enable tomcat.service
sudo systemctl start tomcat.service
sudo systemctl status tomcat