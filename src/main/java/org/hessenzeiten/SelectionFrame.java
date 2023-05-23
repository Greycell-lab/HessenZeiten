package org.hessenzeiten;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SelectionFrame extends JFrame {
    public SelectionFrame(){
        init();
    }
    public void init(){
        JPanel panel = new JPanel();
        String[] monthStrings = new String[12];
        int i=0;
        for(Monat monat : Monat.values()){
            monthStrings[i] = monat.getName();
            i++;
        }
        JComboBox<String> months = new JComboBox<>(monthStrings);
        JButton export = new JButton("Export");
        export.addActionListener(e -> {
            new ExcelExport(Monat.valueOf(Objects.requireNonNull(months.getSelectedItem()).toString().toUpperCase()));
        });
        panel.add(months);
        panel.add(export);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setVisible(true);
    }
}
