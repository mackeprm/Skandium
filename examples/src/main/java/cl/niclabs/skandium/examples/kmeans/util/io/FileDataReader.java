package cl.niclabs.skandium.examples.kmeans.util.io;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileDataReader implements DataReader {
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

    @Override
    public List<Point> read(String sourceAddress) throws IOException {
        final Stream<String> stream = readFromFile(sourceAddress);
        return stream.map(Point::new).collect(Collectors.toList());
    }

    private Stream<String> readFromFile(String filename) throws IOException {
        Path path = Paths.get(filename);
        InputStream in = Files.newInputStream(path, StandardOpenOption.READ);
        InputStreamReader reader = new InputStreamReader(in);
        return lines(new BufferedReader(reader));
    }

    private Stream<String> lines(BufferedReader br) {
        try {
            return br.lines().onClose(asUncheckedRunnable(br));
        } catch (Error | RuntimeException e) {
            try {
                br.close();
            } catch (IOException ex) {
                try {
                    e.addSuppressed(ex);
                } catch (Throwable ignore) {
                }
            }
            throw e;
        }
    }
}
