import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Comparator;

/**
 * Main class for the Sorting Algorithm Performance application.
 *
 * Features:
 * 1. Upload CSV datasets.
 * 2. Display CSV column names.
 * 3. Detect numeric columns.
 * 4. Select a column for sorting.
 * 5. Compare five sorting algorithms.
 * 6. Display execution times and sorted values.
 * 7. Highlight the fastest algorithm.
 */
public class Main {

    // Stores the currently loaded CSV dataset.
    private Dataset dataset;

    // Stores all CSV column names for display.
    private final DefaultListModel<String> allColumnsModel =
            new DefaultListModel<>();

    private final JList<String> allColumnsList =
            new JList<>(allColumnsModel);

    // Dropdown for selecting a numeric column.
    private final JComboBox<String> numericColumnCombo =
            new JComboBox<>();

    // Displays the uploaded file name and row count.
    private final JLabel fileStatus =
            new JLabel("No CSV file loaded.");

    // Displays a summary of the benchmark results.
    private final JLabel resultSummary =
            new JLabel("Upload a CSV file and compare the algorithms.");

    // Displays a preview of the sorted numeric values.
    private final JTextArea resultPreview =
            new JTextArea(6, 60);

    // Table for displaying algorithm performance results.
    private final DefaultTableModel resultTableModel =
            new DefaultTableModel(
                    new Object[]{
                        "Rank",
                        "Algorithm",
                        "Time (ms)",
                        "Time (ns)",
                        "Status"
                    },
                    0
            ) {

                // Prevent users from editing benchmark results.
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

    private final JTable resultTable =
            new JTable(resultTableModel);

    /**
     * Starts the application.
     */
    public static void main(String[] args) {

        // Create Swing components on the Event Dispatch Thread.
        SwingUtilities.invokeLater(() -> {
            new Main().createAndShowGui();
        });
    }

    /**
     * Creates and displays the main application window.
     */
    private void createAndShowGui() {

        JFrame frame = new JFrame(
                "Sorting Algorithm Performance"
        );

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1050, 800);
        frame.setMinimumSize(new Dimension(850, 600));
        frame.setLocationRelativeTo(null);

        // Application heading.
        JLabel title = new JLabel(
                "Sorting Algorithm Performance",
                SwingConstants.CENTER
        );

        title.setFont(
                new Font("SansSerif", Font.BOLD, 24)
        );

        // Button for uploading a CSV file.
        JButton uploadButton = new JButton("Upload CSV");

        // Button for running the benchmark.
        JButton compareButton =
                new JButton("Compare Sorting Algorithms");

        // Upload panel.
        JPanel uploadPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        uploadPanel.add(uploadButton);
        uploadPanel.add(fileStatus);

        // Numeric column selection panel.
        JPanel selectionPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        selectionPanel.add(new JLabel("Numeric column:"));
        selectionPanel.add(numericColumnCombo);
        selectionPanel.add(compareButton);

        // Top section of the application.
        JPanel northPanel =
                new JPanel(new BorderLayout(5, 5));

        northPanel.setBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        );

        northPanel.add(title, BorderLayout.NORTH);
        northPanel.add(uploadPanel, BorderLayout.CENTER);
        northPanel.add(selectionPanel, BorderLayout.SOUTH);

        // Panel displaying all CSV columns.
        JPanel columnsPanel =
                new JPanel(new BorderLayout());

        columnsPanel.setBorder(
                BorderFactory.createTitledBorder("CSV columns")
        );

        columnsPanel.add(
                new JScrollPane(allColumnsList),
                BorderLayout.CENTER
        );

        // Configure the sorted values preview.
        resultPreview.setEditable(false);
        resultPreview.setLineWrap(true);
        resultPreview.setWrapStyleWord(true);
        resultPreview.setFont(
                new Font("Monospaced", Font.PLAIN, 12)
        );

        JPanel previewPanel =
                new JPanel(new BorderLayout());

        previewPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Sorted values preview"
                )
        );

        previewPanel.add(
                new JScrollPane(resultPreview),
                BorderLayout.CENTER
        );

        // Configure the performance results table.
        resultTable.setRowHeight(25);
        resultTable.setFillsViewportHeight(true);
        resultTable.setAutoCreateRowSorter(true);

        // Make the table header easier to read.
        resultTable.getTableHeader().setFont(
                new Font("SansSerif", Font.BOLD, 12)
        );

        // Renderer that highlights the fastest algorithm.
        DefaultTableCellRenderer renderer =
                new DefaultTableCellRenderer() {

            @Override
            public Component getTableCellRendererComponent(
                    JTable table,
                    Object value,
                    boolean isSelected,
                    boolean hasFocus,
                    int row,
                    int column) {

                Component cell =
                        super.getTableCellRendererComponent(
                                table,
                                value,
                                isSelected,
                                hasFocus,
                                row,
                                column
                        );

                // Convert the displayed row to the model row.
                int modelRow = table.convertRowIndexToModel(row);

                // Highlight the fastest algorithm's row.
                if (!isSelected && modelRow == 0) {
                    cell.setBackground(
                            new Color(210, 245, 220)
                    );

                    cell.setForeground(
                            new Color(20, 90, 45)
                    );

                } else if (!isSelected) {
                    cell.setBackground(Color.WHITE);
                    cell.setForeground(Color.BLACK);
                }

                return cell;
            }
        };

        // Apply the renderer to every table column.
        for (int i = 0;
                i < resultTable.getColumnCount();
                i++) {

            resultTable.getColumnModel()
                    .getColumn(i)
                    .setCellRenderer(renderer);
        }

        // Panel displaying the benchmark results.
        JPanel resultsPanel =
                new JPanel(new BorderLayout(5, 5));

        resultsPanel.setBorder(
                BorderFactory.createTitledBorder(
                        "Performance results"
                )
        );

        resultSummary.setFont(
                new Font("SansSerif", Font.BOLD, 14)
        );

        resultSummary.setBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        );

        resultsPanel.add(
                resultSummary,
                BorderLayout.NORTH
        );

        resultsPanel.add(
                new JScrollPane(resultTable),
                BorderLayout.CENTER
        );

        // Combine the preview and results table vertically.
        JSplitPane lowerSplit = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                previewPanel,
                resultsPanel
        );

        lowerSplit.setResizeWeight(0.35);

        // Combine the column list and results area horizontally.
        JSplitPane mainSplit = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                columnsPanel,
                lowerSplit
        );

        mainSplit.setResizeWeight(0.22);

        // Add all main panels to the window.
        frame.add(northPanel, BorderLayout.NORTH);
        frame.add(mainSplit, BorderLayout.CENTER);

        // Connect the upload button to its event handler.
        uploadButton.addActionListener(
                event -> uploadCsv(frame)
        );

        // Connect the benchmark button to its event handler.
        compareButton.addActionListener(
                event -> compareAlgorithms(frame)
        );

        // Display the application.
        frame.setVisible(true);
    }

    /**
     * Opens a file chooser and loads the selected CSV dataset.
     */
    private void uploadCsv(JFrame parent) {

        JFileChooser chooser = new JFileChooser();

        chooser.setDialogTitle("Select a CSV dataset");

        // Show CSV files in the file chooser.
        chooser.setFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "CSV files (*.csv)",
                        "csv"
                )
        );

        // Stop if the user cancels the operation.
        if (chooser.showOpenDialog(parent)
                != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File selectedFile = chooser.getSelectedFile();

        try {
            // Read the selected CSV file.
            Dataset loaded = CSVReader.readCSV(selectedFile);

            // Require at least one data row.
            if (loaded.getRows().isEmpty()) {
                throw new IllegalArgumentException(
                        "The CSV contains no data rows."
                );
            }

            // Store the newly loaded dataset.
            dataset = loaded;

            // Clear information from the previous dataset.
            allColumnsModel.clear();
            numericColumnCombo.removeAllItems();
            resultTableModel.setRowCount(0);
            resultPreview.setText("");

            resultSummary.setText(
                    "Select a numeric column and compare the algorithms."
            );

            // Display every column name.
            for (String column : dataset.getColumnNames()) {
                allColumnsModel.addElement(column);
            }

            // Find columns containing valid numeric values.
            for (int i = 0;
                    i < dataset.getColumnNames().size();
                    i++) {

                if (NumericColumnUtils.isNumericColumn(
                        dataset, i)) {

                    numericColumnCombo.addItem(
                            dataset.getColumnNames().get(i)
                    );
                }
            }

            // Update the file status.
            fileStatus.setText(
                    selectedFile.getName()
                    + " | "
                    + dataset.getRows().size()
                    + " data rows"
            );

            // Inform the user about the upload result.
            if (numericColumnCombo.getItemCount() == 0) {

                JOptionPane.showMessageDialog(
                        parent,
                        "No numeric columns were found.",
                        "No numeric columns",
                        JOptionPane.WARNING_MESSAGE
                );

            } else {

                JOptionPane.showMessageDialog(
                        parent,
                        "CSV loaded successfully.\n"
                        + "Rows: " + dataset.getRows().size()
                        + "\nNumeric columns: "
                        + numericColumnCombo.getItemCount(),
                        "Upload complete",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }

        } catch (Exception ex) {

            // Clear the dataset if loading fails.
            dataset = null;

            allColumnsModel.clear();
            numericColumnCombo.removeAllItems();
            resultTableModel.setRowCount(0);
            resultPreview.setText("");

            fileStatus.setText("Could not load CSV.");

            resultSummary.setText(
                    "Upload a valid CSV file to begin."
            );

            // Show the reason for the error.
            JOptionPane.showMessageDialog(
                    parent,
                    "Could not read the selected file:\n"
                    + ex.getMessage(),
                    "CSV error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Benchmarks all five algorithms on the selected numeric column.
     */
    private void compareAlgorithms(JFrame parent) {

        // A dataset must be loaded before benchmarking.
        if (dataset == null) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Upload a CSV file first.",
                    "No dataset",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Get the selected numeric column.
        String selectedColumn =
                (String) numericColumnCombo.getSelectedItem();

        if (selectedColumn == null) {

            JOptionPane.showMessageDialog(
                    parent,
                    "Select a numeric column first.",
                    "No column selected",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {
            // Find the selected column's index.
            int index = dataset.getColumnNames()
                    .indexOf(selectedColumn);

            // Extract the column's numeric values.
            double[] values =
                    NumericColumnUtils.extractValues(
                            dataset,
                            index
                    );

            if (values.length == 0) {
                throw new IllegalArgumentException(
                        "The selected column has no numeric values."
                );
            }

            // Run the sorting benchmark.
            List<SortBenchmark.BenchmarkResult> results =
                    SortBenchmark.compare(values);

            // Sort the result list by measured time.
            // The smallest time appears first.
            results.sort(
                    Comparator.comparingLong(
                            SortBenchmark.BenchmarkResult::getElapsedNanos
                    )
            );

            // Remove results from the previous comparison.
            resultTableModel.setRowCount(0);

            // The first result is the fastest in this benchmark run.
            SortBenchmark.BenchmarkResult fastest = results.get(0);

            // Display a summary above the table.
            resultSummary.setText(
                    "FASTEST: " + fastest.getAlgorithmName()
                    + "  |  Dataset size: " + values.length
                    + "  |  Column: " + selectedColumn
                    + "  |  Time: "
                    + String.format(
                            "%.6f ms",
                            fastest.getElapsedMillis()
                    )
            );

            // Add each algorithm's result in ranked order.
            for (int i = 0; i < results.size(); i++) {

                SortBenchmark.BenchmarkResult result =
                        results.get(i);

                // Mark the fastest algorithm.
                String status = (i == 0)
                        ? "FASTEST"
                        : "Sorted correctly";

                resultTableModel.addRow(
                        new Object[]{
                            i + 1,
                            result.getAlgorithmName(),
                            String.format(
                                    "%.6f",
                                    result.getElapsedMillis()
                            ),
                            result.getElapsedNanos(),
                            status
                        }
                );
            }

            // Display the sorted output as a preview.
            resultPreview.setText(
                    "Selected column: " + selectedColumn
                    + "\nNumber of values: " + values.length
                    + "\nValues sorted in ascending order:\n"
                    + formatPreview(
                            fastest.getSortedValues(),
                            100
                    )
            );

        } catch (Exception ex) {

            // Show any error encountered during benchmarking.
            JOptionPane.showMessageDialog(
                    parent,
                    "Could not compare the algorithms:\n"
                    + ex.getMessage(),
                    "Benchmark error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Formats the sorted array for the preview area.
     *
     * Displays at most 100 values to keep the interface readable.
     */
    private String formatPreview(
            double[] values,
            int maxValues) {

        StringBuilder text = new StringBuilder();

        int count = Math.min(values.length, maxValues);

        for (int i = 0; i < count; i++) {

            // Separate values using commas.
            if (i > 0) {
                text.append(", ");
            }

            text.append(values[i]);
        }

        // Indicate when only part of the dataset is displayed.
        if (values.length > maxValues) {

            text.append(" ... (showing ")
                    .append(maxValues)
                    .append(" of ")
                    .append(values.length)
                    .append(" values)");
        }

        return text.toString();
    }
}