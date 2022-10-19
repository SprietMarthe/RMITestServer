package Client;

import Interfaces.ChatService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    ChatService impl = null;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);
    boolean noUserName = true;
    String userName = null;



    public Client() throws RemoteException, InterruptedException {

        try {
            // fire to localhost port 1099
            Registry myRegistry = LocateRegistry.getRegistry("localhost", 1099);

            // search for CounterService
            impl = (ChatService) myRegistry.lookup("Chatservice");

            messageArea.setText("Geef Username");

            textField.setEditable(true);
            messageArea.setEditable(false);
            frame.getContentPane().add(textField, BorderLayout.SOUTH);
            frame.getContentPane().add(new JScrollPane(messageArea), BorderLayout.CENTER);
            frame.pack();



        } catch (Exception e) {
            e.printStackTrace();
        }

        // Send on enter then clear to prepare for next message
        textField.addActionListener(new ActionListener() {
            public synchronized void actionPerformed(ActionEvent e) {
                if(impl != null) {
                    try {
                        if (!noUserName) {
                            impl.sendMessage(textField.getText(),userName);
                            messageArea.append("\n" + "<" + userName + "> " + textField.getText());
                            textField.setText("");
                        }
                        else {
                            userName = textField.getText();
                            impl.connect(userName);
                            noUserName = false;
                            System.out.println("Username: " + textField.getText());
                            userName = textField.getText();
                            messageArea.append("\n" + textField.getText());
                            textField.setText("");
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }
                textField.setText("");
            }
        });
        //System.out.println(impl.receiveMessage(0));

        (new Receiver(impl, messageArea, noUserName)).start();

    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Client client = new Client();
        client.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client.frame.setVisible(true);
    }

    // data doorsturen via return value van u functie
// 3 manieren chatfunctie
    // RMI aan server en client kant, werkt maar stuurt veel void berichten
    // Vanuit de client cte pollen of er nieuwe berichten zijn
    // vanuit client aan server geef mij volgend bericht
    // wait en notify
    // aan server kant (als er geen berichten zijn) client wait
    // of met call back werken,
    // methode namen meegeven en parameters

    private static class Receiver extends Thread {
        int countMessage = 0;
        ChatService impl;
        JTextArea messageArea;
        boolean noUserName;

        public Receiver(ChatService impl, JTextArea messageArea, boolean noUserName){
            this.impl = impl;
            this.messageArea = messageArea;
            this.noUserName = noUserName;
        }
        @Override
        public void start() {
                try {
                    if (!noUserName) {
                        messageArea.append(impl.receiveMessage(countMessage));
                        countMessage++;
                    }
                } catch (RemoteException | InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}