package Server;

import Interfaces.Counter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CounterImpl extends UnicastRemoteObject implements Counter {

    public CounterImpl () throws RemoteException{}

    @Override
    public int berekenSom(int a, int b) throws RemoteException{
        return a+b;
    }

    @Override
    public int berekenVerschil(int a, int b) throws RemoteException {
        return a-b;
    }
}