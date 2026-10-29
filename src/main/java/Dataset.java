import java.util.List;

/**
 * Dataset class:
 * Stores the column headings and data rows loaded from a CSV file.
 */
public class Dataset {

    // Stores the names of the CSV columns.
    private final List<String> columnNames;

    // Stores each data row as an array of strings.
    private final List<String[]> rows;

    /**
     * Creates a dataset using column names and data rows.
     */
    public Dataset(List<String> columnNames, List<String[]> rows) {
        this.columnNames = columnNames;
        this.rows = rows;
    }

    /**
     * Returns all column names.
     */
    public List<String> getColumnNames() {
        return columnNames;
    }

    /**
     * Returns all data rows.
     */
    public List<String[]> getRows() {
        return rows;
    }
}