import java.io.IOException;
import java.util.stream.Stream;

public interface TextFileGenerator {
    void generateFile(final Stream<Song> songs) throws IOException;
}
