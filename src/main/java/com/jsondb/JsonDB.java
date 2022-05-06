package com.jsondb;

//import dependencies 
import java.io.File;
import java.util.Arrays;
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
     * @throws Exception
     */
    public JsonDB(String jsonPath) throws Exception {
        
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
            System.out.println("File does not exist");
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

    } //end constructor

    /**
     * Writes JSON data to JSON file in key-value format
     * @param path path for the JSON file
     * @param varargs data that the user wants written to the JSON file
     * @throws NoSuchElementException if the number of arguments are not even
     * @throws Exception 
     */
    public void set(Object... varargs) throws Exception {

        //checks to make sure varargs 'varargs' are in key, value format
        if(varargs.length %2 != 0){
            throw new NoSuchElementException("varargs do not follow (key, value) format");
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

                //check if vararg is an array
                if(varargs[i].getClass().isArray()){

                    //cast vararg to an Object array
                    Object[] varArr = (Object[]) varargs[i];

                    //create a new JSON Array
                    JSONArray jsonArr = new JSONArray();

                    //add all elements from the array into the JSON Array
                    jsonArr.addAll(Arrays.asList(varArr));

                    //add jsonArr to JSON Object
                    jsonDataObj.put(varargs[i-1], jsonArr);
                    
                } else {

                    //if vararg is not an array 
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

    } //end set()

    /**
     * Allows user to create nested JSON Object 
     * @param objKey key for JSONObject value
     * @param varargs Data that the nested object will hold
     * @throws NoSuchElementException if varargs dont follow key-value format
     * @throws Exception
     */
    public void setObj(String objKey, Object... varargs) throws Exception {

        //checks to make sure varargs 'varargs' are in key, value format
        if(varargs.length %2 != 0){
            throw new NoSuchElementException("varargs do not follow (key, value) format");
        }

        //create new JSON parser and FileReader
        JSONParser parser = new JSONParser();
        FileReader jsonFile = new FileReader(path);

        try{
            
            //reads JSON file and creates JSONObject from read data
            Object jsonData = parser.parse(jsonFile);
            JSONObject jsonObj = (JSONObject) jsonData;

            //create nested JSON Object
            JSONObject userDefinedObject = new JSONObject();

            //loop through varargs
            for(int i = 1; i < varargs.length; i+=2){

                //check to see if varargs[i] is an array
                if(varargs[i].getClass().isArray()){

                    //cast the vararg to an array if it is
                    Object[] varArr = (Object[]) varargs[i];

                    //create JSON Array using Object array
                    JSONArray jsonArr = new JSONArray();

                    //add all elements from the array into the JSON Array
                    jsonArr.addAll(Arrays.asList(varArr));

                    //add JSON Array to nested JSON Object
                    userDefinedObject.put(varargs[i-1], jsonArr);
                    
                } else {

                    //add vararg to nested JSON Object
                    userDefinedObject.put(varargs[i-1], varargs[i]);

                }
 
            } //end for-loop

            //add nested JSONObject to main JSON Object
            jsonObj.put(objKey, userDefinedObject);

            //write the JSON Object to the JSON file
            FileWriter writer = new FileWriter(path);
            writer.write(jsonObj.toString());
            writer.close();

        } catch (Exception e){

            //if there is an error, print the error
            e.printStackTrace();
        }

    } //end setObj()

    /**
     * gets the value attached to key as long as the value isnt an array
     * @param path is the path to the JSON file
     * @param key is associated  with wanted value
     * @return Object wantedData 
     * @throws IllegalArgumentException when wanted data is not getable
     * @throws Exception
     */
    public static Object get(String key, Object... objKey) throws Exception {
            
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

    } //end get()

    /**
     * Get array data from an element a JSON Objtec
     * @param path path to the JSON file
     * @param key key associated with wanted value
     * @return Object[] wantedData
     * @throws IllegalArgumentException if more than one 'objKey' is passed
     * @throws IllegalArgumentException if the wanted data is not an array
     * @throws Exception
     */
    public static Object[] arrGet(String key, Object... objKey) throws Exception {

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

                if(!userDefinedObj.get(key).getClass().isArray()){
                    throw new IllegalArgumentException("Can not retrieve data if it is not an array");
                }

                //create JSON Array to hold array data 
                JSONArray jsonArr = (JSONArray) userDefinedObj.get(objKey[0]);

                //create a returnable Object Array from the JSON Array
                Object[] wantedArr = jsonArr.toArray();

                return wantedArr;

            } else {

                if(!jsonDataObj.get(key).getClass().isArray()){
                    throw new IllegalArgumentException("Can not retrieve data if it is not an array");
                }

                //gets JSONArray from key and casts it to JSON Array
                JSONArray jsonArr = (JSONArray) jsonDataObj.get(key);

                //assigns jsonArr data to wantedData
                Object[] wantedData = jsonArr.toArray();

                return wantedData;

            }

    } //end arrGet()

    /**
     * Check to see if the JSON object has a key
     * @param path path to JSON file
     * @param key key that is checked for if it exists
     * @return Boolean doesExist
     * @throws IllegalArgumentException if more than one 'objKey' is provided
     * @throws Exception
     */
    public static Boolean has(String key, Object... objKey) throws Exception {

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

    } //end end()

    /**
     * 
     * @param key to identify element to be deleted
     * @param objKey optional key is 'key' param is a nested JSON Object
     * @throws IllegalArgumentException if use provides more than one 'objKey'
     * @throws NoSuchElementException if program can not find 'key' or 'objKey' in JSON file
     * @throws Exception
     */
    public static void delete(String key, Object... objKey) throws Exception {

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

        FileWriter writer = new FileWriter(path);
        writer.write(jsonDataObj.toString());
        writer.close();

    } //end delete()

    /**
     * deletes all of the elements in the JSON file
     * @throws Exception
     */
    public static void deleteAll() throws Exception {

        //Create new empty JSON object
        JSONObject jsonReplace = new JSONObject();

        //write the empty JSON Object to the JSON file
        FileWriter writer = new FileWriter(path);
        writer.write(jsonReplace.toString());
        writer.close();

    }

} //end JsonDB