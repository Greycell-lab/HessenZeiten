package org.hessenzeiten;


public class Main {

    public static void main(String[] args) {
        if(JsonFileHandler.fileExists()){
            new SelectionFrame();
        }
        else UserData.getUserData();
    }
}