package ru.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DataInputThread implements Runnable {

    private DataInputStream in;

    @FXML
    TextArea chatWindow;

    public DataInputThread(Socket socket, TextArea chatWindow) throws IOException {
        in = new DataInputStream(socket.getInputStream());
        this.chatWindow = chatWindow;
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    @Override
    public void run() {
        try {
            while (true) {
                chatWindow.appendText(getDate() + " Server: " + in.readUTF() + "\n");
            }
        } catch (SocketException e) {
            System.err.println("socket ex");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
