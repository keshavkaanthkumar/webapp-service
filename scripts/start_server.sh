#!/bin/bash
cd /home/ubuntu/webapp
sudo ln -s /home/ubuntu/webapp/webapp-0.0.1-SNAPSHOT.jar /etc/init.d/webapp
sudo service webapp start