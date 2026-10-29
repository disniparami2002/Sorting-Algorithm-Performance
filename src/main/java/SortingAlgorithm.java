/**
 * Common interface for all sorting algorithms.
 *
 * Each algorithm must:
 * 1. Provide its name.
 * 2. Sort a double array in ascending order.
 */
public interface SortingAlgorithm {

    /**
     * Returns the algorithm's display name.
     */
    String getName();

    /**
     * Sorts the given array in ascending order.
     *
     * The array itself is modified.
     */
    void sort(double[] values);
}