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

You can use run the JAR using `java -jar /path/to/json-db-1.0.jar` or <br>
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

The path for example is where your JSON file is located. <br>
For example: `C:/users/<name>/Desktop/javaProject/database.json`<br>

The constructor also checks if the file exists and if the file dosen't exist <br>
it creates it in the the highest folder and writes `{}` to it

### Set a key
`db.set(key, value);`

The provided `key` must always have a `value` or else the program will <br>
throw an error and not write the data to the JSON file. <br>

`db.setObj(objKey, key, value)`

This does the exact same thing as the previous `set` method but instead <br>
it will create a new JSON Object, write the data, and associate the data with <br>
the `objKey` as a nested JSON Object


### Get a key

`db.get(key, objKey)` 

This get method returns an Object, meaning that it can not return <br>
an array. To do that you should use the `db.arrGet(key, objKey)` method

`db.arrGet(key, objKey)`

This method is the same as the `get` method but returns an <br>
Object array instead of a simple Object type. 

### Check for a key 

`db.has(key, objKey)`

If you want to check to see if your JSON file has a certain key you <br>
can use the `db.has()` method to check for it. 

### Delete a key 

`db.delete(key, objKey)`

If a certain key needs to be deleted you can delete it using the `db.delete()` method. <br>
This method scans the JSON file to make sure the key exists and the deletes it. The `objKey` parameter <br>
is used in case the `key` parameter is a nested JSON Object. 

### Delete everything

If you no longer need to use the database and don't want the IRS to find your data then this method is for you. <br>
When you call `db.delete()` it creates a new JSON Object and overwrites the old one in the JSON file. Once this method <br>
has been called all data previously written to the JSON file will be lost.

### NOTE: <br>
As you may have noticed both of these methods have a second parameter `objKey`.<br>
This parameter is 100% optional and is ment to be use when the `key` parameter references a nested JSON Object <br>

**Example:** <br>
  Say you had this JSON file 
  ```JSON
  {
    "game-name": "my-game-name",
    "player 1": {
        "username": "KingSlayer123",
        "score": 274, 
        "health": 95,
        "inventory": ["sword", "potion", "bow", "arrows"]
    }
  }
  ```

Say you wanted to check for the existance of `game-name`, you could call `db.has("game-name")` <br>
But what if you wanted to check for the existance of `score` in the nested JSON Object `player 1`? <br>
This is where the 'objKey' parameter comes in. If the 'key' paramter references a nested JSON Object it fetch or check <br>
for the existance of `objKey` in the nested JSON Object.
