import java.io.IOException;

public class Runner {

    /**
     * Start visual and ask user for direcotry they want to search for songs
     * and other user facing stuff like how they want output ect
     * <p>
     * get the directory and step thru and get all song.ini files
     * <p>
     * then create song data type for every song.ini
     */

    public static void main (String[] args) throws IOException {
        System.out.println("PROGRAM START");


        SongListGenerator generator = new SongListGeneratorImpl();
        TextFileGenerator textFileGenerator = new TextFileGeneratorImpl();
        textFileGenerator.generateFile(generator.generateSongs());

        System.out.println("Created output.txt");

        System.out.println("PROGRAM END");

    }


}
