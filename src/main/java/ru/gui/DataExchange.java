package ru.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.apache.commons.io.input.ReversedLinesFileReader;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class DataExchange extends Thread {

    private final DataInputStream in;
    private String currentUserLogin;
    private FileReader reader;
    private FileWriter writer;
    private ReversedLinesFileReader reversedReader;
    private int historyMsgShowing = 100;
    private ArrayList<String> lastHundredMessages;
    private File history;

    @FXML
    TextArea chatWindow;

    public DataExchange(DataInputStream in, TextArea chatWindow) throws IOException {
        this.in = in;
        this.chatWindow = chatWindow;
        lastHundredMessages = new ArrayList<>();
    }

    /**
     * wait and proceed actions with string from server
     */
    @Override
    public void run() {
        try {
            while (true) {
                String strFromServer = in.readUTF();
                if (strFromServer.equalsIgnoreCase("/end")) {
                    break;
                } else if (strFromServer.contains("/currentUserLogin")) {
                    currentUserLogin = strFromServer.split(" ")[3];
                    System.out.println(currentUserLogin);
                    historyFileInitialize();
                } else {
                    chatWindow.appendText(strFromServer + "\n");
                    if (writer != null )  {
                        writer.write(strFromServer + "\n");
                        writer.flush();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStreams();
        }
    }

    private void historyFileInitialize() {
        String currentHistoryFileName = String.format("\\history_%s.txt", currentUserLogin);
        history = new File(System.getProperty("user.dir") + currentHistoryFileName);
        System.out.println(history.getAbsolutePath());
        try {
            if (!history.exists()) {
                history.createNewFile();
                System.out.println("created new file " + history.getAbsolutePath());
            }
            reader = new FileReader(history);
            writer = new FileWriter(history, true);
            reversedReader = new ReversedLinesFileReader(history, null);
            showHistory();
        } catch (IOException e) {
            System.err.println("Exception when initialize history file");
        }
    }

    private void showHistory() {
        try {
            String historyMsg = reversedReader.readLine();
            int count = 0;
            while (historyMsg != null && count < historyMsgShowing) {
                lastHundredMessages.add(historyMsg);
                historyMsg = reversedReader.readLine();
            }
            Collections.reverse(lastHundredMessages);
            for (String s : lastHundredMessages) {
                chatWindow.appendText(s + "\n");
            }
        } catch (IOException e) {
            System.err.println("Unable to read history");
        }
    }

    private void closeStreams() {
        try {
            reader.close();
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.err.println("unable to close streams");
        }
    }
}
