package eugene.codewars;

import org.junit.Test;

import static org.junit.Assert.*;

public class NextSmallerNumberTest {

    @Test
    public void basicTests() {
        assertEquals(12, NextSmallerNumber.nextSmaller(21));
        assertEquals(790, NextSmallerNumber.nextSmaller(907));
        assertEquals(513, NextSmallerNumber.nextSmaller(531));
        assertEquals(-1, NextSmallerNumber.nextSmaller(1027));
        assertEquals(414, NextSmallerNumber.nextSmaller(441));
        assertEquals(123456789, NextSmallerNumber.nextSmaller(123456798));
    }

    @Test
    public void extendedTests() {
        assertEquals(351, NextSmallerNumber.nextSmaller(513));
        assertEquals(315, NextSmallerNumber.nextSmaller(351));
        assertEquals(153, NextSmallerNumber.nextSmaller(315));
        assertEquals(135, NextSmallerNumber.nextSmaller(153));
        assertEquals(-1, NextSmallerNumber.nextSmaller(135));
        assertEquals(2017, NextSmallerNumber.nextSmaller(2071));
        assertEquals(1072, NextSmallerNumber.nextSmaller(1207));
        assertEquals(144, NextSmallerNumber.nextSmaller(414));

        assertEquals(-1, NextSmallerNumber.nextSmaller(123456789));
        assertEquals(1234567890, NextSmallerNumber.nextSmaller(1234567908));
        assertEquals(-1, NextSmallerNumber.nextSmaller(9999999999L));
        assertEquals(59884848459853L, NextSmallerNumber.nextSmaller(59884848483559L));
        assertEquals(-1, NextSmallerNumber.nextSmaller(1023456789));
        assertEquals(51226262627551L, NextSmallerNumber.nextSmaller(51226262651257L));
        assertEquals(-1, NextSmallerNumber.nextSmaller(202233445566L));
        assertEquals(-1, NextSmallerNumber.nextSmaller(506789));
    }

    public static long solution(long n)
    {
        char[] nArray = (n + "").toCharArray();

        int i = nArray.length - 1;
        while(i > 0 && nArray[i-1] <= nArray[i])
        {
            i--;
        }

        if(i == 0)
        {
            return -1;
        }
        else
        {
            int from = i-1;
            int end = nArray.length-1;

            int nextHigherIndex = from;
            int nextHigherDiff = 9;
            for(int o=from+1;o<=end;o++)
            {
                int diff = nArray[from] - nArray[o];
                if(diff>0 && diff<nextHigherDiff)
                {
                    nextHigherDiff = diff;
                    nextHigherIndex = o;
                }
            }
            if(nextHigherIndex == from)
            {
                char temp = nArray[i-1];
                nArray[i-1] = nArray[i];
                nArray[i] = temp;
            }
            else
            {
                char temp = nArray[i-1];
                nArray[i-1] = nArray[nextHigherIndex];
                nArray[nextHigherIndex] = temp;
                for(int o=i;o<nArray.length;o++)
                {
                    for(int p=i;p<nArray.length-1;p++)
                    {
                        if(nArray[p]<nArray[p+1])
                        {
                            temp = nArray[p];
                            nArray[p] = nArray[p+1];
                            nArray[p+1] = temp;
                        }
                    }
                }
            }
        }
        if(nArray[0] == '0')
        {
            return -1;
        }
        return Long.parseLong(String.valueOf(nArray));
    }

    @Test
    public void randomTests() {
        for(int i = 0; i < 200; ++i)
        {
            long n = (long)Math.exp(43 * Math.random());

            assertEquals(solution(n), NextSmallerNumber.nextSmaller(n));
        }
    }    
}