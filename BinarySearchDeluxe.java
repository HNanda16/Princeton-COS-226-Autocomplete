/* *****************************************************************************
 *  Name:    Hasit Nanda
 *  NetID:   hasitnanda
 *  Precept: P00
 *
 *
 *  Description:  Program allows a sorted array to be searched using a binary
 *  search and can find the first or last index of the key.
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class BinarySearchDeluxe {

    // Returns the index of the first key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int firstIndexOf(
            Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        int location = -1;
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = Math.floorDiv(low + high, 2);
            if (comparator.compare(key, a[mid]) > 0) {
                low = mid + 1;
            }
            else if (comparator.compare(key, a[mid]) < 0) {
                high = mid - 1;
            }
            else {
                location = mid;
                high = mid - 1;
            }
        }
        return location;
    }

    // Returns the index of the last key in the sorted array a[]
    // that is equal to the search key, or -1 if no such key.
    public static <Key> int lastIndexOf(
            Key[] a, Key key, Comparator<Key> comparator) {
        if (a == null || key == null || comparator == null) {
            throw new IllegalArgumentException("Argument cannot be null");
        }
        int location = -1;
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = Math.floorDiv(low + high, 2);
            if (comparator.compare(key, a[mid]) < 0) {
                high = mid - 1;
            }
            else if (comparator.compare(key, a[mid]) > 0) {
                low = mid + 1;
            }
            else {
                location = mid;
                low = mid + 1;
            }
        }
        return location;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Term a = new Term("a", 1);
        Term b = new Term("b", 1);
        Term c = new Term("c", 1);
        Term d = new Term("d", 1);
        Term e = new Term("e", 1);


        Term[] array = new Term[7];

        array[0] = a;
        array[1] = b;
        array[2] = c;
        array[3] = c;
        array[4] = d;
        array[5] = d;
        array[6] = e;


        Comparator<Term> comparator = Term.byPrefixOrder(2);
        Term key1 = new Term("c", 1);
        Term key2 = new Term("d", 1);

        // Should output 2
        StdOut.println(BinarySearchDeluxe.firstIndexOf(array, key1, comparator));

        // Should output 3
        StdOut.println(BinarySearchDeluxe.lastIndexOf(array, key1, comparator));

        // Should output 4
        StdOut.println(BinarySearchDeluxe.firstIndexOf(array, key2, comparator));

        // Should output 5
        StdOut.println(BinarySearchDeluxe.lastIndexOf(array, key2, comparator));
    }
}
