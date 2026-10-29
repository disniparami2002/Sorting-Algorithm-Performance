import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads data from a CSV file.
 *
 * Features:
 * - Reads column names from the first non-empty line.
 * - Reads the remaining lines as data rows.
 * - Supports quoted fields containing commas.
 * - Detects rows with an incorrect number of fields.
 */
public class CSVReader {

    /**
     * Reads the selected CSV file and returns a Dataset object.
     *
     * @param file CSV file selected by the user
     * @return dataset containing column names and data rows
     * @throws IOException if the file cannot be read or has invalid CSV rows
     */
    public static Dataset readCSV(File file) throws IOException {

        List<String> columns = new ArrayList<>();
        List<String[]> rows = new ArrayList<>();

        // UTF-8 supports common text characters in CSV files.
        try (BufferedReader reader = Files.newBufferedReader(
                file.toPath(), StandardCharsets.UTF_8)) {

            String line;
            boolean firstNonEmptyLine = true;
            int expectedColumns = -1;

            // Read the CSV one line at a time.
            while ((line = reader.readLine()) != null) {

                // Ignore empty lines.
                if (line.trim().isEmpty()) {
                    continue;
                }

                // Split this line into CSV fields.
                List<String> fields = parseCsvLine(line);

                if (firstNonEmptyLine) {

                    // Remove a UTF-8 BOM if it exists.
                    if (!fields.isEmpty()
                            && fields.get(0).startsWith("\uFEFF")) {
                        fields.set(
                                0,
                                fields.get(0).substring(1)
                        );
                    }

                    // The first non-empty line contains column names.
                    for (String field : fields) {
                        columns.add(field.trim());
                    }

                    expectedColumns = columns.size();
                    firstNonEmptyLine = false;

                } else {

                    // Every data row should match the header column count.
                    if (fields.size() != expectedColumns) {
                        throw new IOException(
                                "Invalid CSV row. Expected "
                                + expectedColumns
                                + " fields but found "
                                + fields.size()
                        );
                    }

                    // Save the row as an array of strings.
                    rows.add(fields.toArray(new String[0]));
                }
            }
        }

        // Reject a file that has no header.
        if (columns.isEmpty()) {
            throw new IOException(
                    "The CSV file is empty or has no header row."
            );
        }

        // Return all loaded data inside a Dataset object.
        return new Dataset(columns, rows);
    }

    /**
     * Splits a single CSV line into fields.
     *
     * Commas inside double quotes are treated as part of the field.
     * Example: 1,"Smith, John",25
     *
     * Note: This simple reader does not support quoted fields
     * that continue across multiple lines.
     */
    private static List<String> parseCsvLine(String line)
            throws IOException {

        List<String> fields = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {

                // Two consecutive quotes represent one quote in a field.
                if (inQuotes
                        && i + 1 < line.length()
                        && line.charAt(i + 1) == '"') {

                    current.append('"');
                    i++; // Skip the second quote.

                } else {
                    // Enter or leave a quoted field.
                    inQuotes = !inQuotes;
                }

            } else if (ch == ',' && !inQuotes) {

                // A comma outside quotes separates fields.
                fields.add(current.toString().trim());
                current.setLength(0);

            } else {
                // Add the character to the current field.
                current.append(ch);
            }
        }

        // A quoted field must be closed before the line ends.
        if (inQuotes) {
            throw new IOException(
                    "A quoted CSV field was not closed."
            );
        }

        // Add the final field.
        fields.add(current.toString().trim());

        return fields;
    }
}