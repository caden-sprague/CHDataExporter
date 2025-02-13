import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Stream;

public class SongListGenerator {

    private static final String enchorLink = "https://www.enchor.us/";

    public Stream<Song> generateSongs() {
        final Path searchDirectory = getDirectory();

        final SongFilter songFilter = new SongFilter();
        final Stream<Path> streamOfSongFiles = songFilter.getSongConfigFiles(searchDirectory);

        try {
            return getSongs(streamOfSongFiles);
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
        final Set<Song> songSet = new LinkedHashSet<>();

        final EnchorQuery enchorQuery = new EnchorQuery();
        // get info from songs and add to the builder
        streamOfSongConfig.parallel().forEach(path -> {


            try {
                BufferedReader reader = Files.newBufferedReader(path);
                String name = "", artist = "", charter = "";
                String line;

                while ((line = reader.readLine()) != null) {

                    if (name.isEmpty() || artist.isEmpty() || charter.isEmpty()) {

                        final String strip = line
                                .replaceAll("<color=.*?>|</color>", "")
                                .substring(line.indexOf("=") + 1)
                                .strip();

                        if (line.startsWith("artist")) {
                            artist = strip;
                        } else if (line.startsWith("name")) {
                            name = strip;
                        } else if (line.startsWith("charter")) {
                            charter = strip;
                        }

                    } else {
                        //add to builder and exit when name, artist, and charter are found
                        String advancedSearch = enchorQuery.getEnchorLink(name, artist, charter);
                        if (advancedSearch.equals("null")) {
//                        System.out.println("NOT FOUND");
                            songSet.add(new Song(
                                    name,
                                    artist,
                                    charter,
                                    "not found :("
                            ));
                        } else { // returns an actual chart that matches
                            songSet.add(new Song(
                                    name,
                                    artist,
                                    charter,
                                    "=HYPERLINK(\"" + enchorLink + "chart/" + advancedSearch + "\")"
                            ));
                        }
                        break;
                    }
                }
            }
            catch (IOException e) {
                System.out.println("Cannot create scanner for path: " + path);
                throw new RuntimeException(e);
            }
        });
        return songSet.stream().sorted(
                Comparator.comparing(Song::name)
                        .thenComparing(Song::linkToSong)
        );
    }

}