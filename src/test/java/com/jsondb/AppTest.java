package com.jsondb;

import com.jsondb.JsonDB;
import java.util.Arrays;

public class AppTest {
    
    public static void main(String[] args) throws Exception {

        String path = "./app.json";

        final JsonDB DB = new JsonDB(path);

        DB.setObj("Student 1", "name", "Alex", "age", 16, "gender", "male");

        Object age = DB.get("Student 1", "age");
        System.out.println((long)age == 16);

        Boolean has = DB.has("Student 1", "name");
        System.out.println(has);
    }   


}
