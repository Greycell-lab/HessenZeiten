package org.hessenzeiten;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class ExcelExport {
    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet sheet;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM");
    private String user;
    private static Monat month;
    public static ArrayList<String> exportedList = new ArrayList<>();
    public ExcelExport(){
    }
    public ExcelExport(Monat m) {
        month = m;
        LocalDate dateFrom = LocalDate.of(LocalDate.now().getYear(), month.getNum(), 1);
        LocalDate dateTo = dateFrom.withDayOfMonth(dateFrom.getMonth().length(dateFrom.isLeapYear()));
        String fromTo = "?from=" + dateFrom + "&to=" + dateTo;
        new TimeRequest(fromTo);
    }
    public void writeHeaderLine(){
        sheet = workbook.createSheet("Zeiten");

        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Datum", style);
        createCell(row, 1, "Dauer der Tätigkeit", style);
        createCell(row, 2, "Kunde", style);
        createCell(row, 3, "Arbeitspaket", style);
        createCell(row, 4, "Durchgeführte Tätigkeiten", style);
        createCell(row, 5, "Leistungsort", style);
        createCell(row, 6, "Leistung für Bereich", style);
        createCell(row, 7, "Namenszeichen", style);
        for(int i=0;i<8;i++){
            sheet.autoSizeColumn(i);
        }
    }
    public boolean writeDataLines(JSONObject timeTracks, String projectId, String pId){
        int rowNum = 1;
        double sum = 0;
        JSONArray array = timeTracks.getJSONArray("time_records");
        try {
            JSONObject project = timeTracks.getJSONObject("related");
            project = project.getJSONObject("Project");
            project = project.getJSONObject(projectId);
            for (Object timeTrack : array) {
                sum += ((JSONObject) timeTrack).getDouble("value");
                user = ((JSONObject) timeTrack).getString("user_name").replace(' ', '_');
                Row row = sheet.createRow(rowNum);
                createCell(row, 0, formatter.format(new Date(((JSONObject) timeTrack).getLong("record_date") * 1000)));
                createCell(row, 1, ((JSONObject) timeTrack).get("value").toString().replace('.', ','));
                createCell(row, 2, pId);
                createCell(row, 3, project.getString("name"));
                createCell(row, 4, ((JSONObject) timeTrack).getString("summary"));
                createCell(row, 5, "Würzburg");
                rowNum++;
            }
            Row row = sheet.createRow(rowNum);
            createCell(row, 0, "Gesamt:");
            createCell(row, 1, String.valueOf(sum).replace('.', ','));
            return true;
        }catch(JSONException e){
            JOptionPane.showMessageDialog(null, "Keine Einträge gefunden für das Projekt mit der ID: " + projectId + " für den Monat " + month.getName());
            return false;
        }
    }

    public Path export(JSONObject timeTracks, String projectId, String pId){
        try {
            writeHeaderLine();
            if(writeDataLines(timeTracks, projectId, pId)) {
                Path path = Paths.get("C:" + File.separator
                        + "Users" + File.separator
                        + System.getProperty("user.name") + File.separator
                        + "Desktop" + File.separator
                        + "Hessenzeit_Export" + File.separator);
                Files.createDirectories(path);
                File file = new File(path + File.separator + "tasks" + user + "_" + projectId + ".xlsx");
                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
                workbook.close();
                exportedList.add(path + File.separator + "tasks" + user + "_" + projectId + ".xlsx");
                return path;
            }
            else {
                return null;
            }
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public Monat getMonth() {
        return month;
    }
}
