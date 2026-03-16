import javax.swing.JOptionPane;

public class Main {
    public static void main(String[] args) {
        String filename = JOptionPane.showInputDialog(
                null,
                "Enter a sound filename to play",
                "assets/cardinal_trim.wav"
        );

        if (filename == null) {
            return;
        }

        filename = filename.trim();

        if (filename.length() == 0) {
            JOptionPane.showMessageDialog(null, "No filename was entered.");
            return;
        }

        try {
            double[] samples = StdAudio.read(filename);
            StdAudio.play(samples);
            StdAudio.drain();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Could not open or play the file.");
        }
    }
}