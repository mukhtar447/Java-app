package app;


import static app.ProcessSales.log;

import java.util.List;
import java.util.stream.Collectors;

public class ProcessSalesImp implements ProcessSales {
    private OperationdExecutor operationdExecutor;

    public ProcessSalesImp() {
        this.operationdExecutor = new OperationdExecutorImpl();
    }

    @Override
    public void createSale(Message message, List<Sale> sales) {
        String[] command = message.getCommand();
        if (isValidSize(command, 3)) {
            Sale sale = new Sale(command[0], getValue(command[2]));
            sales.add(sale);
        } else {
            logInvalidMessage(message);
        }
    }

    private boolean isValidSize(String[] array, int size) {
        return array.length >= size;
    }

    @Override
    public void createMultipleSales(Message message, List<Sale> sales) {
        String[] command = message.getCommand();
        if (isValidSize(command, 6)) {
            int number = Integer.valueOf(command[0]);
            for (int i = 0; i < number; i++) {
                String type = getValidType(command[3]);
                Sale sale = new Sale(type, getValue(command[5]));
                sales.add(sale);
            }
        } else {
            logInvalidMessage(message);
        }
    }

    @Override
    public void updateSales(Message message, List<Sale> sales) {
        String[] command = message.getCommand();
        if (isValidSize(command, 3)) {
            OperationType operationType = getOperationType(command[0].toLowerCase());
            if (operationType.equals(OperationType.UNKNOW)) {
                logInvalidMessage(message);
                return;
            }
            updateSales(sales, operationType, getValidType(command[2]), getValue(command[1]));
        } else {
            logInvalidMessage(message);
        }
    }

    private void updateSales(List<Sale> sales, OperationType operationType, String type, final Double value) {
        List<Sale> collect = sales.stream().filter(p -> p.getType().equals(type))
            .collect(Collectors.toList());

        collect.forEach(sale -> {
            sale.setValue(operationdExecutor.calculate(operationType, sale.getValue(), value));
        });
    }

    private OperationType getOperationType(String operand) {
        return OperationType.createMessageType(operand);
    }

    private Double getValue(String message) {
        return Double.valueOf(message.replaceAll("[^\\d.]", ""));
    }

    private String getValidType(String s) {
        return s.substring(0, s.length() - 1);
    }

    private void logInvalidMessage(Message message) {log(String.format("Invalid message %s %s", message.getMessageType(), message.getCommand()) );}

}
