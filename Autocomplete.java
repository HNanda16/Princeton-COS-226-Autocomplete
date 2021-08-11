/* *****************************************************************************
 *  Name:    Hasit Nanda
 *  NetID:   hasitnanda
 *  Precept: P00
 *
 *  Description: Program implements a data type that provides autocomplete
 *  functionality for a given set of string and weights
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Autocomplete {

    // sorted terms
    private Term[] sortedTerms;


    // Initializes the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        for (int i = 0; i < terms.length; i++) {
            if (terms[i] == null) {
                throw new IllegalArgumentException("Entry cannot be null");
            }
        }

        // creating a copy to ensure immutability
        sortedTerms = new Term[terms.length];
        for (int i = 0; i < sortedTerms.length; i++) {
            sortedTerms[i] = terms[i];
        }
        Arrays.sort(sortedTerms);
    }

    // Returns all terms that start with the given prefix, in descending order
    // of weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Comparator<Term> prefixComparator = Term.byPrefixOrder(prefix.length());
        Term key = new Term(prefix, 0);

        // creating a copy to ensure immutability
        Term[] copy = new Term[sortedTerms.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = sortedTerms[i];
        }

        int start = BinarySearchDeluxe.firstIndexOf(
                copy, key, prefixComparator);
        int end = BinarySearchDeluxe.lastIndexOf(
                copy, key, prefixComparator);

        if (start == -1 || end == -1) {
            Term[] matching = new Term[0];
            return matching;
        }

        Term[] matching = new Term[end - start + 1];

        for (int i = start; i <= end; i++) {
            matching[i - start] = copy[i];
        }

        Comparator<Term> weightComparator = Term.byReverseWeightOrder();

        Arrays.sort(matching, weightComparator);

        return matching;
    }

    // Returns the number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        Comparator<Term> prefixComparator = Term.byPrefixOrder(prefix.length());
        Term key = new Term(prefix, 0);

        // creating a copy to ensure immutability
        Term[] copy = new Term[sortedTerms.length];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = sortedTerms[i];
        }

        int start = BinarySearchDeluxe.firstIndexOf(
                copy, key, prefixComparator);
        int end = BinarySearchDeluxe.lastIndexOf(
                copy, key, prefixComparator);

        return end - start + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {

        // read in the terms from a file
        String filename = args[0];
        In in = new In(filename);
        int n = in.readInt();
        Term[] terms = new Term[n];
        for (int i = 0; i < n; i++) {
            long weight = in.readLong();           // read the next weight
            in.readChar();                         // scan past the tab
            String query = in.readLine();          // read the next query
            terms[i] = new Term(query, weight);    // construct the term
        }

        // read in queries from standard input and print the top k matching terms
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            StdOut.printf("%d matches\n", autocomplete.numberOfMatches(prefix));
            for (int i = 0; i < Math.min(k, results.length); i++)
                StdOut.println(results[i]);
        }
    }

}
