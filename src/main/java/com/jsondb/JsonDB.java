package com.jsondb;

//import dependencies 
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class JsonDB {

    public JsonDB(String path) throws Exception {
         checkIfValid(path);
    }

    private static void checkIfValid(String path) throws Exception {
        //check to make sure path is provided
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
        }

        //check to make sure the file is readable
        if(!file.canRead() || !file.canWrite()){
            throw new Error("File is not accessible, check permissions");
        }
    } //end checkIfValie()



    public static void put(String path){
        //creates new JSONObject where data will be stored
        JSONObject data = new JSONObject();

        try{
            
        } catch (JSONException e){
            e.printStackTrace();
        } //end try-catch

        try(PrintWriter out = new PrintWriter(new FileWriter(path))) {
            out.write(data.toString());
        } catch (Exception e){
            e.printStackTrace();
        } //end try-catch
    } //end put()



}
