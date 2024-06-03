## Practical-Automation

[![Java CI with Maven](https://github.com/Lokankara/Practical-Automation/actions/workflows/maven.yml/badge.svg)](https://github.com/Lokankara/Practical-Automation/actions/workflows/maven.yml)

Build the project with Maven Tool

`mvn -B package --file pom.xml`

Clean, install, and generate report

`mvn clean install site -P test`

Clean, install with details

`mvn clean install -X`

Clean, install skip Test

`mvn clean install -DskipTests`