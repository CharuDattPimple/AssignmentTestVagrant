# Selenium-Rest Assured Weather Comparator

# Tools and technology used

Language: Java 

Version : 1.8

Testing framework: Cucumber

Automation tool: Selenium webdriver

API automation : Rest Assured

Build tool: maven

Logging: log4j

Reporting: cucumber reporting

# Execution
hit the command on terminal to start the execution "Mvn clean install "-Dcucumber.options=--tags @AllTest" "

# Reporting

running "mvn clean test" will generate the Cucumber report file target/cucumber.json

running "mvn verify -DskipTests" will generate the cucumber-report-html based on the cucumber.json

running "mvn clean verify" will do all together


Note: Tested with chromeDriver v90.


