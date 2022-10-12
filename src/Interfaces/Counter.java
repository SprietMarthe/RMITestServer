package Interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Counter extends Remote {
    int berekenSom(int a, int b) throws RemoteException;
    int berekenVerschil(int a, int b) throws RemoteException;
}

// sendMessage()
// receiveMessage()

// connect()
// getUserList()