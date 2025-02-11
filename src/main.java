import javax.swing.JFileChooser;
import javax.swing.JFrame;
import java.io.File;
import java.nio.file.Path;
import java.util.stream.Stream;

public class main {

    /**
     * Start visual and ask user for direcotry they want to search for songs
     * and other user facing stuff like how they want output ect
     * <p>
     * get the directory and step thru and get all song.ini files
     */

    public static void main(String[] args) {
        System.out.println("PROGRAM START");

        final Path selectedFile = getDirectory();

        SongFilter songFilter = new SongFilter();
        Stream<Path> streamOfSongs = songFilter.getSongConfigFiles(selectedFile);

        streamOfSongs.forEach(System.out::println);


        System.out.println("PROGRAM END");

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
