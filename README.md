# Simple JsonDB

JsonDB is a fast, light-weight JSON key-value storage engine for Java
> [What is key-value storage?](https://redis.com/nosql/key-value-databases/)

## Installation

**Pre-Requisites:**
  * [Java 1.8](https://www.java.com/download/ie_manual.jsp) 
  * [Maven 1.4](https://maven.apache.org/download.cgi)
    * Feel free to use newer versions of these but I <br> can't promise that everything will work as intented


<br>Start off by cloning the repository: 
```bash
git clone https://github.com/AlexStan0/json-db
```

Then cd into the same directory as `pom.xml` and install/update maven dependencies:
```bash
mvn clean install -U
```

Follow up by creating the JAR file:
```bash
mvn package
```

You can use run the JAR using `java -jar /path/to/file.jar` or <br>
you can import the jar into your maven project.

## Usage

### Import
```Java
import com.jsondb.JsonDB;
```

### Instantiation
```Java
JsonDB db = new JsonDB(path)
```

the path for example is where your JSON file is located <br>
for example: `C:/users/<name>/Desktop/javaProject/database.json`<br>

The constructor also checks if the file exists and if it dosent it creates <br>
in the the highest folder and writes `{}` to it

### Set a key
`db.set("key", "value");`

The provided `key` must always have a `value` or else the program will <br>
throw an error and not write the data to the JSON file. <br>

`db.setObj("objKey", "key", "value");

This does the exact same thing as the previous `set` method but instead <br>
it will create a new JSON Object, write the data, and associate the data with <br>
the `objKey` as a nested JSON Object


### Get a key

`db.get(key, objKey)` 

This get method returns an Object, meaning that it can not return <br>
an array. To do that you should use the `db.arrGet(key, objKey)` method

`
 
