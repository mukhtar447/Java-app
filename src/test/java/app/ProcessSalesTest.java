package app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
public class ProcessSalesTest {
    private ProcessSales processSales;
    private List<Sale> sales;

    @Before
    public void setup(){
        processSales = new ProcessSalesImp();
        sales = new ArrayList<>();
    }

    @Test
    public void test_createSale(){
        Message message = Message.from("Message Type 1 – apple at 10p");
        Message message2= Message.from( "Message Type 1 – samsung at 20p");
        Message message3= Message.from( "Message Type 1 – samsung at 30p");
        assertThat(message.getMessageType(), is(MessageType.MESSAGE_TYPE_1));
        assertThat(message2.getMessageType(), is(MessageType.MESSAGE_TYPE_1));
        assertThat(message3.getMessageType(), is(MessageType.MESSAGE_TYPE_1));
        processSales.createSale(message,sales);
        processSales.createSale(message2,sales);
        processSales.createSale(message3,sales);
        assertThat(sales.size(), is(3));
        assertThat(sales.stream().filter(p->p.getType().equals("samsung"))
            .count(), is(2l));
        assertThat(sales.stream().filter(p->p.getType().equals("samsung"))
            .collect(Collectors.summingDouble(Sale::getValue)), is(50.0));
        assertThat(sales.stream().filter(p->p.getType().equals("apple"))
            .collect(Collectors.summingDouble(Sale::getValue)), is(10.0));

    }
    @Test
    public void test_createMultipleSales(){
        Message message = Message.from("Message Type 1 – apple at 10p");
        Message message2 = Message.from("Message Type 1 – samsung at 20p");

        Message message3 = Message.from("Message Type 2 – 20 sales of apples at 10p each.");
        Message message4 = Message.from("Message Type 2 – 15 sales of samsungs at 10p each.");
        assertThat(message.getMessageType(), is(MessageType.MESSAGE_TYPE_1));
        assertThat(message2.getMessageType(), is(MessageType.MESSAGE_TYPE_1));
        assertThat(message3.getMessageType(), is(MessageType.MESSAGE_TYPE_2));
        assertThat(message4.getMessageType(), is(MessageType.MESSAGE_TYPE_2));
        processSales.createSale(message,sales);
        processSales.createSale(message2,sales);
        processSales.createMultipleSales(message3,sales);
        processSales.createMultipleSales(message4,sales);
        assertThat(sales.size(), is(37));
    }
    @Test
    public void test_updateSales(){
        Message message = Message.from("Message Type 1 – apple at 10p");
        Message message2 = Message.from("Message Type 1 – samsung at 20p");
        Message message3 = Message.from("Message Type 1 – samsung at 30p");
        Message message4 = Message.from("Message Type 3 – Add 20p apples");
        Message message5 = Message.from("Message Type 3 – Add 10p samsungs");
        processSales.createSale(message,sales);
        processSales.createSale(message2,sales);
        processSales.createSale(message3,sales);
        processSales.updateSales(message4,sales);
        processSales.updateSales(message5,sales);
        assertThat(sales.size(), is(3));
        assertThat(sales.stream().filter(p->p.getType().equals("apple"))
            .collect(Collectors.summingDouble(Sale::getValue)), is(30.0));
        assertThat(sales.stream().filter(p->p.getType().equals("samsung"))
            .collect(Collectors.summingDouble(Sale::getValue)), is(70.0));

    }

}