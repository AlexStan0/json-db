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

    //declaration of JSON file path variable
    private static String path = "";

    /**
     * Creates DB and checks JSON file integrity
     * @param path Path to file with JSON data
     * @throws Error if path argument is not provided or program can not write to/read the file
     * @throws Exception
     */
    public JsonDB(String jsonPath) throws Exception {
        
        //assign jsonPath to 'path' so user dont have to define each time they call a method
        path = jsonPath;

        //makes sure a path argument is provided
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

            //create a new empty JSONObject and write it to JSONfile
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
     * @throws Error if the number of arguments are not even
     * @throws Exception 
     */
    public void set(Object... varargs) throws Exception {

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

    } //end setSingle()

    /**
     * Allows user to create nested Object 
     * @param objKey key for JSONObject value
     * @param varargs Data that the nested object will hold
     * @throws Exception
     */
    public void setObj(String objKey, Object... varargs) throws Exception{

        //checks to make sure varargs 'varargs' are in key, value format
        if(varargs.length %2 != 0){
            throw new Error("varargs do not follow (key, value) format");
        }

        //create new JSON parser and FileReader
        JSONParser parser = new JSONParser();
        FileReader jsonFile = new FileReader(path);

        try{
            
            //assigns data to jsonData object
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
            e.printStackTrace();
        }

    } //end setObj()

    /**
     * gets the value attached to key as long as the value isnt an array
     * @param path is the path to the JSON file
     * @param key is associated  with wanted value
     * @return Object wantedData 
     * @throws Error when user wants to pull Array 
     * @throws Exception
     */
    public static Object objGet(String key, Object objKey) throws Exception {
            
            //create new JSON parser and FileReader
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

            //Reads the data in JSON file and assigns to Object jsonData
            Object jsonData = parser.parse(jsonFile);

            //assigns data to a JSONObject object
            JSONObject jsonDataObj = (JSONObject) jsonData;

            //throw error if the value in the key is an array
            if(jsonDataObj.get(key).getClass().isArray()){
                throw new Error("Data is an array and not returnable");
            }

            //check to make sure the objKey isn't null and that the key is a JSONObject
            if(objKey != null && jsonDataObj.get(key) instanceof JSONObject){

                //created new JSON Object and assign it the nested JSON Object
                JSONObject coolData = (JSONObject) jsonDataObj.get(key);

                //get data from nested JSON Object using optional 'objKey'
                Object wantedData = coolData.get(objKey);

                System.out.println(wantedData);

                return wantedData;

            } else {

                //if 'objKey' is not provided just data from main JSON Object
                Object wantedData = (Object) jsonDataObj.get(key);

                return wantedData;

            }   
    
    } //end objGet()

    /**
     * 
     * @param path path to the JSON file
     * @param key key associated with wanted value
     * @return Object[] wantedData
     * @throws Error if element 'key' does not exist as an array
     * @throws Exception
     */
    public static Object[] arrGet(String key, Object... objKey) throws Exception {

            //create new JSON parser and FileReader
            JSONParser parser = new JSONParser();
            FileReader jsonFile = new FileReader(path);

             //Reads the data in JSON file and assigns to Object jsonData
            Object jsonData = parser.parse(jsonFile);

            //assigns data to a JSONObject object
            JSONObject jsonDataObj = (JSONObject) jsonData;

            //check to see if the data is an array or if the key does not exists
            if(!jsonDataObj.get(key).getClass().isArray() || jsonDataObj.get(key) == null){
                throw new Error("The wanted data is not fetchable");
            }

            if(jsonDataObj.get(key) instanceof JSONObject) {

                JSONObject userDefinedObj = (JSONObject) jsonDataObj.get(key);

                Object[] wantedArr = (Object[]) userDefinedObj.get(objKey);

                return wantedArr;

            } else {

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
     * @return  Boolean doesExist
     * @throws Exception
     */
    public static Boolean has(String key) throws Exception {

        //create new JSON paser and FileReader 
        JSONParser parser = new JSONParser();
        FileReader jsonFile = new FileReader(path);

        //reads file and creates JSON Object 
        Object jsonData = parser.parse(jsonFile);
        JSONObject jsonDataObj = (JSONObject) jsonData;

        //checks to see if the key exists 
        if(jsonDataObj.get(key) != null){

            //if it exists return true
            return true;

        } else {

            //return false if it doesn't exist
            return false;
        }

    } //end 

    public static void delete(String key) throws Exception {

    }

} //end JsonDB
