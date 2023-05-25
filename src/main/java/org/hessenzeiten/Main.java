package org.hessenzeiten;

public class Main {

    public static void main(String[] args) {
        if(JsonFileHandler.fileExists()){
            new SelectionFrame();
        }
        else{
            if(UserData.getUserData()){
                new SelectionFrame();
            }
            else System.out.println("Username und/oder Passwort falsch");
        }
    }
}