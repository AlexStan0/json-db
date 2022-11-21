package com.jsondb;

//import dependencies 
import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.NoSuchElementException;
import java.lang.IllegalArgumentException;

public class JsonDB {

    //declaration of JSON file path variable
    private static String path;

    /**
     * Creates DB and checks JSON file integrity
     * @param path Path to file with JSON data
     * @throws IllegalArgumentException if path argument is not provided or program can not write to/read the file
     */
    public JsonDB(String jsonPath) {
       
        try {
            //assign jsonPath to 'path' so user dont have to define each time they call a method
            path = jsonPath;

            //makes sure a path argument is provided
            if(path.equals("")){
                throw new IllegalArgumentException("Missing path argument");
            }

            //create new file using provided path
            File file = new File(path);

            //check to make sure the file exists
            if(!file.exists() || file.isDirectory()){
                
                //if file does not exist it creates a new one in the current directory
                System.out.println("File does not exist, creating new file...");
                file.createNewFile();

                //create a new empty JSONObject and write it to JSONfile
                JSONObject jsonObject = new JSONObject();
                FileWriter writer = new FileWriter(path);
                writer.write(jsonObject.toString());
                writer.close();
            }

            //check to make sure the file is readable
            if(!file.canRead() || !file.canWrite()){
                throw new IllegalArgumentException("File is not accessible, check permissions");
            }
        
        } catch (Exception e) {
             //if there is an error, print the error
            e.printStackTrace();
        
        }

    } //end constructor

    /**
     * Writes JSON data to JSON file in key-value format
     * @param varargs data that the user wants written to the JSON file
     * @throws NoSuchElementException if the number of arguments are not even
     */
    protected void set(Object... varargs){

        try {
            
            //create new JSON parser and FileReader
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

            //reads JSON file and creates JSONObject from read data
            Object jsonData = parser.parse(jsonFile);
            JSONObject jsonDataObj = (JSONObject) jsonData;

            if(varargs.length < 2)  
                throw new Exception("You need 2 or more attributes to write");
            

            if(varargs.length % 2 != 0){

                JSONObject parentObj = new JSONObject();
                Object parentObjKey = varargs[0];

                for(int i = 1; i < varargs.length; i+=2){
                    
                    if(varargs[i].getClass().isArray()){

                        Object[] varArr = (Object[]) varargs[i];
                        Object objKey = varargs[i-1];

                        JSONArray jsonArr = new JSONArray();

                        jsonArr.addAll(Arrays.asList(varArr));

                        parentObj.put(objKey, jsonArr);


                    } else {

                        parentObj.put(varargs[i-1], varargs[i]);

                    }

                    jsonDataObj.put(parentObjKey, parentObj);

                }


            } else {

                for(int i = 1; i < varargs.length; i+= 2){

                    if(varargs[i].getClass().isArray()){

                        Object varArr = (Object[]) varargs[i];
                        JSONArray jsonArr = new JSONArray();

                        jsonArr.addAll(Arrays.asList(varArr));
                        jsonDataObj.put(varargs[i-1], jsonArr);


                    } else {

                        jsonDataObj.put(varargs[i-1], varargs[i]);

                    }

                }

            }

            //write the JSON Object to the JSON file
            FileWriter writer = new FileWriter(path);
            writer.write(jsonDataObj.toString());
            writer.close();

        } catch (Exception e){

            e.printStackTrace();
        }

    }
    
    protected Object get(Object key, Object... objKey) {
            
        try {

            //make sure only one vararg is passed 
            if(objKey.length > 1){
                throw new IllegalArgumentException("You can only fecth one item inside a nested object at once");
            }

            //create new JSON parser and FileReader
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

            //Reads the data in JSON file and assigns to Object jsonData
            Object jsonData = parser.parse(jsonFile);

            //assigns data to a JSONObject object
            JSONObject jsonDataObj = (JSONObject) jsonData;

            //checks to see if data is an array
            if(jsonDataObj.get(key).getClass().isArray()){
                throw new Error("Data is an array and not returnable");
            }

            if(jsonDataObj.get(key) instanceof JSONObject){
            
                //get JSON Object into JSONObject variable to read from
                JSONObject nestedJsonObj = (JSONObject) jsonDataObj.get(key);

                //get wanted data from nested JSON Object
                Object wantedData = nestedJsonObj.get(objKey[0]);

                return wantedData;

            } else {

                //get data associated with 'key' value in JSON Object
                Object wantedData = jsonDataObj.get(key);

                return wantedData;

            }

        } catch (Exception e) {
        
            e.printStackTrace();

            return null;
        
        }

    } //end get()

    /**
     * Get array data from an element a JSON Object
     * @param objKey option key to interact with nested JSON Objects
     * @param key key associated with wanted value
     * @return Object[] wantedData
     * @throws IllegalArgumentException if more than one 'objKey' is passed or the wanted data is not an array
     */
    protected Object[] arrGet(Object key, Object... objKey) {

        try {

            //make sure only one vararg is passed 
            if(objKey.length > 1){
                throw new IllegalArgumentException("You can only pass in one vararg");
            }

            //create new JSON parser and FileReader
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

             //Reads the data in JSON file and assigns to Object jsonData
            Object jsonData = parser.parse(jsonFile);

            //assigns data to a JSONObject object
            JSONObject jsonDataObj = (JSONObject) jsonData;

            //check to see if the element is a nested JSON Object
            if(jsonDataObj.get(key) instanceof JSONObject) {

                //create new JSON Object to hold the nested JSON Object
                JSONObject userDefinedObj = (JSONObject) jsonDataObj.get(key);

                //create JSON Array to hold array data 
                JSONArray jsonArr = (JSONArray) userDefinedObj.get(objKey[0]);

                //create a returnable Object Array from the JSON Array
                Object[] wantedArr = jsonArr.toArray();

                return wantedArr;

            } else {
                
                //gets JSONArray from key and casts it to JSON Array
                JSONArray jsonArr = (JSONArray) jsonDataObj.get(key);

                //assigns jsonArr data to wantedData
                Object[] wantedData = jsonArr.toArray();

                return wantedData;

            }

        } catch (Exception e) {
        
            //if error print it to console
            e.printStackTrace();

            //create empty array to return
            Object[] emptyArr = {};

            return emptyArr;
        
        }

    } //end arrGet()

    /**
     * Check to see if the JSON object has a key
     * @param objKey option key to interact with nested JSON Objects
     * @param key key that is checked for if it exists
     * @return Boolean doesExist
     * @throws IllegalArgumentException if more than one 'objKey' is provided
     */
    protected Boolean has(Object key, Object... objKey) {

        try {
            //make sure only one vararg is passed 
            if(objKey.length > 1){
                throw new IllegalArgumentException("You can only pass in one vararg");
            }

            //create new JSON paser and FileReader 
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

            //reads file and creates JSON Object 
            Object jsonData = parser.parse(jsonFile);
            JSONObject jsonDataObj = (JSONObject) jsonData;

            //check to see if value associated with key is a JSON Object
            if(jsonDataObj.get(key) instanceof JSONObject) {

                //check to see if user is checking if a nested object exists
                if(objKey.length == 0){
                    
                    //instaiate a JSON Object with the value from the nested object
                    JSONObject nestedObj = (JSONObject) jsonDataObj.get(key);

                    //check if the JSON Object exists or not
                    Boolean exists = nestedObj != null ? true : false;

                    return exists;

                } else {

                    //create new JSON Object to pull data from
                    JSONObject nestedJsonObj = (JSONObject) jsonDataObj.get(key);

                    //get data from nested JSON Object 
                    Object jsonInfo = nestedJsonObj.get(objKey[0]);

                    //check if the data exists
                    Boolean exists = jsonInfo != null ? true : false;

                    return exists;

                }

            } else {

                //get the data from the normal object
                Object jsonInfo = jsonDataObj.get(key);

                //check if the data exists
                Boolean exists = jsonInfo != null ? true : false;

                return exists;

            } 
        
        } catch (Exception e){
        
            //if error print it to console
            e.printStackTrace();

            return false;
            
        }

    } //end has()

    /**
     * Deletes the inputed key and value associated with it
     * @param key to identify element to be deleted
     * @param objKey option key to interact with nested JSON Objects
     * @throws IllegalArgumentException if use provides more than one 'objKey'
     * @throws NoSuchElementException if program can not find 'key' or 'objKey' in JSON file
     */
    protected void delete(String key, Object... objKey) {

        try {

            //make sure only one vararg is sepcified
            if(objKey.length > 1){
                throw new IllegalArgumentException("You can not provide more than one vararg");
            }

            //create new JSON parser and file reader
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

            //create a JSON Object with the data retrieved from the JSON file
            Object jsonData = parser.parse(jsonFile);
            JSONObject jsonDataObj = (JSONObject) jsonData;

            //check that they key provided exists in the JSON file
            Boolean exists = jsonDataObj.get(key) != null ? true : false;

            //if the key does not exists tell user
            if(!exists){
                throw new NoSuchElementException("The JSON key can not be found");
            }

            //check to see if the wanted key is a nest JSON Object
            if(jsonDataObj.get(key) instanceof JSONObject && !objKey[0].equals("")){

                //create a new JSON Object with the data from the nested JSON Object
                JSONObject nestedJsonObj = (JSONObject) jsonDataObj.get(key);

                //make sure that the key in the nested JSON Object exists
                Boolean nestedExists = nestedJsonObj.get(objKey[0]) != null ? true : false;

                //inform user if it does not exist
                if(!nestedExists){
                throw new NoSuchElementException("The nested JSON key can not be found");
                }
                
                //remove key from nested JSON Object
                nestedJsonObj.remove(objKey[0]);

                jsonDataObj.put(key, nestedJsonObj);

            } else {

                //remove data from JSON Object
                jsonDataObj.remove(key);

            }

            //write modified JSON Object to the JSON file
            FileWriter writer = new FileWriter(path);
            writer.write(jsonDataObj.toString());
            writer.close();

        } catch (Exception e) {
            
            //if error print to console
            e.printStackTrace();
        
        }

    } //end delete()

    /**
     * deletes all of the elements in the JSON file
     */
    protected void deleteAll() {

        try {
            //Create new empty JSON object
            JSONObject jsonReplace = new JSONObject();

            //write the empty JSON Object to the JSON file
            FileWriter writer = new FileWriter(path);
            writer.write(jsonReplace.toString());
            writer.close();

        } catch (Exception e){
        
            //if error print to console
            e.printStackTrace();
        
        }

    }

} //end JsonDB