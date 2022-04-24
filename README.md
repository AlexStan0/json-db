# Simple JsonDB

JsonDB is a fast, light-weight JSON key-value storage engine for Java
> [What is key-value storage?](https://redis.com/nosql/key-value-databases/)

## Installation

**Pre-Requisites:**
  * [Java 1.8](https://www.java.com/download/ie_manual.jsp) 
  * [Maven 1.4](https://maven.apache.org/download.cgi)
    * Feel free to use newer versions of these but I <br> can't promise that everything will work as intented


<br>Start off by cloning the repository: 
```
git clone https://github.com/AlexStan0/json-db
```

Then cd into the same directory as `pom.xml` and install/update maven dependencies:
```
mvn clean install -U
```

Follow up by creating the JAR file:
```console
mvn package
```

You can use run the JAR using `java -jar /path/to/file.jar` or <br>
you can import the jar into your maven project.

## The Problem



