# Logging-reporting 

[Link](https://github.com/taqc-java/logging-and-reporting-Lokankara)

### Requirements
Utilize SLF4J with Log4j2 for logging and Allure for reporting

### Task #1
Select an appropriate log level and:
- [x] Log the browser name and version, platform name and search strategy.
- [x] Log the start and end of each test execution, including timestamps and method names.
- [x] Log any errors or exceptions that occur.
- [x] Log details of actions and elements being interacted with, such as opening a URL, clicking a button, entering text, etc.

### Task #2
- [x] Use Rolling File Appenders for log messages with ERROR and CRITICAL levels. The file should be named error_level.log and should not exceed 100 KB, with a maximum of 7 backup files.
- [x] Write all other log messages (excluding ERROR and CRITICAL levels) to a file named with the current date and time. We want all log messages issued on the same day to be in the same log file.

### Task # 3
- [x] Replace comments in code with corresponding log messages

### Task # 4
- [x] Generate test reports using Allure. Ensure that each report includes the title and severity of each test. Add screenshots for any failed tests.

`mvn clean surefire-report:report`

`mvn clean install -X`
