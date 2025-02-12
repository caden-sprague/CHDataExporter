import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

// DO CSV file generator
// eg jackson
public class TextFileGenerator {
    public void generateFile(final Stream<Song> songs, final File outputLocation) throws IOException {
        FileWriter writer = new FileWriter(outputLocation);

        songs.forEach(song -> {
            try {
                writer.write(song.toString() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        writer.close();
    }
}
