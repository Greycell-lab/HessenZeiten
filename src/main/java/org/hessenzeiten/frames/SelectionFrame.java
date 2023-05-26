package org.hessenzeiten.frames;

import org.hessenzeiten.enums.Monat;
import org.hessenzeiten.utils.ExcelExport;

import javax.swing.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

public class SelectionFrame extends JFrame {
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
        String[] yearStringsArray = new String[yearStrings.size()];
        yearStrings.toArray(yearStringsArray);
        JComboBox<String> months = new JComboBox<>(monthStrings);
        JComboBox<String> years = new JComboBox<>(yearStringsArray);
        JButton export = new JButton("Export");
        export.addActionListener(e -> new ExcelExport(
                Monat.valueOf(
                Objects.requireNonNull(months.getSelectedItem()).toString().toUpperCase()),
                Objects.requireNonNull(years.getSelectedItem()).toString()));
        panel.add(months);
        panel.add(years);
        panel.add(export);
        panel.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(panel);
        this.setLocationRelativeTo(null);
        this.pack();
        this.setVisible(true);
    }
}
