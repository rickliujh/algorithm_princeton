/*
 * Question 1 Social network connectivity. Given a social network containing n
 * members and a log file containing m timestamps at which times pairs of
 * members formed friendships, design an algorithm to determine the earliest
 * time at which all members are connected (i.e., every member is a friend of a
 * friend of a friend ... of a friend). Assume that the log file is sorted by
 * timestamp and that friendship is an equivalence relation. The running time of
 * your algorithm should be log ⁡ mlogn or better and use extra space
 * proportional to n. Note: these interview questions are ungraded and purely
 * for your own enrichment. To get a hint, submit a solution. 1 point
 */

/**
 * Question 1 Social network connectivity
 */

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class SocialNetworkConnectivity {
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
            new FakeLogFile(LocalDateTime.of(1998, 9, 21, 19, 22).toEpochSecond(ZoneOffset.UTC), 1, 2),
            new FakeLogFile(LocalDateTime.of(2003, 10, 30, 5, 57).toEpochSecond(ZoneOffset.UTC), 5, 2), // here
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

    static class QuickUnion {
        static int[] items;

        public QuickUnion(int total) {
            items = new int[total + 1];
            for (int i = 0; i < items.length; i++) {
                items[i] = i;
            }
        }

        public void connect(int a, int b) {
            items[a] = b;
        }

        public boolean isConnect(int a, int b) {
            return root(a) == root(b);
        }

        private int root(int node) {
            int curr = node;
            while (items[curr] != curr) {
                curr = items[curr];
            }
            return curr;
        }
    }

    public static void main(String[] args) {
        QuickUnion qu = new QuickUnion(logFile.length);
        for (int i = 0; i < logFile.length; i++) {

            System.out.printf("%s, %s, %s\n", logFile[i].timestamp, logFile[i].connections[0],
                    logFile[i].connections[1]);

            var conns = logFile[i].connections;
            qu.connect(conns[0], conns[1]);

            System.out.printf("is %s connect to %s? %s\n", "1", "6", qu.isConnect(1, 6));
        }
    }
}