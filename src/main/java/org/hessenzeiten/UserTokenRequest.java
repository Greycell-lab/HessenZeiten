package org.hessenzeiten;

import org.json.JSONObject;

import java.util.Scanner;

public class UserTokenRequest {
    public UserTokenRequest(String username, String password){
        String loginString = "{\"username\":" + "\"" + username + "\"" + ", \"password\":" + "\"" + password + "\"" + ", \"client_name\": \"Active Collab Time-Record-Tool\", \"client_vendor\": \"Domesoft\"}";
        new JsonFileHandler(new LoginRequest(loginString).tokenRequest()).writeToFile();
    }
}
