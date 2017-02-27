package app;

import java.util.List;

public interface ProcessSales {
    void createSale(Message message, List<Sale> sales);

    void createMultipleSales(Message message, List<Sale> sales);

    void updateSales(Message message, List<Sale> sales);

    static void log(String message) {
        System.out.println(message);
    }
}
