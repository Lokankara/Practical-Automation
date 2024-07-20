**Task #1**

- [x] Create a class UserDao to interact with the database. 
- [x] Within this class, implement a method activateUserById(int id). 
- [x] This method should set the status to true for the user with the specified id.

**Task #2**

Develop tests to verify user authorization functionality:

1. Test to ensure an unapproved user cannot log in to the application:

- [x] Register a user with a random email and password using REST-assured.
- [x] Attempt to log in using these credentials via Selenium.
 
2. Test to ensure an approved user can log in to the application:

- [x] Approve the user account by invoking the activateUserById(int id) method.
- [x] Log in with created user's credentials using Selenium (utilize the checkSuccessfulLogin method from the SomeTest class).

**Task #3**

Verify if a new comment is displayed correctly:

- [x] Create a unique comment for some clubs using REST-assured.
- [x] Confirm the display of this comment via Selenium (use the checkCommentExist method from the SomeTest class).

The endpoints necessary for these tests are available at: [Swagger API Documentation](http://speak-ukrainian.eastus2.cloudapp.azure.com/dev/swagger-ui/index.html#/).

You may refer to ideas from the UserTest class for implementation.
