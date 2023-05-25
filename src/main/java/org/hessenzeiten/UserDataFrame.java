package org.hessenzeiten;

import org.json.JSONException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class UserDataFrame extends JFrame {
    public UserDataFrame(){
        JPanel panel = new JPanel();
        JLabel userLabel = new JLabel("Benutzername");
        TextField userField = new TextField();
        JLabel passwordLabel = new JLabel("Passwort");
        TextField passwordField = new TextField();
        JButton loginButton = new JButton("Login");
        panel.setLayout(new GridLayout(0,2));
        passwordField.setEchoChar('*');
        loginButton.addActionListener(e -> {
            try{
                new UserTokenRequest(userField.getText(), passwordField.getText());
                new SelectionFrame(this);
            }catch(JSONException ex){
                JOptionPane.showMessageDialog(null, "Falsche Eingabe");
            }
        });
        panel.add(userLabel);
        panel.add(userField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        this.getRootPane().setDefaultButton(loginButton);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.add(panel);
        this.pack();
        this.setVisible(true);

    }
}
