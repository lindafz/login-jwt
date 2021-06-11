# Sring Data and JWT Login

## Spring Data and JWT login application

This repository contains the following contents:
 1. Spring MVC
 2. Spring JPA Mapping
 3. Spring security
 4. Springfox Swagger UI for documentation
 5. JWT  Login
 6. The default database is MySql

## Instruction for Installation
* Download source code from GitHub
* Create login_hwt schema in mysql
* Update application.properties to your username and password
* MVN clean compile
* Run the SpringBoot or LoginApplication Application from either IntelliJ or Eclipse
* Table role and user table will be created and populated automatically

## Unit Test and CI Pipeline
* The project is configured using circleCI by default

## Swagger UI:
* ![] (images/Swagger-UI.png)
* http://localhost:9090/swagger-ui.html#

## Request URL
* http://localhost:9090/api/login  (POST)
* http://localhost:9090/api/user   (GET)
* http://localhost:9090/api/user   (POST)
* http://localhost:9090/api/user   (PUT)
* http://localhost:9090/api/user/{userID}  (DELETE)
* http://localhost:9090/api/users   (GET)

## Port Configuration
* application.properties - server.port=9090

## Known Issues
* The code is for demonstration puporse. Some code may not follow the best practice
* No Java comments
