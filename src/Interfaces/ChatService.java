package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ChatService extends Remote {

    void sendMessage(String message, String username) throws RemoteException;

    String receiveMessage(int i) throws RemoteException, InterruptedException;

    void connect(String username) throws RemoteException;

    List<String> getUserList() throws RemoteException;

    String getStringFromList(int countMessage) throws RemoteException;
}
