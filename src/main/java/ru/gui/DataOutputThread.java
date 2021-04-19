package ru.gui;

import java.io.DataOutputStream;
import java.util.ArrayList;

public class DataOutputThread implements Runnable {

    private DataOutputStream out;
    private ArrayList<String> messageList;

    public DataOutputThread(DataOutputStream out, ArrayList<String> messageList) {
        this.out = out;
        this.messageList = messageList;
    }

    @Override
    public void run() {
        try {
            for (String s : messageList) {
                out.writeUTF(s);
            }
            messageList.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}