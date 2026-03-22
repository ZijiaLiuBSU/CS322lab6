import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog(
                null,
                "Enter filename and number of bars to display",
                "assets/cardinal_trim.wav 100"
        );

        if (input == null) {
            return;
        }

        input = input.trim();

        if (input.length() == 0) {
            JOptionPane.showMessageDialog(null, "No input was entered.");
            return;
        }

        String[] parts = input.split("\\s+");

        if (parts.length != 2) {
            JOptionPane.showMessageDialog(null, "Please enter exactly two values: filename and number of bars.");
            return;
        }

        String filename = parts[0];
        int numberOfBars;

        if (!filename.endsWith(".wav")) {
            JOptionPane.showMessageDialog(null, "The file must be a .wav file.");
            return;
        }

        if (filename.contains("../") || filename.contains("..\\") || filename.startsWith("/") || filename.startsWith("\\")) {
            JOptionPane.showMessageDialog(null, "Invalid file path.");
            return;
        }

        try {
            numberOfBars = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "The number of bars must be an integer.");
            return;
        }

        if (numberOfBars <= 0) {
            JOptionPane.showMessageDialog(null, "The number of bars must be greater than 0.");
            return;
        }

        if (numberOfBars > 5000) {
            JOptionPane.showMessageDialog(null, "The number of bars is too large.");
            return;
        }

        System.out.println("Filename entered: " + filename);
        System.out.println("Number of bars entered: " + numberOfBars);

        try {
            double[] samples = StdAudio.read(filename);

            if (numberOfBars > samples.length) {
                JOptionPane.showMessageDialog(null, "The number of bars cannot be greater than the number of samples.");
                return;
            }

            double[] barHeights = getBarHeights(samples, numberOfBars);

            System.out.println("Number of samples: " + samples.length);
            System.out.println("Number of bars created: " + barHeights.length);

            playAndDraw(samples, barHeights);
            StdAudio.drain();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not open or play the file.");
        }
    }

    public static double[] getBarHeights(double[] samples, int numberOfBars) {
        double[] barHeights = new double[numberOfBars];
        int groupSize = samples.length / numberOfBars;

        for (int i = 0; i < numberOfBars; i++) {
            int start = i * groupSize;
            int end = start + groupSize;
            double maxValue = 0.0;

            for (int j = start; j < end; j++) {
                double currentValue = Math.abs(samples[j]);

                if (currentValue > maxValue) {
                    maxValue = currentValue;
                }
            }

            barHeights[i] = maxValue;
        }

        return barHeights;
    }

    public static void playAndDraw(double[] samples, double[] barHeights) {
        StdDraw.setTitle("Music Visualizer");
        StdDraw.setCanvasSize(1000, 400);
        StdDraw.setXscale(-1, barHeights.length);
        StdDraw.setYscale(-1.0, 1.0);
        StdDraw.setPenRadius(0.003);
        StdDraw.enableDoubleBuffering();

        int groupSize = samples.length / barHeights.length;

        for (int i = 0; i < barHeights.length; i++) {
            StdDraw.clear();

            for (int j = 0; j <= i; j++) {
                double height = barHeights[j];
                StdDraw.line(j, -height, j, height);
            }

            StdDraw.show();

            int start = i * groupSize;
            int end = start + groupSize;

            double[] segment = new double[groupSize];
            int index = 0;

            for (int j = start; j < end; j++) {
                segment[index] = samples[j];
                index++;
            }

            StdAudio.play(segment);
        }
    }
}