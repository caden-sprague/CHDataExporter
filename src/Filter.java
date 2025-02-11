import java.nio.file.Path;
import java.util.stream.Stream;

public interface Filter {
    Stream<Path> getSongConfigFiles(Path rootDir);
}
