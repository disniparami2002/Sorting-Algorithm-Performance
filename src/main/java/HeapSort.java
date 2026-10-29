// Sorts values using a binary max-heap.
public class HeapSort implements SortingAlgorithm {

    @Override
    public String getName() {
        return "Heap Sort";
    }

    @Override
    public void sort(double[] values) {
        int n = values.length;

        // Build a max-heap.
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(values, n, i);
        }

        // Move the largest value to the end repeatedly.
        for (int i = n - 1; i > 0; i--) {
            double temp = values[0];
            values[0] = values[i];
            values[i] = temp;

            heapify(values, i, 0);
        }
    }

    private void heapify(double[] values, int size, int root) {
        int largest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        // Find the largest among the root and its children.
        if (left < size && values[left] > values[largest]) {
            largest = left;
        }

        if (right < size && values[right] > values[largest]) {
            largest = right;
        }

        // Restore the max-heap if needed.
        if (largest != root) {
            double temp = values[root];
            values[root] = values[largest];
            values[largest] = temp;

            heapify(values, size, largest);
        }
    }
}