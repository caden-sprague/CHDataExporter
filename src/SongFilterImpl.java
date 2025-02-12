import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class SongFilterImpl implements SongFilter {
    public final Stream<Path> getSongConfigFiles(final Path rootDir) {
        try {
            return Files.walk(rootDir)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith("song.ini"));
        } catch (IOException | SecurityException e) {
            System.out.println("Could not walk thru " + rootDir);
            System.out.println("Because of an Exception: " + e);
            throw new RuntimeException(e);
        }
    }
}
