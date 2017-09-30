# What was done?
# Architecture
It was chosen to use the most simple architectural type - Multi-Layered Architecture (persistence, service and web layers).
From user's standpoint, UI is communicating with back-end over REST and form-submission (in case of login flow (for simplicity))

# Structure
The project is wrapped up with Maven 3. It consists of 4 modules (core (holds domain objects and exceptions), persistence (spring data + h2 database), service (business logic), web (web API and representation)).

## REST endpoints:
- /api/stocks GET to get all of stocks
- /api/stocks POST create a stock
- /api/stocks/{id} PUT update current price of stock by id
- /api/stocks/{id} DELETE delete by id 

Take a look at https://localhost:8443/swagger-ui.html for detailed description of endpoints and try them to use.

# Security
Server supports SSL/TLS with 'want' client authentication on 8443 port. Each user is limited to have only 1 session.
Keystore is located at web/resources and used by Tomcat to establish connection between server and client.
Clients use JSESSIONID over secure cookie with CSRF token in header (against XSRF attacks) to keep them alive.

# Stack
* Java 8
* Spring Boot/MVC/Security/REST/Data
* Lombok
* JUnit + Spring Boot Test
* Pitest
* Thymeleaf
* Footable

# Tests 
### Pitest
Service and web layers were covered by JUnit and Pitest usage. Web layer (line coverage 91% and mutation coverage 85%) and service layer (line coverage 96% and mutation coverage 90%).  

Execute the following command on service and web layer to run tests with Pitest mutation:

mvn org.pitest:pitest-maven:mutationCoverage 

After running all the tests and performing all the mutations, you need to open /service(web)/target/pit-reports/datetimestamp/index.html. Here you can find reports with line and mutation coverage.

# JavaDocs
Code is documented with in-depth details. You can pass through the classes to follow the flow along with reading comments.
REST APi is documented by Swagger annotations and available at https://localhost:8443/swagger-ui.html

# Users
There is one default user:
* user/password1

Take a look at SpringSecurityConfig class to find out how they are defined

# How to build?
mvn clean install

# How to run the application?
Application has embedded Tomcat, so the only one thing you need is to run

java -jar web/target/web-0.0.1-SNAPSHOT.jar  

# Application flow
1. Go to https://localhost:8443 (or http://localhost:8080 - you will be always redirected to 8443 port)
2. Use login and password from Users section to log in
3. In case of successful login, you appear to be at stock dashboard page
4. At dashboard page, you can find table with all of stocks and you can save/edit/delete any stock
5. Click 'Edit Rows' to have 'edit' buttons  appeared.
6. To add new stock, click 'New Row', fill out required fields and click Save. New row will appear in the table.
7. To edit existing stock, click pencil icon on interesting row, change current price (user can change current price of stock only) and clich Save. Stock in the table will be updated automatically.
8. To delete a stock, click trash bin icon on interesting row and confirm you want to delete. Table will be updated automatically.

![Login Form](https://image.ibb.co/fM7RFb/image.png)
![Stock Dashboard](https://image.ibb.co/b2Bsow/image.png)
![Edit stock](https://image.ibb.co/hzba8w/image.png)
![Save stock](https://image.ibb.co/csCnMG/image.png)


# Further improvements
Below is the list of further improvements:

## Security
* mutual client-server authentication over SSL/TLS
* JWT token support to make sure consistency
* HPKP (HTTP Public Key Pinning) to make sure the client is not impersonated by attacker
* forgot password? / remember-me functionality

## REST
* Last-Modified and ETag headers and conditional requests for optimistic concurrency control

## Misc
* implement exception dictionary with mapping exception-to-message-shown-to-user
* introduce resource management system to support i18n, push locales files to UI to show appropriately 
* implement additional tests to reach 100% of mutation and line coverage 

