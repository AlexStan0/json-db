package com.jsondb;

import com.jsondb.JsonDB;
import java.util.Arrays;

public class AppTest {
    
    public static void main(String[] args) throws Exception {

        String path = "./app.json";

        JsonDB db = new JsonDB(path);

        db.setObj("User 1", "age", "30");

    }   


}
