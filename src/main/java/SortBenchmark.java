import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Benchmarks the five sorting algorithms.
 *
 * Every algorithm receives its own copy of the original data.
 * Execution time is measured using System.nanoTime().
 */
public final class SortBenchmark {

    // Prevent creating objects of this utility class.
    private SortBenchmark() {
    }

    /**
     * Runs all five algorithms on the same original values.
     *
     * @param original unsorted numeric values
     * @return benchmark results for all algorithms
     */
    public static List<BenchmarkResult> compare(double[] original) {

        List<BenchmarkResult> results = new ArrayList<>();

        // Register the sorting algorithms that will be compared.
        SortingAlgorithm[] algorithms = {
            new InsertionSort(),
            new ShellSort(),
            new MergeSort(),
            new QuickSort(),
            new HeapSort()
        };

        // Use a small warm-up to reduce some JVM startup effects.
        // Each algorithm gets its own copy of the warm-up data.
        double[] warmup = Arrays.copyOf(
                original,
                Math.min(original.length, 1000)
        );

        for (SortingAlgorithm algorithm : algorithms) {
            algorithm.sort(warmup.clone());
        }

        // Benchmark each algorithm separately.
        for (SortingAlgorithm algorithm : algorithms) {

            // Keep the original array unchanged.
            double[] copy = original.clone();

            // Start the timer.
            long start = System.nanoTime();

            // Execute the algorithm being measured.
            algorithm.sort(copy);

            // Stop the timer and calculate elapsed time.
            long elapsed = System.nanoTime() - start;

            // Never report a result if sorting was incorrect.
            if (!isSorted(copy)) {
                throw new IllegalStateException(
                        algorithm.getName()
                        + " produced an unsorted result."
                );
            }

            // Store the name, elapsed time and sorted output.
            results.add(
                    new BenchmarkResult(
                            algorithm.getName(),
                            elapsed,
                            copy
                    )
            );
        }

        return results;
    }

    /**
     * Checks whether values are sorted in ascending order.
     */
    private static boolean isSorted(double[] values) {

        for (int i = 1; i < values.length; i++) {

            if (values[i - 1] > values[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * Holds the measured information for one algorithm.
     */
    public static class BenchmarkResult {

        private final String algorithmName;
        private final long elapsedNanos;
        private final double[] sortedValues;

        public BenchmarkResult(
                String algorithmName,
                long elapsedNanos,
                double[] sortedValues) {

            this.algorithmName = algorithmName;
            this.elapsedNanos = elapsedNanos;
            this.sortedValues = sortedValues;
        }

        public String getAlgorithmName() {
            return algorithmName;
        }

        public long getElapsedNanos() {
            return elapsedNanos;
        }

        public double[] getSortedValues() {
            return sortedValues;
        }

        /**
         * Converts nanoseconds to milliseconds for display.
         */
        public double getElapsedMillis() {
            return elapsedNanos / 1_000_000.0;
        }
    }
}