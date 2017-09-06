package eugene.codewars.replWithFunctions;

import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Storage {
    private final Map<String, Double> varMap = new HashMap<>();
    private final Map<String, FunctionDefinition> funcMap = new HashMap<>();

    public void setVar(String name, Double val) {
        if (getFunc(name) != null) {
            throw new InterpreterException("Duplicate identifier '" + name + "'. This name is used by a function.");
        }
        varMap.put(name, val);
    }

    public Double getVar(String name) {
        return varMap.get(name);
    }

    public void setFunc(String name, int parameterCount, Deque<Token> bodyTokens) {
        if (getVar(name) != null) {
            throw new InterpreterException("Duplicate identifier '" + name + "'. This name is used by a variable.");
        }
        funcMap.put(name, new FunctionDefinition(parameterCount, bodyTokens));
    }

    public FunctionDefinition getFunc(String name) {
        return funcMap.get(name);
    }
}
