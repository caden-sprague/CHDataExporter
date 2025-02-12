import java.io.IOException;
import java.util.stream.Stream;

public interface SongListGenerator {
    Stream<Song> generateSongs() throws IOException;
}
