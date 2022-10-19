package Server;

import Interfaces.ChatService;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatServiceImpl extends UnicastRemoteObject implements ChatService {

    private List<String> userlist;
    List<String> messages;
    int count = 0;

    public ChatServiceImpl () throws RemoteException{
        userlist = new ArrayList<>();
        messages = new ArrayList<>(1000);
        for(int i = 0; i<1000; i++) {
            messages.add("");
        }
    }

    @Override
    synchronized public void sendMessage(String message, String username) throws RemoteException {
        System.out.println("Send: " + "\n" + "<" + username + "> " + message);
        String s = "<" + username + "> " + message;
        messages.add(s);
        count++;
        System.out.println("Notify");
        notifyAll();
    }

    @Override
    synchronized public String receiveMessage(int i) throws RemoteException, InterruptedException {
        System.out.println("Receive");
        while(count < i) {
            System.out.println("Wait");
            wait();
        }
        return messages.get(i);
    }

    @Override
    public void connect(String me) throws RemoteException {
        userlist.add(me);
    }

    @Override
    public List<String> getUserList() throws RemoteException {
        return userlist;
    }

    public String getStringFromList(int count) {
        if (messages != null)
            if (messages.get(count) != null)
                return messages.get(count);
        else
            return null;
        return null;
    }
}