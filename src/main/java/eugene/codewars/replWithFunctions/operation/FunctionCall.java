package eugene.codewars.replWithFunctions.operation;

import eugene.codewars.replWithFunctions.ExecutionContext;

// So far, this class is only needed to process functions priority correctly.
public class FunctionCall extends Operation {
    public FunctionCall(int priority) {
        super(priority, false);
    }

    @Override
    public void run(ExecutionContext context) {
    }
}
