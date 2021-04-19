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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Controller {

    private int SERVER_PORT = 8189;
    private String SERVER_IP = "localhost";
    private Socket socket;
    private DataOutputStream out;
    private ArrayList<String> messageList;

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
            messageList = new ArrayList<>();
            Thread dataFromServer = new Thread(new DataInputThread(socket, chatWindow));
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
        messageList.add(inputLine.getText());
        try {
            sendMsgToServer();
        } catch (IOException e) {
            System.err.println("Can not send messages to server");
        }
        inputLine.setText("");
    }

    public void sendMsgToServer() throws IOException {

        Thread outputThread = new Thread(new DataOutputThread(out, messageList));
        try {
            outputThread.start();
            outputThread.join();
            outputThread.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        messageList.clear();
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
