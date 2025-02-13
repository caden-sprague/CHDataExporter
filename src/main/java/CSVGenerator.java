import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Stream;

// DO CSV file generator
// eg jackson
public class CSVGenerator {
    public void generateFile(final Stream<Song> songs, final File outputLocation) throws IOException {
        final CSVWriter writer = new CSVWriter(new FileWriter(outputLocation));

        songs.forEach(song -> writer.writeNext(song.toString().split(" - ")));

        writer.close();
    }
}
