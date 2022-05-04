package com.jsondb;

import com.jsondb.JsonDB;
import java.util.Arrays;

public class AppTest {
    
    public static void main(String[] args) throws Exception {

        String path = "./app.json";

        JsonDB db = new JsonDB(path);

        db.setObj("Student 2", "name", "blaise", "age", 15, "favorite class", "tech class", "hates", "caleb");

        Boolean hasName = db.has("Student 2", "name");
        System.out.println(hasName);

        db.delete("Student 2", "name");

    }   


}
