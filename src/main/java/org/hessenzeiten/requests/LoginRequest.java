package org.hessenzeiten.requests;

import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginRequest extends JSONObject {
    private final String loginString;
    public LoginRequest(String loginString) {
        this.loginString = loginString;
    }
    public JSONObject tokenRequest(){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(loginString))
                    .uri(new URI("https://ac.ai-ag.de/api/v1//issue-token"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        }catch(IOException | InterruptedException | URISyntaxException e){
            e.printStackTrace();
        }
        return null;
    }
}
