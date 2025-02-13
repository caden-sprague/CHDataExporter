import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.stream.Stream;

public class SongListGenerator {

    private static final String enchorLink = "https://www.enchor.us/chart/";

    public Stream<Song> generateSongs() {
        final Path searchDirectory = getDirectory();

        final SongFilter songFilter = new SongFilter();
        final Stream<Path> streamOfSongFiles = songFilter.getSongConfigFiles(searchDirectory);

        try {
            return getSongs(streamOfSongFiles).distinct().sorted();
        } catch (IOException e){
            System.out.println();
            throw new RuntimeException(e);
        }

    }



    //TODO probably want to remove making the JFrame and closing it here.

    /**
     * @return the directory user provides to be searched
     */
    private Path getDirectory() {
        final File selectedFile;
        final JFrame frame = new JFrame("File Selector");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);

        final JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select File or Directory");
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
        } else {
            throw new RuntimeException("No file selected on popup window!");
        }

        frame.dispose();
        return selectedFile.toPath();
    }

    /**
     *
     * @param streamOfSongConfig stream that contains all the paths to all found song.ini files
     * @return Stream of Songs found within streamOfSongConfig
     * @throws IOException need for new Scanner
     */
    private Stream<Song> getSongs(final Stream<Path> streamOfSongConfig) throws IOException {
        final Stream.Builder<Song> builder = Stream.builder();

        final EnchorQuery enchorQuery = new EnchorQuery();
        // get info from songs and add to the builder
        streamOfSongConfig.forEach(path -> {

            final Scanner scanner;

            try {
                scanner = new Scanner(path);
            } catch (IOException e) {
                System.out.println("Cannot create scanner for path: " + path);
                throw new RuntimeException(e);
            }

            String name = "", artist = "", charter = "";

            while (scanner.hasNext()) {
                if (name.isEmpty() || artist.isEmpty() || charter.isEmpty()) {
                    final String line = scanner.nextLine();
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
                            "=HYPERLINK(\"" + enchorLink + enchorQuery.getEnchorLink(name, artist, charter) + "\")"
                    ));
                    break;
                }
            }
        });
        return builder.build();
    }

}