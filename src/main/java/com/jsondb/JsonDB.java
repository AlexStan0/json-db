package com.jsondb;

//import dependencies 
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class JsonDB {

    public JsonDB(String path) throws Exception {
        
        if(path.equals("")){
            throw new Error("Missing path argument");
        }

        //create new file using provided path
        File file = new File(path);

        //check to make sure the file exists
        if(!file.exists() || file.isDirectory()){
            //if file does not exist it creates a new one in the current directory
            System.out.println("File does not exist");
            file.createNewFile();

            //create a new JSONObject and write it
            JSONObject jsonObject = new JSONObject();
            FileWriter writer = new FileWriter(path);
            writer.write(jsonObject.toString());
            writer.close();

        }

        //check to make sure the file is readable
        if(!file.canRead() || !file.canWrite()){
            throw new Error("File is not accessible, check permissions");
        }

    } //end constructor

    /**
     * Writes JSON data to JSON file 
     * @param path path for the JSON file
     * @param varargs data that the user wants written to the JSON file
     * @throws Exception 
     */
    public void put(String path, Object... varargs) throws Exception {

        //checks to make sure varargs 'varargs' are in key, value format
        if(varargs.length %2 != 0){
            throw new Error("varargs do not follow (key, value) format");
        }

        //create new JSON parser and FileReader
        JSONParser parser = new JSONParser();
        FileReader jsonFile = new FileReader(path);

        try{

            //Reads the data in JSON file and assigns to Object jsonData
            Object jsonData = parser.parse(jsonFile);

            //assigns data to a JSONObject object
            JSONObject jsonDataObj = (JSONObject) jsonData;

            //Loops through varargs to check the provided
            for(int i = 1; i < varargs.length; i+=2){
                
                //checks to see if 'varargs[i]' is an array
                if(varargs[i].getClass().isArray()){
                    //if value is an array, convert to JSONArray
                    JSONArray jsonArr = (JSONArray) varargs[i];
                    jsonDataObj.put(varargs[i-1], jsonArr);
                    
                } else {
                    //if value is not an array, assign to JSONObject
                    jsonDataObj.put(varargs[i-1], varargs[i]);
                }

            } //end for-loop

            //create new FileWriter and write to file
            FileWriter writer = new FileWriter(path);
            writer.write(jsonDataObj.toString());
            writer.close();

        } catch (Exception e) {

            //if there is an error, print the error
            e.printStackTrace();

        }

    } //end put()



} //end JsonDB
