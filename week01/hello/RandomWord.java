import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        int count = 0;
        String champion = "";
        // Read the strings from the standard input
        while (!StdIn.isEmpty()) {
            count++;
            String word = StdIn.readString();
            champion = StdRandom.bernoulli(1 / (double) count) ? word : champion;
        }
        StdOut.println(champion);
    }
}
