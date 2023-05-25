package org.hessenzeiten.utils;

import org.json.JSONObject;

import java.io.*;

public class JsonFileHandler {
    private final JSONObject jsonToken;
    public JsonFileHandler(JSONObject jsonToken){
        this.jsonToken = jsonToken;
    }
    public void writeToFile(){
        try(PrintWriter writer = new PrintWriter("token.json")){
            writer.println(jsonToken);
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public static JSONObject readFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("token.json"))){

            return new JSONObject(reader.readLine());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public static boolean fileExists(){
        return new File("token.json").exists();
    }
}
