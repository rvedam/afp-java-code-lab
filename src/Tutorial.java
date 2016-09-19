import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by vedam on 9/17/16.
 */
public class Tutorial {

    public static final int ARRAY_SIZE = 10;

    public static void main(String[] args) {
        System.out.println("Hello World");

        // Common pre-Java 8 way of creating a simple range of numbers.
        int arr[] = new int[ARRAY_SIZE];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i;
        }

        // Java 8 way of creating a simple array of numbers from 1 to ARRAY_SIZE
        int[] arrTwo = IntStream.range(0, ARRAY_SIZE).toArray();

        // convert array to a stream and sum all the numbers in the stream
        int sumOfArrTwo = Arrays.stream(arrTwo).sum();
        System.out.println(sumOfArrTwo);

        // Old way of creating anonymous inner class to use in
        IntPredicate isOdd = new IntPredicate() {
            @Override
            public boolean test(int value) {
                return Math.abs(value) % 2 > 0;
            }
        };

        List<Integer> oddNumbersFrom1To10 = IntStream.range(0,10).filter(isOdd).boxed().collect(Collectors.toList());
        System.out.println(oddNumbersFrom1To10);

        // With Java 8 lambdas, the above code snippet can be refactored to the following:
        IntPredicate isEven = (s)-> s % 2 == 0;

        // Lambdas do support functions that are more then one expression like the following:
        IntPredicate isDivisibleByThree = (s) -> {
            if(s % 3 == 0)
                return true;
            return false;
        };


        // you can also make streams into a a parallel.
        OptionalInt sum = Arrays.stream(arr).parallel().filter(isEven).reduce((x, y)->x+y);
        if(sum.isPresent()) {
            System.out.println("Sum: " + sum.getAsInt());
        }

        // Java 6 : Added range-based for loops
//        for(int i : arr) {
//            System.out.println(i);
//        }

        // Java 8: meet forEach and filter (forEach is a map function that allows one to call a method reference
        // or a lambda to perform an action with Each element

        // Streams create a lazy-evaluated that is only executed when terminal operation is called on said pipeline.
        // This means unless one of a few specific functions are called, Everything that is used to transform a stream
        // of objects is not evaluated. You can keep on chaining more and more and nothing will happen unless
        // one of the few terminal operations are called.
        IntStream evenNumbers = Arrays.stream(arr).filter((s)-> s % 2 == 0);
        evenNumbers.forEach(System.out::println); // evenNumbers pipeline will not be evaluated until forEach is called.

        System.out.println();

        // One can also call map on each element going from Stream -> Stream
        List<Integer> doubled = IntStream.range(0,5).map((x)-> 2*x).boxed().collect(Collectors.toList());
        System.out.println(doubled);
    }
}
