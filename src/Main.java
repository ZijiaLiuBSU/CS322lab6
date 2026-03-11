// Lab 06: Music Visualizer
// Name: Zijia Liu

public class Main {
    public static void main(String[] args) {
        double[] birdSounds = StdAudio.read("assets/cardinal_trim.wav");

        System.out.println("Length of sound array: " + birdSounds.length);

        StdAudio.play(birdSounds);
        StdAudio.drain();
    }
}