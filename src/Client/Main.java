package Client;

import Interfaces.Counter;

import javax.swing.*;
import java.io.PrintWriter;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    PrintWriter out;
    JFrame frame = new JFrame("Chatter");
    JTextField textField = new JTextField(50);
    JTextArea messageArea = new JTextArea(16, 50);


    private void doTest(){
        try {
            // fire to localhost port 1099
            Registry myRegistry = LocateRegistry.getRegistry("localhost", 1099);

            // search for CounterService
            Counter impl = (Counter) myRegistry.lookup("CounterService");

            // call server's method
            System.out.println(impl.berekenSom(10, 5));
            System.out.println(impl.berekenVerschil(10,5));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.doTest();
    }
}