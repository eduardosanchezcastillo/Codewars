package eugene.codewars;

/*
    https://www.codewars.com/kata/psychic/java
    Psychic

    A common exercise, when you're learning a new language, is to make a guessing game. It's a great way to learn control structures, IO, the works.
    This is taking the guessing game to a whole new level. This time, you're the one playing the guessing game. And the guessing game is Math.random().
    The task is really simple. You make a guess, Math.random() does it's thing, and if you're right 5 times out of 5, you win!

    Hint: You guess first.
 */

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Random;

public class Psychic {

    public static double guess() {
        try {
            CheatRandom cheatRandom = new CheatRandom();

            Class<?> clazz = Class.forName("java.lang.Math$RandomNumberGeneratorHolder");
            Field field = clazz.getDeclaredField("randomNumberGenerator");
            field.setAccessible(true);

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);

            field.set(null, cheatRandom);
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return Math.random();
    }

    private static class CheatRandom extends Random {
        @Override
        public double nextDouble() {
            return 0.1;
        }
    }
}
