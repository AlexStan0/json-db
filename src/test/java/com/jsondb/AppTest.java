package com.jsondb;

import com.jsondb.JsonDB;
import java.util.Arrays;

public class AppTest {

    public static String path = "app.json";

    public static void main(String[] args) throws Exception {

        //run all of the testing functions
        testSet();
        testSetObj();
        testGet();
        testArrGet();
        testDelete();
        testDeleteAll();


    }   

    public static void testSet() throws Exception {

        JsonDB db = new JsonDB(path);

        //Write to the file for the first time and see if any of the variables can be fetches
        db.set("name", "John");
        Object databaseName = db.get("name");
        System.out.println("\nExpected name John, returned name" + databaseName);

        //Overwrite one of the values and check to see if the change has been recorded
        db.set("name", "Joe");
        Object overwrittenName = db.get("name");
        System.out.println("Expected name Joe, returned name = " + overwrittenName);

        //Test multiple input type inputs
        db.set("Integer", 10, "String", "hello", "Boolean", true, "Double", 21.5, "Float", 34.2f);
        Object num = db.get("Integer");
        Object word = db.get("String");
        Object trueOrFalse = db.get("Boolean");

        //Exception handling test
        
    }

    public static void testSetObj() throws Exception {

        JsonDB db = new JsonDB(path);

    }

    public static void testGet()throws Exception {

        JsonDB db = new JsonDB(path);

    }

    public static void testArrGet() throws Exception {

        JsonDB db = new JsonDB(path);

    }

    public static void testDelete() throws Exception {

        JsonDB db = new JsonDB(path);

    }

    public static void testDeleteAll() throws Exception {

        JsonDB db = new JsonDB(path);

    }

}
