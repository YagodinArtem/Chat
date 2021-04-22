package ru.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Controller {

    private int SERVER_PORT = 8189;
    private String SERVER_IP = "localhost";
    private Socket socket;

    private DataOutputStream out;
    private DataInputStream in;
    private boolean connected = false;

    @FXML
    TextField inputLine;

    @FXML
    TextArea chatWindow;

    @FXML
    private void initialize() {
        try {
            if (openConnection()) {
                connected = true;
                System.out.println("Connected");
            }
            DataExchange dataExchange = new DataExchange(in, chatWindow);
            dataExchange.setDaemon(true);
            dataExchange.start();
        } catch (IOException e) {
            System.err.println("Not able to open connection");
        } catch (NullPointerException n) {
            System.err.println("Socket | out | in == null");
        }
    }

    private boolean openConnection() throws IOException, NullPointerException {
        socket = new Socket(SERVER_IP, SERVER_PORT);
        out = new DataOutputStream(socket.getOutputStream());
        in = new DataInputStream(socket.getInputStream());

        return socket != null && out != null && in != null;
    }

    private void addMsgToChat() {
        try {
            if (out != null) out.writeUTF(inputLine.getText());
            else chatWindow.appendText(getDate() + " : Server down");
        } catch (IOException e) {
            System.err.println("Can not send messages to server");
        }
        inputLine.setText("");
    }

    public void sendMessage() {
        if (isMsgContainChars()) {
            addMsgToChat();
        }
    }

    public void enter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && isMsgContainChars()) {
            addMsgToChat();
        }
    }

    public boolean isMsgContainChars() {
        if (!inputLine.getText().equals("") && inputLine.getText() != null) {
            char[] chars = inputLine.getText().toCharArray();
            for (char c : chars) if (c != ' ') return true;
        }
        return false;
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }
}
