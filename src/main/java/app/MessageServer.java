package app;

import static app.ProcessSales.log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessageServer implements Runnable {
    private Socket socket;
    private List<String> messages = new ArrayList<>();
    private List<Sale> sales = new ArrayList<>();
    private List<String> adjustments = new ArrayList<>();
    private ProcessSales processSales;
    private PrintReport printReport;
    private int numberOfMessage = 0;
    private static final int LOG_TIME = 10;
    private static final int PAUSING = 50;

    public MessageServer(Socket socket) {
        this.socket = socket;
        processSales = new ProcessSalesImp();
        printReport = new PrintReport();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket server = new ServerSocket(8080);
        ExecutorService executor = null;
        try {
            executor = Executors.newSingleThreadExecutor();
            executor.execute(new MessageServer(server.accept()));
        } finally {
            server.close();
            executor.shutdown();
        }
    }

    public void start() {
        try {
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                numberOfMessage++;
                if (numberOfMessage == PAUSING) {
                    log("Pausing time ");
                    printReport.printAdjustments(adjustments);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    messages.add(line);
                    Message message = Message.from(line);
                    if (message == null) {
                        log("invalid message  type: " + line);
                    } else {
                        processMessage(message);
                    }
                    if (numberOfMessage == LOG_TIME) {
                        printReport.print(sales);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            log("IOException" + ": " + e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                log("Closing problem");
            }
            log("closed");
        }
    }

    private void processMessage(Message message) {
        switch (message.getMessageType()) {
        case MESSAGE_TYPE_1:
            processSales.createSale(message, sales);
            break;
        case MESSAGE_TYPE_2:
            processSales.createMultipleSales(message, sales);
            break;
        case MESSAGE_TYPE_3:
            adjustments.add(String.join(" ", message.getCommand()));
            processSales.updateSales(message, sales);
            break;
        default:
            numberOfMessage--;
        }
    }

    public void run() {
        start();
    }

}
