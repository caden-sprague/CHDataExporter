import java.io.File;
import java.io.IOException;

public class Runner {

    /**
     * Start visual and ask user for direcotry they want to search for songs
     * and other user facing stuff like how they want output ect
     * <p>
     * get the directory and step thru and get all song.ini files
     * <p>
     * then create song data type for every song.ini
     * <p>
     * then spit out a file to location
     */

    public static void main (String[] args) throws IOException {
        System.out.println("PROGRAM START");


        SongListGenerator generator = new SongListGeneratorImpl();
        TextFileGenerator textFileGenerator = new TextFileGeneratorImpl();
        File outputTo = new File("/Users/user/Downloads/output.txt");
        textFileGenerator.generateFile(generator.generateSongs(),  outputTo);

        System.out.println("Created output.txt in location: \"" + outputTo.getAbsolutePath() + "\"");

        System.out.println("PROGRAM END");

    }


}
