import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class SongListGeneratorImpl implements SongListGenerator {

    @Override
    public Stream<Song> generateSongs() throws IOException {
        final Path selectedFile = getDirectory();

        SongFilter songFilter = new SongFilter();
        Stream<Path> streamOfSongFiles = songFilter.getSongConfigFiles(selectedFile);

        return getSongs(streamOfSongFiles).distinct().sorted();

    }

    private Path getDirectory() {
        final File selectedFile;
        final JFrame frame = new JFrame("File Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select File or Directory");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // Allow selection of both

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            // You can now work with the selected file
        } else {
            System.out.println("No file selected.");
            throw new RuntimeException("No file selected on popup window!");
        }

        frame.dispose(); // Close the frame after use
        return selectedFile.toPath();
    }

    private Stream<Song> getSongs(final Stream<Path> streamOfSongs) throws IOException {
        final Stream.Builder<Song> builder = Stream.builder();

        for (final Path path : streamOfSongs.toList()) {
            final Scanner scanner = new Scanner(path);

            String name = "null";
            String artist = "null";
            String charter = "null";

            // get info from songs and add to the builder
            while (scanner.hasNext()) {
                if (name.equals("null") || artist.equals("null") || charter.equals("null")) {
                    String line = scanner.nextLine();
                    if (line.startsWith("artist")) {
                        artist = line
                                .replaceAll("<color=.*?>|</color>", "")
                                .split("=")[1]
                                .strip();
                    } else if (line.startsWith("name")) {
                        name = line
                                .replaceAll("<color=.*?>|</color>", "")
                                .split("=")[1]
                                .strip();
                    } else if (line.startsWith("charter")) {
                        charter = line
                                .replaceAll("<color=.*?>|</color>", "")
                                .split("=")[1]
                                .strip();
                    }
                } else {
                    //add to builder and exit when name, artist, and charter are found
                    builder.add(new Song(
                            name,
                            artist,
                            charter,
                            false
                    ));
                    break;
                }
            }
        }
        return builder.build();
    }

}