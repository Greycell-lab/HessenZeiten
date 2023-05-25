package org.hessenzeiten;
import org.json.JSONObject;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Objects;

public class TimeRequest {
    private Path path = null;
    public TimeRequest(String fromTo){
        String token = Objects.requireNonNull(JsonFileHandler.readFromFile()).get("token").toString();
        String[] projects = {"160", "83", "49", "160"};
        String[] pId = {"AZ", "AZ", "AZ", "AZ"};

        /*String[] projects = {"26", "27", "28", "29"};
        String[] pId = {"LBIH", "HZD", "HMO", "VOL_Stellen"};*/
        int counter = 0;
        for(int i=0;i< projects.length;i++){
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .setHeader("X-Angie-AuthApiToken", token)
                        .uri(new URI("https://ac.ai-ag.de/api/v1/projects/"+ projects[i] + "/time-records/filtered-by-date/" + fromTo))
                        .build();
                HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
                path = new ExcelExport().export(new JSONObject(response.body()), projects[i], pId[i]);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        if(!ExcelExport.exportedList.isEmpty()) {
            StringBuilder fullExport = new StringBuilder();
            for(String s : ExcelExport.exportedList){
                fullExport.append(s);
                fullExport.append("\n");
            }
            JOptionPane.showMessageDialog(null, "Export erfolgreich.\n" + fullExport);
            ExcelExport.exportedList.clear();
        }

    }
}
