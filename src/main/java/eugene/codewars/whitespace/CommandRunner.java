package eugene.codewars.whitespace;

import java.util.Map;

public interface CommandRunner {

    String run();

    Map<String, Integer> getMarks();

    void stackPush();

    void stackDuplicateNth();

    void stackDiscardTopN();

    void stackDuplicateTop();

    void stackSwap();

    void stackDiscardTop();

    void arithmeticAdd();

    void arithmeticSubtract();

    void arithmeticMultiply();

    void arithmeticDivide();

    void arithmeticModulo();

    void heapWrite();

    void heapRead();

    void outputChar();

    void outputNumber();

    void inputChar();

    void inputNumber();

    void flowMark();

    void flowCallFunction();

    void flowJump();

    void flowJumpIfZero();

    void flowJumpIfNegative();

    void flowExitFunction();

    void flowExitProgram();
}
