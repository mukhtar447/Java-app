package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MessageClient {
    public MessageClient() {
    }

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(), 8080);
        MessageClient messageClient = new MessageClient();
        messageClient.connectToServer(socket);
    }

    public void connectToServer(Socket socket) throws IOException {
        BufferedReader reader = null;
        PrintWriter writer = null;
        try {
            while (true) {
                String input;
                reader = new BufferedReader(new InputStreamReader(System.in));
                System.out.print("Enter message: ");
                input = reader.readLine();
                writer = new PrintWriter(socket.getOutputStream(), true);
                writer.println(input);
            }
        } finally {
            writer.close();
            reader.close();
            socket.close();
        }
    }

}
