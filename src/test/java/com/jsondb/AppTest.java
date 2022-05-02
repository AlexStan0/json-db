package com.jsondb;

import com.jsondb.JsonDB;
import java.util.Arrays;

public class AppTest {
    
    public static void main(String[] args) throws Exception {

        String path = "app.json";

        final JsonDB DB = new JsonDB(path);

        DB.delete("Alex");

    }   


}
