package org.hessenzeiten.utils;

import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFileHandler {
    private final JSONObject jsonToken;
    public JsonFileHandler(JSONObject jsonToken){
        this.jsonToken = jsonToken;
    }
    public void writeToFile(){
        try(PrintWriter writer = new PrintWriter("token.json")){
            writer.println(jsonToken);
            Path path = Paths.get("token.json");
            Files.setAttribute(path, "dos:hidden", true);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static JSONObject readFromFile(){
        try(BufferedReader reader = new BufferedReader(new FileReader("token.json"))){
            return new JSONObject(reader.readLine());
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Token Datei nicht gefunden.");
            return null;
        }
    }
    public static boolean fileExists(){
        return new File("token.json").exists();
    }
}
