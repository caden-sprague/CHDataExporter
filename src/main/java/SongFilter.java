import lombok.SneakyThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class SongFilter {
    @SneakyThrows
    public final Stream<Path> getSongConfigFiles(final Path rootDir) {
            return Files.walk(rootDir)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith("song.ini"));
    }
}
