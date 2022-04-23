package com.jsondb;

public class Test {
    
    public static void main(String[] args) throws Exception {

        String path = "app.json";

        JsonDB db = new JsonDB(path);

        db.put(path, "name", "Alex");

        // db.put(path, "age", 15);
    }

}
