# Sorting Algorithm Performance Application

A Java Swing coursework project for loading CSV datasets, selecting a numeric column,
and comparing five sorting algorithms.

## Features
- Upload a CSV file using a file chooser.
- Display the CSV column names and detect numeric columns.
- Select a numeric column.
- Compare Insertion, Shell, Merge, Quick, and Heap Sort.
- Measure elapsed time with `System.nanoTime()`.
- Verify that each algorithm produced ascending sorted output.
- Display a small preview of the sorted data.

## Requirements
- JDK 17 or newer recommended
- VS Code with the Java Extension Pack, or IntelliJ IDEA

## Project structure
```text
SortingAlgorithmPerformance/
├── src/
│   ├── main/java/
│   │   ├── Main.java
│   │   ├── Dataset.java
│   │   ├── CSVReader.java
│   │   ├── NumericColumnUtils.java
│   │   ├── SortingAlgorithm.java
│   │   ├── SortBenchmark.java
│   │   ├── InsertionSort.java
│   │   ├── ShellSort.java
│   │   ├── MergeSort.java
│   │   ├── QuickSort.java
│   │   └── HeapSort.java
│   └── test/java/SortingAlgorithmsTest.java
├── data/sample.csv
├── .gitignore
└── README.md
```

## Run in VS Code or terminal
From the project root:

```bash
mkdir -p out
javac -d out src/main/java/*.java src/test/java/SortingAlgorithmsTest.java
java -cp out SortingAlgorithmsTest
java -cp out Main
```

On Windows PowerShell, the same commands work in a current JDK environment. If `mkdir -p`
is not supported by your shell, create the `out` folder in the file explorer first.

## How to use
1. Run `Main`.
2. Click **Upload CSV**.
3. Choose `data/sample.csv` or another CSV with a header row.
4. Choose a numeric column such as `Age` or `Salary`.
5. Click **Compare Sorting Algorithms**.
6. Review each algorithm's time and correctness status in the results table.

## CSV notes
- The first non-empty row must contain column names.
- Every data row must have the same number of fields as the header.
- Numeric columns should contain only numbers or blank cells.
- This coursework CSV reader supports quoted commas on a single line; quoted fields spanning multiple lines are not supported.
- Very large datasets can make the quadratic Insertion Sort slow. Start with a small dataset, then increase the data size carefully.

## GitHub workflow for two members

### 1. Create the repository
One member creates a public or private GitHub repository named `sorting-algorithm-performance`.
Do not initialize a second README if you are pushing this existing project folder.

### 2. Initialize Git in the project root
```bash
git init
git add .
git commit -m "Initial project structure and sorting algorithms"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/sorting-algorithm-performance.git
git push -u origin main
```
Replace the URL with the actual repository URL from GitHub.

### 3. Add the second member
On GitHub, open **Settings → Collaborators** (or repository access settings), invite the second member,
and have them accept the invitation.

### 4. Both members clone the repository
The second member runs:
```bash
git clone https://github.com/YOUR-USERNAME/sorting-algorithm-performance.git
cd sorting-algorithm-performance
git checkout -b member2-sorting-tests
```
The first member can work on a separate branch:
```bash
git checkout -b member1-gui-csv
```

### 5. Daily work
Before starting:
```bash
git checkout YOUR-BRANCH
git pull origin main
```
After making changes:
```bash
git status
git add .
git commit -m "Describe the change"
git push -u origin YOUR-BRANCH
```
Use a descriptive commit message and do not commit generated `.class` files.

### 6. Merge through a Pull Request
1. Push the branch to GitHub.
2. Open a Pull Request targeting `main`.
3. The other member reviews the code.
4. Resolve conflicts if GitHub reports any.
5. Merge the Pull Request.
6. Both members update their local `main`:
```bash
git checkout main
git pull origin main
```

### Suggested responsibilities
- Member 1: Swing GUI, CSV upload flow, user-facing validation, integration.
- Member 2: sorting algorithm implementations, test cases, performance validation.
- Both: review each other's work, test on different CSV files, maintain README and presentation evidence.

## Benchmark interpretation
Timing varies with machine load, JVM warm-up, input size, and data ordering. Run comparisons more than once for a stronger report.
Do not claim that one algorithm is always fastest based on one run. Insertion Sort is O(n²) in average/worst cases,
so use a small/medium dataset for initial tests.
