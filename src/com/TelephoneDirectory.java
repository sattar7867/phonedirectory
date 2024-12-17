package com;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionListener;
import java.sql.*;

public class TelephoneDirectory {
    static Connection con;

    public TelephoneDirectory() {
        JFrame frame = new JFrame("Telephone Directory");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);

        JTextField nameField = new JTextField(20);
        nameField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(nameField, gbc);

        JLabel phoneLabel = new JLabel("Phone No:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(20);
        phoneField.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(phoneField, gbc);

        Dimension buttonSize = new Dimension(200, 30);

        JButton addRecord = new JButton("Add Record");
        addRecord.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        frame.add(addRecord, gbc);

        JButton selectRecord = new JButton("Select Record");
        selectRecord.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        frame.add(selectRecord, gbc);

        JButton selectAllRecord = new JButton("Select All Record");
        selectAllRecord.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        frame.add(selectAllRecord, gbc);

        JButton editRecord = new JButton("Edit Record");
        editRecord.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        frame.add(editRecord, gbc);

        JButton deleteRecord = new JButton("Delete Record");
        deleteRecord.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        frame.add(deleteRecord, gbc);

        JButton deleteAllRecord = new JButton("Delete All Record");
        deleteAllRecord.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        frame.add(deleteAllRecord, gbc);

        JButton clearText = new JButton("Clear Text");
        clearText.setPreferredSize(buttonSize);
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;
        frame.add(clearText, gbc);

        addRecord.addActionListener(e -> handleDatabaseOperation("insert into phonebook(name,phone_no) values(?,?)", nameField, phoneField, frame, "Record added successfully", "Record not added"));
        deleteRecord.addActionListener(e -> handleDatabaseOperation("delete from phonebook where phone_no=?", nameField, phoneField, frame, "Record deleted successfully", "Record not deleted"));
        selectRecord.addActionListener(e -> handleDatabaseOperation("select * from phonebook where phone_no=?", nameField, phoneField, frame, "Record selected successfully", "Record not selected"));
        editRecord.addActionListener(e -> handleDatabaseOperation("update phonebook set name=?,phone_no=? where phone_no=?", nameField, phoneField, frame, "Record edited successfully", "Record not edited"));
        deleteAllRecord.addActionListener(e -> handleDatabaseOperation("delete from phonebook", nameField, phoneField, frame, "All records deleted successfully", "All records not deleted"));
        selectAllRecord.addActionListener(e -> handleDatabaseOperation("select * from phonebook", nameField, phoneField, frame, "All records selected successfully", "All records not selected"));

        clearText.addActionListener(e -> {
            nameField.setText("");
            phoneField.setText("");
        });

        frame.setVisible(true);
    }

    private void handleDatabaseOperation(String query, JTextField nameField, JTextField phoneField, JFrame frame, String successMessage, String failureMessage) {
        String name = nameField.getText();
        String phoneNo = phoneField.getText();
        if (name.isEmpty() || phoneNo.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all the fields");
            return;
        }

        try {
            con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, phoneNo);
            ResultSet rs = stmt.executeQuery();
            nameField.setText("");
            phoneField.setText("");
            if (rs.next()) {
                JOptionPane.showMessageDialog(frame, successMessage);
            } else {
                JOptionPane.showMessageDialog(frame, failureMessage);
            }
            con.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        //Connection con = null; // Declare connection here
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "SYSTEM", "12345678");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return con; // Return the connection object
    }
    
}