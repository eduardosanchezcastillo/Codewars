package eugene.codewars.skyscrappers;

import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class PossiblePositionsProvider {

    private final int dataSize;

    private final Map<Integer, Map<Integer, Set<Integer>>> possiblePositions = new HashMap<>();     // clue -> height -> possible positions

    public static void main(String[] args) {
        PossiblePositionsProvider provider = new PossiblePositionsProvider(4);
        System.out.println(provider.calculate());
    }

    public PossiblePositionsProvider(int dataSize) {
        this.dataSize = dataSize;
    }

    public Map<Integer, Map<Integer, Set<Integer>>> calculate() {
        LinkedList<Integer> data = new LinkedList<>();
        TreeSet<Integer> digits = new TreeSet<>();
        for (int i = 0; i < dataSize; i++) {
            digits.add(i + 1);
        }

        fillData(data, digits);

        while (true) {
            int last = 0;
            while (!data.isEmpty() && data.peek() > last) {
                last = data.pop();
                digits.add(last);
            }

            if (data.isEmpty()) {
                break;
            }

            last = data.pop();
            data.push(digits.higher(last));
            digits.remove(data.peek());
            digits.add(last);
            fillData(data, digits);
        }

        return possiblePositions;
    }

    private void fillData(Deque<Integer> data, TreeSet<Integer> digits) {
        while (data.size() < dataSize) {
            data.push(digits.first());
            digits.remove(data.peek());
        }

        int visibleCount = getVisibleCount(data);

        int index = 0;
        Iterator<Integer> it = data.descendingIterator();
        while (it.hasNext()) {
            Integer height = it.next();

            Set<Integer> set = possiblePositions
                    .computeIfAbsent(visibleCount, k -> new HashMap<>())
                    .computeIfAbsent(height, k -> new HashSet<>());
            set.add(index);

            index++;
        }
    }

    private int getVisibleCount(Deque<Integer> data) {
        int count = 0;
        int maxHeight = 0;

        Iterator<Integer> it = data.descendingIterator();
        while (it.hasNext()) {
            int height = it.next();
            if (height > maxHeight) {
                maxHeight = height;
                count++;
            }
        }

        return count;
    }
}
