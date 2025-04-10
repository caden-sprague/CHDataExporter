/**
 * ADT to hold the data related to each song
 *
 * @param name
 * @param artist
 * @param charter
 */
public record Song (String name, String artist, String charter, String linkToSong) implements Comparable {
    public String toString() {
        return name + " ~ " + artist + " ~ " + charter + " ~ " + linkToSong;
    }

    /**
     * implemented so could be used Stream.sorted()
     * @param o the other Song to be compared.
     * @return String.compareTo of the name values
     */
    @Override
    public int compareTo(final Object o) {
        return name.compareTo(((Song) o).name);
    }

    @Override
    public boolean equals(final Object o) {
        return name.compareTo(((Song) o).name) == 0;
    }

}
