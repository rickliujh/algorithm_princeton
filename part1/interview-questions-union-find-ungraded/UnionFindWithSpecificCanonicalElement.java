/*
 * Question 2 Union-find with specific canonical element. 
 * Add a method find() to the union-find data type so that find(i) returns
 * the largest element in the connected component containing i. 
 * The operations, union(), connected(), and find() should all take logarithmic time or better. 
 * For example, if one of the connected components is { 1 , 2 , 6 , 9 } {1,2,6,9}, 
 * then the find() method should return 9 9 for each of the four elements in the connected components.
 */

/**
 * Question 2 Union-find with specific canonical element
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class UnionFindWithSpecificCanonicalElement {
    static class FakeLogFile {
        public long timestamp;
        public int[] connections;

        public FakeLogFile(long timestamp, int member1, int member2) {
            this.timestamp = timestamp;
            this.connections = new int[] { member1, member2 };
        }
    }

    public static int[] members = { 1, 2, 3, 4, 5, 6 };

    public static FakeLogFile[] logFile = {
            new FakeLogFile(LocalDateTime.of(1991, 12, 6, 15, 59).toEpochSecond(ZoneOffset.UTC), 2, 6),
            new FakeLogFile(LocalDateTime.of(1993, 5, 23, 23, 34).toEpochSecond(ZoneOffset.UTC), 1, 4),
            new FakeLogFile(LocalDateTime.of(1997, 11, 11, 1, 14).toEpochSecond(ZoneOffset.UTC), 3, 5),
            new FakeLogFile(LocalDateTime.of(1998, 2, 27, 14, 35).toEpochSecond(ZoneOffset.UTC), 6, 3),
            new FakeLogFile(LocalDateTime.of(1998, 9, 21, 19, 22).toEpochSecond(ZoneOffset.UTC), 1, 2), // here
            new FakeLogFile(LocalDateTime.of(2003, 10, 30, 5, 57).toEpochSecond(ZoneOffset.UTC), 5, 2),
            new FakeLogFile(LocalDateTime.of(2005, 5, 1, 3, 50).toEpochSecond(ZoneOffset.UTC), 1, 5),
            new FakeLogFile(LocalDateTime.of(2008, 9, 15, 21, 21).toEpochSecond(ZoneOffset.UTC), 3, 4),
            new FakeLogFile(LocalDateTime.of(2008, 4, 16, 23, 45).toEpochSecond(ZoneOffset.UTC), 1, 6),
            new FakeLogFile(LocalDateTime.of(2011, 6, 21, 9, 55).toEpochSecond(ZoneOffset.UTC), 3, 2),
            new FakeLogFile(LocalDateTime.of(2015, 7, 13, 7, 52).toEpochSecond(ZoneOffset.UTC), 2, 4),
            new FakeLogFile(LocalDateTime.of(2015, 7, 17, 19, 11).toEpochSecond(ZoneOffset.UTC), 5, 1),
            new FakeLogFile(LocalDateTime.of(2016, 1, 19, 10, 12).toEpochSecond(ZoneOffset.UTC), 4, 1),
            new FakeLogFile(LocalDateTime.of(2020, 1, 29, 13, 15).toEpochSecond(ZoneOffset.UTC), 3, 6),
            // new FakeLogFile(LocalDateTime.of(2020, 2, 9, 17,
            // 51).toEpochSecond(ZoneOffset.UTC), 2, 6),
            // new FakeLogFile(LocalDateTime.of(2021, 3, 2, 11,
            // 41).toEpochSecond(ZoneOffset.UTC), 2, 6),
            // new FakeLogFile(LocalDateTime.of(2021, 7, 16, 12,
            // 37).toEpochSecond(ZoneOffset.UTC), 2, 6),
            // new FakeLogFile(LocalDateTime.of(2022, 9, 14, 13,
            // 29).toEpochSecond(ZoneOffset.UTC), 2, 6),
            // new FakeLogFile(LocalDateTime.of(2022, 10, 10, 9,
            // 37).toEpochSecond(ZoneOffset.UTC), 2, 6),
            // new FakeLogFile(LocalDateTime.of(2023, 11, 11, 5,
            // 1).toEpochSecond(ZoneOffset.UTC), 2, 6),
            // new FakeLogFile(LocalDateTime.of(2023, 3, 31, 19,
            // 9).toEpochSecond(ZoneOffset.UTC), 2, 6),

    };

    static class WeightedQuickUnion {
        int total;
        static int[] items;
        static int[] sz;
        static int[] bigest;

        public WeightedQuickUnion(int total) {
            this.total = total;
            items = new int[total + 1];
            sz = new int[total + 1];
            bigest = new int[total + 1];
            for (int i = 0; i < items.length; i++) {
                items[i] = i;
                sz[i] = 1;
                bigest[i] = i;
            }
        }

        public void connect(int a, int b) {
            int i = root(a);
            int j = root(b);
            if (i == j)
                return;

            // weight the tree
            if (sz[i] < sz[j]) {
                items[a] = j;
                sz[j] += sz[i];
            } else {
                items[b] = i;
                sz[i] += sz[j];
            }

            if (bigest[i] > bigest[j]) {
                bigest[j] = bigest[i];
            } else {
                bigest[i] = bigest[j];
            }
        }

        public boolean isConnect(int a, int b) {
            return root(a) == root(b);
        }

        public int find(int node) {
            return bigest[root(node)];
        }

        private int root(int node) {
            int i = node;
            while (items[i] != i) {
                items[i] = items[items[i]]; // path compression
                i = items[i];
            }
            return i;
        }
    }

    public static String toStringFromTimestampLong(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.UTC).toString();
    }

    public static void printArray(int[] array) {
        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += array[i] + " ";
        }
        System.out.println(result);
    }

    public static void main(String[] args) {
        WeightedQuickUnion wqu = new WeightedQuickUnion(members.length);

        for (int i = 0; i < logFile.length; i++) {
            // System.out.printf("%s, %s, %s\n", logFile[i].timestamp,
            // logFile[i].connections[0],
            // logFile[i].connections[1]);

            int[] conns = logFile[i].connections;
            wqu.connect(conns[0], conns[1]);

            // printArray(wqu.items);
            // printArray(cqu.sz);
            // printArray(wqu.bigest);
            // System.out.println();

        }

        System.out.println("largest number in the connected component 4 is " + wqu.find(4)); // should be 6
    }
}