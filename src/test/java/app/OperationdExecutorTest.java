package app;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class OperationdExecutorTest {
    private OperationdExecutor operationdExecutor;

    @Before
    public void setup() {
        operationdExecutor = new OperationdExecutorImpl();
    }

    @Test
    public void tes_execute_add_operation() {
        Double calculate = operationdExecutor.calculate(OperationType.ADD, 5.0, 8.0);
        assertThat(calculate, is(13.0));
    }

    @Test
    public void tes_execute_subtract_operation() {
        Double calculate = operationdExecutor.calculate(OperationType.SUBTRACT, 8.0, 5.0);
        assertThat(calculate, is(3.0));
    }

    @Test
    public void tes_execute_multiply_operation() {
        Double calculate = operationdExecutor.calculate(OperationType.MULTIPLY, 8.0, 5.0);
        assertThat(calculate, is(40.0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void tes_execute_unknow_operation() {
        operationdExecutor.calculate(OperationType.UNKNOW, 8.0, 5.0);
    }
}