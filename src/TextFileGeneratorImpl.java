import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

public class TextFileGeneratorImpl implements TextFileGenerator {
    @Override
    public void generateFile(final Stream<Song> songs) throws IOException {
        FileWriter writer = new FileWriter("/Users/user/Downloads/output.txt");

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
