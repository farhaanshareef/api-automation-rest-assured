# Don't forget to give a ⭐ to make the project popular.

# API Automation using Rest Assured

This project is designed for automating API tests using Java, Rest Assured, TestNG, and Extent Reports. The tests cover various operations such as creating, updating, patching, deleting, and retrieving users from the API.

## Prerequisites

Before you can run the tests, ensure you have the following installed:

1. **Java Development Kit (JDK)**
    - Download and install the latest JDK from [Oracle](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).

2. **Download JAVA IDE**
   - Install any Java IDE(Aqua, Intellij) to run the Automation repo.

## Technologies Used
### Java: 
Programming language for writing tests.

### Rest Assured: 
A Java library for testing RESTful APIs.

### TestNG: 
A testing framework for running the tests.

### Extent Reports: 
A reporting library for generating HTML reports.

## Project Structure
src/test/java/tests:
Contains all the test cases.
reports:
Contain all the reports.

testng.xml: TestNG suite configuration file.

pom.xml: Maven configuration with necessary dependencies for Appium, TestNG, and the Flutter driver.
## Setup Instructions
1. Clone the Repository:

2. Install Dependencies: 

Make sure you have Maven installed. Run the following command to install the required dependencies:
   mvn install

3. Configure Base URL: 

Update the config.properties file in the utils/ directory with the appropriate base URL of the API you want to test.

4. Run Tests: 
You can run the tests using Maven:

   mvn test

5. View Reports: 
After running the tests, you can find the generated Extent Reports in the reports/ directory.

## Test Cases
TestCreateUser: Tests the creation of a user.

TestUpdateUser: Tests the update of user details.

TestPatchUser: Tests partial updates of user details.

TestDeleteUser: Tests the deletion of a user.

TestGetUser: Tests retrieval of users.