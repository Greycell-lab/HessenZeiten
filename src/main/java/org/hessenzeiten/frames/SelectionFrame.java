package org.hessenzeiten.frames;

import org.hessenzeiten.enums.Monat;
import org.hessenzeiten.requests.TimeRequest;
import org.hessenzeiten.utils.ExcelExport;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class SelectionFrame extends JFrame {
    private static JComboBox<String> months;
    public SelectionFrame(UserDataFrame frame){
        if(frame != null) frame.dispose();
        init();
    }
    public void init(){
        JPanel panel = new JPanel();
        String[] monthStrings = new String[12];
        ArrayList<String> yearStrings = new ArrayList<>();
        int i=0;
        for(Monat monat : Monat.values()){
            monthStrings[i] = monat.getName();
            i++;
        }
        for(int j=LocalDate.now().getYear();j> 2000; j--){
            yearStrings.add(String.valueOf(j));
        }
        String[] yearMonthStrings = new String[LocalDate.now().getMonthValue()];
        System.out.println();
        System.arraycopy(monthStrings, 0, yearMonthStrings, 0, yearMonthStrings.length);
        DefaultComboBoxModel<String> defaultMonths = new DefaultComboBoxModel<>(monthStrings);
        DefaultComboBoxModel<String> actualYearMonths = new DefaultComboBoxModel<>(yearMonthStrings);
        String[] yearStringsArray = new String[yearStrings.size()];
        yearStrings.toArray(yearStringsArray);
        months = new JComboBox<>(actualYearMonths);
        JComboBox<String> years = new JComboBox<>(yearStringsArray);
        JButton export = new JButton("Export");
        JButton setId = new JButton("ID setzen");
        years.addActionListener(e -> {
            if(Integer.parseInt(Objects.requireNonNull(years.getSelectedItem()).toString()) == LocalDate.now().getYear()){
                months.setModel(actualYearMonths);
            }else {
                months.setModel(defaultMonths);
            }
            this.pack();
        });
        JLabel projectId = new JLabel("Projekt ID");
        JLabel employeeId = new JLabel("Mitarbeiter");
        JTextField projectText = new JTextField();
        ArrayList<String> employeeStrings = new ArrayList<>();
        HashMap<String, Integer> employeeMap = new HashMap<>();
        JComboBox<String> employeeBox = new JComboBox<>();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        setId.addActionListener(e -> {
            employeeStrings.clear();
            JSONObject object = new TimeRequest().getResponseObject(Integer.parseInt(projectText.getText()), ExcelExport.getDurationString(years, months));
            JSONArray array = object.getJSONArray("time_records");
            for(Object employee : array){
                JSONObject emp = ((JSONObject)employee);
                if(employeeMap.get(emp.getString("user_name")) == null){
                    employeeMap.put(emp.getString("user_name"), emp.getInt("user_id"));
                }
                if(!employeeStrings.contains(emp.getString("user_name"))) employeeStrings.add(emp.getString("user_name"));
            }
            String[] eStrings = new String[employeeStrings.size()];
            for(int j=0; j<employeeStrings.size(); j++){
                eStrings[j] = employeeStrings.get(j);
            }
            DefaultComboBoxModel<String> employees = new DefaultComboBoxModel<>(eStrings);
            employeeBox.setModel(employees);
        });
        export.addActionListener(e -> {
            try {
                fileChooser.showSaveDialog(null);
                System.out.println(fileChooser.getSelectedFile().getAbsolutePath());

                new ExcelExport(
                        Monat.valueOf(
                                Objects.requireNonNull(months.getSelectedItem()).toString().toUpperCase()),
                        Objects.requireNonNull(years.getSelectedItem()).toString(), Integer.parseInt(projectText.getText()), Objects.requireNonNull(employeeBox.getSelectedItem()).toString(), fileChooser.getSelectedFile().getAbsolutePath());
            }catch(NumberFormatException ex){
                JOptionPane.showMessageDialog(null, "Falsches Format für Projekt oder Mitarbeiter ID");
            }
        });

        panel.add(months);
        panel.add(years);
        panel.add(new JPanel());
        panel.add(projectId);
        panel.add(projectText);
        panel.add(setId);
        panel.add(employeeId);
        panel.add(employeeBox);
        panel.add(export);

        //panel.add(employeeText);
        panel.setLayout(new GridLayout(0,3));
        panel.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(panel);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
    public static Monat getMonth(){
        return Monat.valueOf(months.getSelectedItem().toString().toUpperCase());
    }
}
