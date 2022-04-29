package com.jsondb;

import com.jsondb.JsonDB;
import java.util.Arrays;
import java.io.FileReader;
import org.json.simple.parser.JSONParser;
import org.json.JSONArray;
import org.json.simple.JSONObject;

public class AppTest {
    
    public static void main(String[] args) throws Exception {

        String path = "app.json";

        final JsonDB DB = new JsonDB(path);

        DB.set("name", "Alex", "age", 20);
    
        DB.setObj("Alex", "age", 15, "like cs", true);

        DB.setObj("Alex", "age", 16);

        Object name = DB.objGet("Alex", "age");
        System.out.println(name == null);

    }   


}
