public class QuickSort implements SortingAlgorithm {

    @Override
    public String getName() {
        return "Quick Sort";
    }

    @Override
    public void sort(double[] values) {
        quickSort(values, 0, values.length - 1);
    }

    private void quickSort(double[] values, int low, int high) {
        if (low >= high) return;

        int pivotIndex = partition(values, low, high);
        quickSort(values, low, pivotIndex - 1);
        quickSort(values, pivotIndex + 1, high);
    }

    // Lomuto partition: returns the pivot's final index.
    private int partition(double[] values, int low, int high) {
        double pivot = values[high];
        int i = low;

        for (int j = low; j < high; j++) {
            if (values[j] <= pivot) {
                double temp = values[i];
                values[i] = values[j];
                values[j] = temp;
                i++;
            }
        }

        double temp = values[i];
        values[i] = values[high];
        values[high] = temp;

        return i;
    }
}