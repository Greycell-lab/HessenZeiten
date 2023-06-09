package org.hessenzeiten.requests;
import org.hessenzeiten.utils.ExcelExport;
import org.hessenzeiten.utils.JsonFileHandler;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;

public class TimeRequest {
    String token = Objects.requireNonNull(JsonFileHandler.readFromFile()).get("token").toString();
    public TimeRequest(){

    }
    public TimeRequest(String fromTo, int projectId){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .setHeader("X-Angie-AuthApiToken", token)
                    .uri(new URI("https://ac.ai-ag.de/api/v1/projects/"+ projectId + "/time-records/filtered-by-date/" + fromTo))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            new ExcelExport().export(new JSONObject(response.body()), projectId, ExcelExport.path);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public JSONObject getResponseObject(int projectId, String fromTo){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .setHeader("X-Angie-AuthApiToken", token)
                    .uri(new URI("https://ac.ai-ag.de/api/v1/projects/"+ projectId + "/time-records/filtered-by-date/" + fromTo))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            return new JSONObject(response.body());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
