import java.io.File;
import java.io.IOException;

public class Runner {

    /**
     * Start visual and ask user for directory they want to search for songs
     * and other user facing stuff like how they want output ect
     * <p>
     * get the directory and step through and get all song.ini files
     * <p>
     * then create song data type for every song.ini
     * <p>
     * then spit out a file to location
     */

    public static void main (String[] args) throws IOException {
        System.out.println("PROGRAM START");

        SongListGenerator generator = new SongListGenerator();
        CSVGenerator textFileGenerator = new CSVGenerator();

        final String userHome = System.getProperty("user.home");

        final File outputTo = new File(userHome + File.separator + "Downloads" + File.separator + "output.csv");
        textFileGenerator.generateFile(generator.generateSongs(),  outputTo);

        System.out.println("Created output.txt in location: \"" + outputTo.getAbsolutePath() + "\"");

        System.out.println("PROGRAM END");

    }


}
