package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.hybridpartition.SHPKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.SMMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequential.SequentialKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequentialmaximization.SSMKmeans;

public class Kmeans {

    public static final String USAGE = "Usage: <flavour> <n> <k> <d> <i> <threads> <partitions> <taskset>";

    public static void main(String[] args) throws Exception {
        if (args.length != 8) {
            System.out.println("Usage: <flavour> <n> <k> <d> <i> <threads> <partitions> <taskset>");
        } else {
            final String flavor = args[0];
            final AbstractKmeans kmeans;
            if (flavor.equalsIgnoreCase("sd-sm")) {
                kmeans = new SSMKmeans(args);
            } else if (flavor.equalsIgnoreCase("sd-mm")) {
                kmeans = new SMMKmeans(args);
            } else if (flavor.equalsIgnoreCase("sd-hp")) {
                kmeans = new SHPKmeans(args);
            } else if (flavor.equalsIgnoreCase("sd-seq")) {
                kmeans = new SequentialKmeans(args);
            } else {
                System.out.println(USAGE);
                return;
            }
            System.out.println(kmeans.toString());
            kmeans.run();
        }
    }
}
