package org.hessenzeiten;

public class Main {

    public static void main(String[] args) {
        if(JsonFileHandler.fileExists()){
            new SelectionFrame(null);
        }
        else{
            new UserDataFrame();
        }
    }
}