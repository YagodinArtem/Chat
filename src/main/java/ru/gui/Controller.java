package ru.gui;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Controller {

    @FXML
    TextField inputLine;

    @FXML
    TextArea chatWindow;

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

    private void addMsgToChat() {
        chatWindow.appendText(getDate() + ": " + inputLine.getText() + "\n");
        inputLine.setText("");
    }

    public void sendMessage(MouseEvent event) {
        if (isMsgContainChars()) { addMsgToChat(); }
    }

    public void enter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER && isMsgContainChars()) { addMsgToChat(); }
    }

    public boolean isMsgContainChars() {
        if (!inputLine.getText().equals("") && inputLine.getText() != null) {
            char[] chars = inputLine.getText().toCharArray();
            for (char c : chars) if (c != ' ') return true;
        }
        return false;
    }
}
