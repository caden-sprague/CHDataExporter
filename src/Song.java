/**
 * @param name
 * @param artist
 * @param charter
 * @param match - will be used to see if this song matched with friend maybe
 */
public record Song (String name, String artist, String charter, Boolean match) implements Comparable {
    public String toString() {
        return name + " " + artist + " " + charter + "\n";
    }

    /**
     * implemented so could be used Stream.sorted()
     * @param o the other Song to be compared.
     * @return String.compareTo of the name values
     */
    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Song) o).name);
    }

}
