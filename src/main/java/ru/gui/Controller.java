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
        if (inputLine.getText() != null
                && !inputLine.getText().equals("")) { addMsgToChat(); }
    }

    public void enter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER
                && inputLine.getText() != null
                && !inputLine.getText().equals("")) { addMsgToChat(); }
    }
}
