package eugene.codewars.replWithFunctions.operation;

import eugene.codewars.replWithFunctions.ExecutionContext;

public abstract class Operation {
    private final int priority;
    private final boolean isLeftAssociative;

    Operation(int priority, boolean isLeftAssociative) {
        this.priority = priority;
        this.isLeftAssociative = isLeftAssociative;
    }

    public boolean higherThan(Operation other) {
        if (isLeftAssociative) {
            return priority < other.priority;
        } else {
            return priority <= other.priority;
        }
    }

    public abstract void run(ExecutionContext context);
}
