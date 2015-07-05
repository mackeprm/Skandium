package cl.niclabs.skandium.examples.kmeans.util;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataFileReader {

    public List<XYPoint> readData(String filename) throws IOException {
        final Stream<String> stream = read(filename);
        return stream.map(XYPoint::new).collect(Collectors.toList());
    }

    public Stream<String> read(String filename) throws IOException {
        Path path = Paths.get(filename);
        InputStream in = Files.newInputStream(path, StandardOpenOption.READ);
        InputStreamReader reader = new InputStreamReader(in);
        return lines(new BufferedReader(reader));
    }

    private Stream<String> lines(BufferedReader br) {
        try {
            return br.lines().onClose(asUncheckedRunnable(br));
        } catch (Error|RuntimeException e) {
            try {
                br.close();
            } catch (IOException ex) {
                try {
                    e.addSuppressed(ex);
                } catch (Throwable ignore) {}
            }
            throw e;
        }
    }

    /**
     * Convert a Closeable to a Runnable by converting checked IOException
     * to UncheckedIOException
     */
    private static Runnable asUncheckedRunnable(Closeable c) {
        return () -> {
            try {
                c.close();
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };
    }

}
