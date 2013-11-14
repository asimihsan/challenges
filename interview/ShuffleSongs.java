import java.util.List;
import java.util.ArrayList;
import java.util.Random;

class Song implements Comparable<Song> {
    private String name;
    Song(String name) { this.name = name; }

    @Override
    public int compareTo(Song o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Song)) return false;
        Song song = (Song)o;
        return this.name.equals(song.name);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("{name=%s}", name);
    }
}

class ShuffleSongs {
    static <T extends Comparable<T>> void shuffle(List<T> array) {
        final int n = array.size();
        Random r = new Random();
        for (int i = n - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            T temp = array.get(j);
            array.set(j, array.get(i));
            array.set(i, temp);
        }
    }

    public static void main(String[] args) {
        List<Song> songs = new ArrayList<Song>();
        songs.add(new Song("lalala"));
        songs.add(new Song("falala"));
        songs.add(new Song("tralala"));
        System.out.println(songs);
        shuffle(songs);
        System.out.println(songs);
    }
}