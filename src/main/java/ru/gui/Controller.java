package ru.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    private Thread dataFromServer;
    private DataOutputStream out;

    @FXML
    TextField inputLine;

    @FXML
    TextArea chatWindow;

    @FXML
    private void initialize() {
        openConnection();
    }

    private void openConnection() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            out = new DataOutputStream(socket.getOutputStream());
            dataFromServer = new Thread(new DataInputThread(socket, chatWindow));
            dataFromServer.start();
        } catch (IOException e) {
            System.err.println("Not able to open connection");
        }
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    private void addMsgToChat() {
        chatWindow.appendText(getDate() + ": " + inputLine.getText() + "\n");
        try {
            out.writeUTF(inputLine.getText());
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
}
