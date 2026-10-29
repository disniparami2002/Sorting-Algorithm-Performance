// Sorts values by inserting each value into its correct position.
public class InsertionSort implements SortingAlgorithm {

    @Override
    public String getName() {
        return "Insertion Sort";
    }

    @Override
    public void sort(double[] values) {
        for (int i = 1; i < values.length; i++) {
            double key = values[i];
            int j = i - 1;

            // Shift larger values one position to the right.
            while (j >= 0 && values[j] > key) {
                values[j + 1] = values[j];
                j--;
            }

            values[j + 1] = key;
        }
    }
}