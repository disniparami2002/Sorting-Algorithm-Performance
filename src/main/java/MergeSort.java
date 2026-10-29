// Sorts values using the divide-and-conquer method.
public class MergeSort implements SortingAlgorithm {

    @Override
    public String getName() {
        return "Merge Sort";
    }

    @Override
    public void sort(double[] values) {
        if (values.length > 1) {
            mergeSort(values, 0, values.length - 1);
        }
    }

    private void mergeSort(double[] values, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;

        // Sort the left and right halves.
        mergeSort(values, left, middle);
        mergeSort(values, middle + 1, right);

        // Merge both sorted halves.
        merge(values, left, middle, right);
    }

    private void merge(double[] values, int left, int middle, int right) {
        double[] temp = new double[right - left + 1];
        int i = left;
        int j = middle + 1;
        int k = 0;

        while (i <= middle && j <= right) {
            if (values[i] <= values[j]) {
                temp[k++] = values[i++];
            } else {
                temp[k++] = values[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = values[i++];
        }

        while (j <= right) {
            temp[k++] = values[j++];
        }

        // Copy the merged values back into the original array.
        System.arraycopy(temp, 0, values, left, temp.length);
    }
}