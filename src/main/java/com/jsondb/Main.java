package com.jsondb;


//import maven dependencies 
import org.json.JSONException;
import org.json.JSONObject;
import com.jsondb.JsonDB;

//import java dependencies
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Main {
    
    public static void main(String[] args) throws Exception {

        String path = "app.json";

        JsonDB db = new JsonDB(path);

        db.put(path);

    }


}
