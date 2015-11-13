package cl.niclabs.skandium.examples.kmeans.util.io;

import cl.niclabs.skandium.examples.kmeans.model.Point;

import java.io.IOException;
import java.util.List;

public interface DataReader {

    //LIST is needed to assign random element via x.get(randomIndex);
    List<Point> read(String sourceAddress) throws IOException;
}
