package eugene.codewars.replWithFunctions.operation;

import eugene.codewars.replWithFunctions.ExecutionContext;

import java.util.function.DoubleBinaryOperator;

public class Arithmetic extends Operation {
    private final DoubleBinaryOperator operator;

    public Arithmetic(int priority, DoubleBinaryOperator operator) {
        super(priority, true);
        this.operator = operator;
    }

    @Override
    public void run(ExecutionContext context) {
        Double right = context.popValueAsNumber();
        Double left = context.popValueAsNumber();
        Double result = operator.applyAsDouble(left, right);
        context.push(result);
    }
}
