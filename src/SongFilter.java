import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.stream.Stream;

public class SongFilter implements Filter {
    public final Stream<Path> getSongConfigFiles(final Path rootDir) {
        try {
            return Files.walk(rootDir)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith(".ini"));
        } catch (Exception e) {
            if(e.getCause() instanceof IOException) {
                e.printStackTrace();
            } else if (e.getCause() instanceof NoSuchFileException) {
                System.out.println("Could not walk root dir");
                System.out.println("No such directory: " + rootDir.toString());
                System.out.println("Cause: " + e.getCause());
            }
            throw new RuntimeException(e);
        }
    }
}
