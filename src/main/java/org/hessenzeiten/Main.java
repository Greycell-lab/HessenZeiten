package org.hessenzeiten;

import org.hessenzeiten.frames.SelectionFrame;
import org.hessenzeiten.frames.UserDataFrame;
import org.hessenzeiten.utils.JsonFileHandler;

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