package com.jsondb;

import com.jsondb.JsonDB;
import java.util.Arrays;
import org.json.JSONArray;

public class AppTest {
    
    public static void main(String[] args) throws Exception {

        String path = "app.json";

        JsonDB jsonDB = new JsonDB(path);

        Object[] names = {"John", "Jane", "Joe"};

        jsonDB.set(path, "name", "Alice", "age", "25");

        Object name = JsonDB.objGet(path, "name");
        System.out.println(name);

        JsonDB.set(path, "names", names);
        Object[] nameArr = JsonDB.arrGet(path, "names");

        System.out.println(Arrays.toString(nameArr));
    
    }

}
