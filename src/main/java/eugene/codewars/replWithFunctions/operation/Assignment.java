package eugene.codewars.replWithFunctions.operation;

import eugene.codewars.replWithFunctions.ExecutionContext;

public class Assignment extends Operation {
    public Assignment(int priority) {
        super(priority, false);
    }

    @Override
    public void run(ExecutionContext context) {
        Double value = context.popValueAsNumber();
        String name = context.popValueAsName();
        context.getStorage().setVar(name, value);
        context.push(value);
    }
}
