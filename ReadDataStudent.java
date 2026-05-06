import java.util.Scanner;
import java.io.File;

public class ReadDataStudent {

    private double[][] data = new double[20000][10]; // bigger size
    private int rowCount = 0;

    public void read() {
        try {
            Scanner scanner = new Scanner(new File("cps.csv"));
            int row = 0;
            scanner.nextLine(); // skip header

            while (scanner.hasNextLine() && row < data.length) {
                String line = scanner.nextLine();
                String[] lineArr = line.split(",");

                for (int col = 0; col < lineArr.length && col < data[row].length; col++) {
                    try {
                        data[row][col] = Double.parseDouble(lineArr[col]);
                    } catch (NumberFormatException e) {
                        data[row][col] = 0;
                    }
                }

                row++;
            }
            System.out.println("Rows read: " + rowCount);
            rowCount = row; // store actual number of rows
            scanner.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double[] getColumn(int col) {
        double[] column = new double[rowCount];
        for (int i = 0; i < rowCount; i++) {
            column[i] = data[i][col];
        }
        return column;
    }

    public double mean(double[] arr) {
        double sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }
        return sum / arr.length;
    }

    public double stdDeviation(double[] arr) {
        double sum = 0;
        double mean = mean(arr);

        for (int i = 0; i < arr.length; i++) {
            sum += Math.pow(arr[i] - mean, 2);
        }

        return Math.sqrt(sum / (arr.length - 1));
    }

    public double[] standardUnits(double[] arr) {
        double[] stdArr = new double[arr.length];
        double mean = mean(arr);
        double std = stdDeviation(arr);

        for (int i = 0; i < arr.length; i++) {
            stdArr[i] = (arr[i] - mean) / std;
        }

        return stdArr;
    }

    public double correlation(double[] x, double[] y) {
        double sum = 0;
        for (int i = 0; i < x.length; i++) {
            sum += x[i] * y[i];
        }
        return sum / (x.length - 1);
    }

    public void runRegression() {
        double[] x = getColumn(7);
        double[] y = getColumn(9);

        double[] xStd = standardUnits(x);
        double[] yStd = standardUnits(y);

        double correlation = correlation(xStd, yStd);
        double slope = correlation * stdDeviation(y) / stdDeviation(x);
        double intercept = mean(y) - slope * mean(x);

        System.out.println("Correlation: " + correlation);
        System.out.println("Slope: " + slope);
        System.out.println("Intercept: " + intercept);
    }

    public void print(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        ReadDataStudent rd = new ReadDataStudent();
        rd.read();
        rd.runRegression();
    }
}