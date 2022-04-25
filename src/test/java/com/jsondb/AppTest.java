package com.jsondb;

import com.jsondb.JsonDB;
import java.util.Arrays;
import org.json.JSONArray;

public class AppTest {
    
    public static void main(String[] args) throws Exception {

        String path = "app.json";

        final JsonDB DB = new JsonDB(path);

        Object[] names = {"John", "Jane", "Joe"};
        Object[] nameList = {"Arr", "Anne", "Alex"};

        DB.set(path, "names", names);
        DB.set(path, "name list", nameList);
        DB.set(path, "name", "Alex");

        Object name = DB.objGet(path, "name");
        System.out.println(name);

        DB.set(path, "name", "John");

        Object name1 = DB.objGet(path, "name");
        System.out.println(name1);

        Object[] nameListArr = DB.arrGet(path, "name list");
    }   

}
