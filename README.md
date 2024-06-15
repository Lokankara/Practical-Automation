## Task 1

[Links](https://github.com/ita-classroom-projects/taqc-cross-browser-testing-Lokankara)

1. The test methods test the functionality of inserting comments.
2. Add the IS_HEADLESS environment variable. If the variable IS_HEADLESS is true, then the tests should be executed without a GUI. Print to the console a message about the steps performed.
3. Add the PASSWORD environment variable. Replace the hardcoded password with the value of the PASSWORD variable.
4. Read the browser name from the BROWSER variable. If the variable is undefined, use the default value.
5. Read the site link from the URL variable. If the variable is undefined, use the default value.
6. Update the ClubTest class so that the tests run in parallel. Make changes to the @Before* and @After* annotated methods.

## Task 2

1. Move the following functionality to separate methods
 - sign in
 - making a comment,
 - comment checks,
 - logout.
  
2. Update test methods.

## Task 3 (additional, optional)

1. Both tests are of the same type, which differ only in test data. Highlight the method with the data. Parameterize the test method.
2. Transfer the creation of WebDriver objects to the method annotated @Before*.  Implement the getWebDriver(...) method, which will return the required driver during parallel execution. The @AfterAll method should close all browsers.

> Record a short video (3-5 minutes) demonstrating your code functionality and test execution, then post it on your YouTube channel.
The report should include a link to GitHub and a link to the video.
