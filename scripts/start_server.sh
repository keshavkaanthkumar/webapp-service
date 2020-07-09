#!/bin/bash
sudo /opt/aws/amazon-cloudwatch-agent/bin/amazon-cloudwatch-agent-ctl \
    -a fetch-config \
    -m ec2 \
    -c file:/opt/amazon-cloudwatch-agent.json \
    -s
cd /home/ubuntu/webapp
chmod +x webapp-0.0.1-SNAPSHOT.jar
sudo systemctl enable webapp.service
sudo systemctl start webapp.service