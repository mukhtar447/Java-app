package app;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrintReport {
    public void print(List<Sale> sales){
        Map<String, List<Sale>> groups = sales.stream().collect(Collectors.groupingBy(Sale::getType));
        groups.forEach((k, v)->{
            Double total = v.stream().collect(Collectors.summingDouble(Sale::getValue));
            String info = "number of sales of " + k + " is " + v.size() + " and total value is " + total+"p" ;
            System.out.println(info);
        });
    }

    public void printAdjustments(List<String> adjustments){
        System.out.println(" It is pausing, stopped accepting new messages and log a report of the adjustments");
        adjustments.forEach((v)->{
            System.out.println(v);
        });
    }

}
