package com.jsondb;

//import dependencies 
import java.io.File;
import java.util.Arrays;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class JsonDB {

    /**
     * Creates DB and checks JSON file integrity
     * @param path Path to file with JSON data
     * @throws Exception
     */
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
     * Writes JSON data to JSON file in key-value format
     * @param path path for the JSON file
     * @param varargs data that the user wants written to the JSON file
     * @throws Exception 
     */
    public void set(String path, Object... varargs) throws Exception {

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
            JSONObject jsonDataObj = (JSONObject)jsonData;

            //Loops through varargs to check the provided varargs is an Array
            for(int i = 1; i < varargs.length; i+=2){

                if(varargs[i].getClass().isArray()){

                    Object[] varArr = (Object[]) varargs[i];

                    JSONArray jsonArr = new JSONArray();

                    jsonArr.addAll(Arrays.asList(varArr));

                    jsonDataObj.put(varargs[i-1], jsonArr);
                    
                } else {

                    jsonDataObj.put(varargs[i-1], varargs[i]);

                }

            } //end for-loop 'i'

            //create new FileWriter and write to file
            FileWriter writer = new FileWriter(path);
            writer.write(jsonDataObj.toString());
            writer.close();

        } catch (Exception e) {

            //if there is an error, print the error
            e.printStackTrace();

        }

    } //end put()

    /**
     * 
     * @param path
     * @param key
     * @return
     * @throws Exception
     */
    public static Object objGet(String path, String key) throws Exception {
            
            //create new JSON parser and FileReader
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

            //Reads the data in JSON file and assigns to Object jsonData
            Object jsonData = parser.parse(jsonFile);

            //assigns data to a JSONObject object
            JSONObject jsonDataObj = (JSONObject) jsonData;

            //checks to make sure value for provided key is not an arrays
            if(jsonDataObj.get(key).getClass().isArray()){
                throw new Error("The wanted data is an array and not fetchable");
            }

            //assigns data to wantedData
            Object wantedData = jsonDataObj.get(key);

            return wantedData;
    
    } //end objGet()

    public static Object[] arrGet(String path, String key) throws Exception {

            //create new JSON parser and FileReader
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

             //Reads the data in JSON file and assigns to Object jsonData
            Object jsonData = parser.parse(jsonFile);

            //assigns data to a JSONObject object
            JSONObject jsonDataObj = (JSONObject) jsonData;

            if(!jsonDataObj.get(key).getClass().isArray()){
                throw new Error("The wanted data is null and not fetchable");
            }

            //gets JSONArray from key and assigns to jsonArray
            JSONArray jsonArr = (JSONArray) jsonDataObj.get(key);

            //assigns data to wantedData
            Object[] wantedData = jsonArr.toArray();

            return wantedData;

    } //end arrGet()

} //end JsonDB
