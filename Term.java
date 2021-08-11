/* *****************************************************************************
 *  Name:    Hasit Nanda
 *  NetID:   hasitnanda
 *  Precept: P00

 *
 *  Description:  Program implements immutable term data structure which
 * represents an autocomplete term consisting of a query string and an
 * associated integer weight.
 *
 *
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Term implements Comparable<Term> {
    // query
    private String queryCopy;
    // weight
    private long weightCopy;

    // Initializes a term with the given query string and weight.
    public Term(String query, long weight) {
        if (query == null || weight < 0) {
            throw new IllegalArgumentException(
                    "query cannot be null and weight must be nonnegative");
        }
        queryCopy = query;
        weightCopy = weight;
    }

    // nested class for byReverseWeightOrder
    private static class ReverseWeightOrder implements Comparator<Term> {
        public int compare(Term a, Term b) {
            return Double.compare(b.weightCopy, a.weightCopy);
        }
    }

    // Compares the two terms in descending order by weight.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // nested class for byPrefixOrder
    private static class PrefixOrder implements Comparator<Term> {
        // prefix length
        private int prefixLength;

        // constructor
        public PrefixOrder(int r) {
            prefixLength = r;
        }

        public int compare(Term a, Term b) {
            int i = -1;

            do {
                i++;
                if (a.queryCopy.length() <= i) {
                    return a.queryCopy.length() + Math.max(
                            -b.queryCopy.length(), -prefixLength);
                }
                if (b.queryCopy.length() <= i) {
                    return -b.queryCopy.length() + Math.min(
                            a.queryCopy.length(), prefixLength);
                }
            } while (a.queryCopy.charAt(i) == b.queryCopy.charAt(
                    i) && i < prefixLength - 1);

            return a.queryCopy.charAt(i) - b.queryCopy.charAt(i);
        }
    }

    // Compares the two terms in lexicographic order,
    // but using only the first r characters of each query.
    public static Comparator<Term> byPrefixOrder(int r) {
        if (r < 0) {
            throw new IllegalArgumentException("argument cannot be negative");
        }
        return new PrefixOrder(r);
    }

    // Compares the two terms in lexicographic order by query.
    public int compareTo(Term that) {
        return queryCopy.compareTo(that.queryCopy);
    }

    // Returns a string representation of this term in the following format:
    // the weight, followed by a tab, followed by the query.
    public String toString() {
        return (weightCopy + "\t" + queryCopy);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term a = new Term("dog", 1);
        Term b = new Term("d", 2);
        Term c = new Term("dogfight", 5);
        Term d = new Term("c", 1);

        // Should output 2
        StdOut.println(a.compareTo(b));

        Comparator<Term> comparator1 = byPrefixOrder(3);

        // Should output 0
        StdOut.println(comparator1.compare(a, c));

        // Should output 1
        StdOut.println(b.compareTo(d));

        // Should output -5
        StdOut.println(a.compareTo(c));

        Comparator<Term> comparator2 = byReverseWeightOrder();

        // Should output 0
        StdOut.println(comparator2.compare(a, d));

        // Should output 1
        StdOut.println(comparator2.compare(a, c));
    }

}
