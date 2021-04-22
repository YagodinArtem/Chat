package ru.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.IOException;

public class DataExchange extends Thread {

    private final DataInputStream in;

    @FXML
    TextArea chatWindow;

    public DataExchange(DataInputStream in, TextArea chatWindow) throws IOException {
        this.in = in;
        this.chatWindow = chatWindow;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String strFromServer = in.readUTF();
                if (strFromServer.equalsIgnoreCase("/end")) {
                    break;
                }
                chatWindow.appendText(strFromServer);
                chatWindow.appendText("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
