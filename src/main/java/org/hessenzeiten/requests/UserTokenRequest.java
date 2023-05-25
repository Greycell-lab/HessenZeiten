package org.hessenzeiten.requests;

import org.hessenzeiten.utils.JsonFileHandler;

public class UserTokenRequest {
    public UserTokenRequest(String username, String password){
        String loginString = "{\"username\":" + "\"" + username + "\"" + ", \"password\":" + "\"" + password + "\"" + ", \"client_name\": \"Active Collab Time-Record-Tool\", \"client_vendor\": \"Domesoft\"}";
        new JsonFileHandler(new LoginRequest(loginString).tokenRequest()).writeToFile();
    }
}
