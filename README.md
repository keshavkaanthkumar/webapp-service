# webapp-service

## Pre-reqeuisites for building and testing
1. Java 1.8
2. MYSQL server
3. Maven
4. Postman/browser
5. IDE STS/Eclipse
demo2

## Build Instructions
1. Open preferred IDE STS/Eclipse 
2. Import cloned project folder
3. Right click on project and choose Run as-> Maven install
4. After successful build right click on the project and select Run as-> Spring boot app in STS.
   If using eclipse select Run as -> on server. Select Tomcat. Confiigure server if not already.
5. Use Postman to test the REST API calls
    Resource paths: User registration: /user            POST
                    Authentication:    /authenticate    POST
                   User ProfileUpdate: /user            PUT
                 User Password Update: /password        PUT
                 View User Profile:    /user            GET

