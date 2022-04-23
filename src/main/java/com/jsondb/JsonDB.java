package com.jsondb;

//import dependencies 
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;

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
            
            try {
                FileWriter out = new FileWriter(path);
                out.write("[\n]");
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        //check to make sure the file is readable
        if(!file.canRead() || !file.canWrite()){
            throw new Error("File is not accessible, check permissions");
        }
    } //end checkIfValie()

    /**
     * Pushes JSON data 
     * @param path
     * @param varargs
     */
    public static void put(String path, Object... varargs){
        
        if(varargs.length %2 != 0){
            throw new Error("varags do not follow (key, value) format");
        }

        JSONParser jsonP = new JSONParser();

        try {

            JSONObject jsondb = (JSONObject)jsonP.parse(new FileReader(path));

            for(int i = 0; i < varargs.length; i+=2){
                jsondb.put((String)varargs[i-1], varargs[i]);
            }

            FileWriter out = new FileWriter(path);
            out.write(jsondb.toString());
            out.close();

        } catch(Exception e) {
            e.printStackTrace();
        } //end try-catch 

    }



}
