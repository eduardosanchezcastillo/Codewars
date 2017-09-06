package eugene.codewars.whitespace;

import eugene.codewars.whitespace.dto.PreparedCodeDTO;
import eugene.codewars.whitespace.exception.WhitespaceRuntimeException;
import eugene.codewars.whitespace.language.WhitespaceCommandPath;
import eugene.codewars.whitespace.runner.CommandRunnerCodeWars;
import eugene.codewars.whitespace.runner.FirstPassRunner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Interpreter {
    public static String unbleach(String code) {
        return code != null
                ? code.replace(' ', 's').replace('\t', 't').replace('\n', 'n')
                : null;
    }

    static String execute(String code, InputStream input) {
        return execute(code, input, null);
    }

    static String execute(String code, InputStream input, OutputStream output) {
        if (output == null) {
            output = new ByteArrayOutputStream();
        }

        String outputString;
        try {
            output.flush();

            // First pass: prepare code for execution and (optionally) visualize it.
            CommandRunner firstPassRunner = new FirstPassRunner(code, COMMAND_PATH, false);
            firstPassRunner.run();

            // Second pass: run the code
            PreparedCodeDTO codeDTO = new PreparedCodeDTO(code, firstPassRunner.getMarks(), COMMAND_PATH);
            CommandRunner commandRunner = new CommandRunnerCodeWars(codeDTO, input, output);
            outputString = commandRunner.run();
        } catch (IOException e) {
            throw new WhitespaceRuntimeException("Problem with output stream.", e);
        } finally {
            try {
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return outputString;
    }

    private static final WhitespaceCommandPath COMMAND_PATH = new WhitespaceCommandPath()
            .addCommand("  ", CommandRunner::stackPush)
            .addCommand(" \t ", CommandRunner::stackDuplicateNth)
            .addCommand(" \t\n", CommandRunner::stackDiscardTopN)
            .addCommand(" \n ", CommandRunner::stackDuplicateTop)
            .addCommand(" \n\t", CommandRunner::stackSwap)
            .addCommand(" \n\n", CommandRunner::stackDiscardTop)

            .addCommand("\t   ", CommandRunner::arithmeticAdd)
            .addCommand("\t  \t", CommandRunner::arithmeticSubtract)
            .addCommand("\t  \n", CommandRunner::arithmeticMultiply)
            .addCommand("\t \t ", CommandRunner::arithmeticDivide)
            .addCommand("\t \t\t", CommandRunner::arithmeticModulo)

            .addCommand("\t\t ", CommandRunner::heapWrite)
            .addCommand("\t\t\t", CommandRunner::heapRead)

            .addCommand("\t\n  ", CommandRunner::outputChar)
            .addCommand("\t\n \t", CommandRunner::outputNumber)
            .addCommand("\t\n\t ", CommandRunner::inputChar)
            .addCommand("\t\n\t\t", CommandRunner::inputNumber)

            .addCommand("\n  ", CommandRunner::flowMark)
            .addCommand("\n \t", CommandRunner::flowCallFunction)
            .addCommand("\n \n", CommandRunner::flowJump)
            .addCommand("\n\t ", CommandRunner::flowJumpIfZero)
            .addCommand("\n\t\t", CommandRunner::flowJumpIfNegative)
            .addCommand("\n\t\n", CommandRunner::flowExitFunction)
            .addCommand("\n\n\n", CommandRunner::flowExitProgram);
}
