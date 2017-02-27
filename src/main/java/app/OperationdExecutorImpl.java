package app;

import java.util.function.Function;

public class OperationdExecutorImpl implements OperationdExecutor {
    public Function<Double, Function<Double, Double>> add = x -> y -> x + y;
    public Function<Double, Function<Double, Double>> subtract = x -> y -> x - y;
    public Function<Double, Function<Double, Double>> multiply = x -> y -> x * y;

    @Override
    public Double calculate(OperationType operationType, Double x, Double y) {
        return operand(operationType).apply(x).apply(y);
    }

    Function<Double, Function<Double, Double>> operand(OperationType operationType) {
        switch (operationType) {
        case ADD:
            return add;
        case SUBTRACT:
            return subtract;
        case MULTIPLY:
            return multiply;
        default:
                throw new IllegalArgumentException();
        }
    }

}
