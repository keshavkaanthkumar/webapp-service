#!/bin/bash
cd /home/ubuntu/webapp
sudo chown tomcat:tomcat webapp-0.0.1-SNAPSHOT.jar
sudo chmod 500 webapp-0.0.1-SNAPSHOT.jar
sudo systemctl daemon-reload
sudo systemctl enable tomcat.service
sudo systemctl start tomcat.service
sudo systemctl status tomcat