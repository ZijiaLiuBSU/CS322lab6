// Lab 06: Music Visualizer
// Name: Zijia Liu

public class Main {
    public static void main(String[] args) {
        double[] birdSounds = StdAudio.read("assets/cardinal_trim.wav");

        System.out.println("Length of sound array: " + birdSounds.length);

        StdAudio.play(birdSounds);
        StdAudio.drain();

        int start = 0;
        int end = birdSounds.length / 3;

        double[] oneBird = new double[end - start];

        for (int i = 0; i < oneBird.length; i++) {
            oneBird[i] = birdSounds[start + i];
        }

        StdAudio.play(oneBird);
        StdAudio.drain();
    }
}