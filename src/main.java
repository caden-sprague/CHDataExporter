import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class main {

    /**
     * Start visual and ask user for direcotry they want to search for songs
     * and other user facing stuff like how they want output ect
     * <p>
     * get the directory and step thru and get all song.ini files
     *
     * then create song data type for every song.ini
     */

    public static void main(String[] args) throws IOException {
        System.out.println("PROGRAM START");

        final Path selectedFile = getDirectory();

        SongFilter songFilter = new SongFilter();
        Stream<Path> streamOfSongFiles = songFilter.getSongConfigFiles(selectedFile);

        Stream<Song> steamOfSongs = getSongs(streamOfSongFiles);

        steamOfSongs.forEach(System.out::println);


        System.out.println("PROGRAM END");

    }

    private static Stream<Song> getSongs(final Stream<Path> streamOfSongs) throws IOException {
        Stream.Builder<Song> builder = Stream.builder();

        for (Path path : streamOfSongs.toList()) {
            Scanner scanner = new Scanner(path);

            String name = "null";
            String artist = "null";
            String charter = "null";


            while (scanner.hasNext()) {
                if(name.equals("null") || artist.equals("null") || charter.equals("null")) {
                String line = scanner.nextLine();
                    if (line.matches("artist[\\S | \\s]=[\\S | \\s](.*)")) {
                        artist = line.split("=")[1].strip();
                    } else if (line.matches("name[\\S | \\s]=[\\S | \\s](.*)")) {
                        name = line.split("=")[1].strip();
                    } else if (line.matches("charter[\\S | \\s]=[\\S | \\s](.*)")) {
                        charter = line.split("=")[1].strip();
                    }
                }
                else {
                    builder.add(new Song(
                            name,
                            artist,
                            charter
                    ));
                    break;
                }
            }
        }

        return builder.build();
    }

    private static Path getDirectory() {
        final File selectedFile;
        JFrame frame = new JFrame("File Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select File or Directory");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // Allow selection of both

        int result = fileChooser.showOpenDialog(frame);

        if (result == JFileChooser.APPROVE_OPTION) {
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
}
