package org.hessenzeiten.utils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hessenzeiten.enums.Monat;
import org.hessenzeiten.frames.SelectionFrame;
import org.hessenzeiten.requests.TimeRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static org.apache.poi.ss.util.CellUtil.createCell;

public class ExcelExport {
    private final XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet sheet;
    private final SimpleDateFormat formatter = new SimpleDateFormat("dd.MM");
    private String user;
    private static Monat month;
    private static Integer year;
    private static String eId;
    public static ArrayList<String> exportedList = new ArrayList<>();
    public static String path;
    public ExcelExport(){
    }
    public ExcelExport(Monat m, String y, int projectId, String employeeId, String p) {
        month = m;
        year = Integer.parseInt(y);
        eId = employeeId;
        path = p;
        new TimeRequest(getDurationString(year, SelectionFrame.getMonth()), projectId);
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
    public boolean writeDataLines(JSONObject timeTracks, int projectId){
        int rowNum = 1;
        double sum = 0;
        JSONArray array = timeTracks.getJSONArray("time_records");
        try {
            JSONObject project = timeTracks.getJSONObject("related");
            project = project.getJSONObject("Project");
            project = project.getJSONObject(String.valueOf(projectId));
            for (Object timeTrack : array) {
                if(eId.equals(((JSONObject)timeTrack).getString("user_name"))) {
                    sum += ((JSONObject) timeTrack).getDouble("value");
                    user = ((JSONObject) timeTrack).getString("user_name").replace(' ', '_');
                    Row row = sheet.createRow(rowNum);
                    createCell(row, 0, formatter.format(new Date(((JSONObject) timeTrack).getLong("record_date") * 1000)));
                    createCell(row, 1, ((JSONObject) timeTrack).get("value").toString().replace('.', ','));
                    createCell(row, 2, "placeholder");
                    createCell(row, 3, project.getString("name"));
                    createCell(row, 4, ((JSONObject) timeTrack).getString("summary"));
                    createCell(row, 5, "Würzburg");
                    rowNum++;
                }
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

    public void export(JSONObject timeTracks, int projectId, String path){
        try {
            writeHeaderLine();
            if(writeDataLines(timeTracks, projectId)) {
                File file = new File(path + File.separator + "tasks" + user + "_" + projectId + "_" + month.getName() + "_" + year + ".xlsx");
                FileOutputStream out = new FileOutputStream(file);
                workbook.write(out);
                out.close();
                workbook.close();
                exportedList.add(path + File.separator + "tasks" + user + "_" + projectId + "_" + month.getName() + "_" + year + ".xlsx");
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public static String getDurationString(JComboBox<String> yearBox, JComboBox<String> monthBox){
        LocalDate dateFrom = LocalDate.of(Integer.parseInt(Objects.requireNonNull(yearBox.getSelectedItem()).toString()), monthBox.getSelectedIndex()+1, 1);
        LocalDate dateTo = dateFrom.withDayOfMonth(dateFrom.getMonth().length(dateFrom.isLeapYear()));
        return "?from=" + dateFrom + "&to=" + dateTo;
    }
    public static String getDurationString(int year, Monat month){
        LocalDate dateFrom = LocalDate.of(year, month.getNum(), 1);
        LocalDate dateTo = dateFrom.withDayOfMonth(dateFrom.getMonth().length(dateFrom.isLeapYear()));
        return "?from=" + dateFrom + "&to=" + dateTo;
    }
}
