import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods for finding numeric columns
 * and extracting their values from a dataset.
 */
public final class NumericColumnUtils {

    // Utility classes do not need public objects.
    private NumericColumnUtils() {
    }

    /**
     * Checks whether a column contains numeric values.
     *
     * Blank cells are ignored, but at least one number must exist.
     * A mixture of text and numbers is not treated as numeric.
     *
     * @param dataset loaded CSV dataset
     * @param columnIndex index of the column to check
     * @return true if the column is numeric
     */
    public static boolean isNumericColumn(
            Dataset dataset, int columnIndex) {

        boolean foundNumber = false;

        for (String[] row : dataset.getRows()) {

            String value = getCell(row, columnIndex);

            // Ignore blank cells.
            if (value.isEmpty()) {
                continue;
            }

            try {
                double number = Double.parseDouble(value);

                // NaN and infinity are not valid benchmark values.
                if (!Double.isFinite(number)) {
                    return false;
                }

                foundNumber = true;

            } catch (NumberFormatException ex) {

                // Any non-numeric value makes this column unsuitable.
                return false;
            }
        }

        // An empty column is not considered numeric.
        return foundNumber;
    }

    /**
     * Extracts numeric values from the selected column.
     *
     * Blank cells are skipped.
     *
     * @param dataset loaded CSV dataset
     * @param columnIndex selected column index
     * @return numeric values as a double array
     */
    public static double[] extractValues(
            Dataset dataset, int columnIndex) {

        List<Double> numbers = new ArrayList<>();

        for (String[] row : dataset.getRows()) {

            String value = getCell(row, columnIndex);

            // Skip missing values.
            if (value.isEmpty()) {
                continue;
            }

            try {
                double number = Double.parseDouble(value);

                if (!Double.isFinite(number)) {
                    throw new NumberFormatException(
                            "Non-finite number"
                    );
                }

                numbers.add(number);

            } catch (NumberFormatException ex) {

                throw new IllegalArgumentException(
                        "Column contains an invalid number: " + value
                );
            }
        }

        // Convert the list to a primitive double array.
        double[] result = new double[numbers.size()];

        for (int i = 0; i < numbers.size(); i++) {
            result[i] = numbers.get(i);
        }

        return result;
    }

    /**
     * Safely returns a cell value.
     * Returns an empty string if the cell is missing.
     */
    private static String getCell(
            String[] row, int columnIndex) {

        if (columnIndex < 0 || columnIndex >= row.length) {
            return "";
        }

        if (row[columnIndex] == null) {
            return "";
        }

        return row[columnIndex].trim();
    }
}