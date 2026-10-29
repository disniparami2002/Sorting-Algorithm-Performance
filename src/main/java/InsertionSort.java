// Uses decreasing gaps to sort values efficiently.
public class ShellSort implements SortingAlgorithm {

    @Override
    public String getName() {
        return "Shell Sort";
    }

    @Override
    public void sort(double[] values) {
        int n = values.length;

        // Reduce the gap until it reaches zero.
        for (int gap = n / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < n; i++) {
                double temp = values[i];
                int j = i;

                // Insert the value into its gap-sorted position.
                while (j >= gap && values[j - gap] > temp) {
                    values[j] = values[j - gap];
                    j -= gap;
                }

                values[j] = temp;
            }
        }
    }
}