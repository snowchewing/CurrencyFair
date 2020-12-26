# CurrencyFair

## General info

* Java version: 11
* Framework used: Spring boot
* Build tool: Maven
* Frontend library: React

## Deployment pre-requisite

* environment variable 'JAVA_HOME' is set
* npm is installed

## Deployment and testing procedure

* Execute the deployment script
  * Windows: [project-root]\deploy\deploy.bat
  * Linux: [project-root]/deploy/deploy.sh
* Open browser and go to below links for testing:
  * main page: http://localhost:8080
  * swagger UI: http://localhost:8080/swagger-ui.html
    * alternate to swagger: directly post to http://localhost:8080/api/trade
