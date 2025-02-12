import java.nio.file.Path;
import java.util.stream.Stream;

public interface SongFilter {
    Stream<Path> getSongConfigFiles(final Path rootDir);
}
