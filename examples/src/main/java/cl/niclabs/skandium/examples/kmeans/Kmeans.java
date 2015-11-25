package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.hybridpartition.SDHPKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.SDMMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequential.SDSeqKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequentialmaximization.SDSMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.simulated.SDSimKmeans;

public class Kmeans {

    public static final String USAGE = "Usage: <flavour> <n> <k> <d> <i> <threads> <partitions> <taskset>";

    public static void main(String[] args) throws Exception {
        if (args.length != 8) {
            System.out.println("Usage: <flavour> <n> <k> <d> <i> <threads> <partitions> <taskset>");
        } else {
            final String flavor = args[0];
            final AbstractKmeans kmeans;
            if (flavor.equalsIgnoreCase("sd-sm")) {
                kmeans = new SDSMKmeans(args);
            } else if (flavor.equalsIgnoreCase("sd-mm")) {
                kmeans = new SDMMKmeans(args);
            } else if (flavor.equalsIgnoreCase("sd-hp")) {
                kmeans = new SDHPKmeans(args);
            } else if (flavor.equalsIgnoreCase("sd-seq")) {
                kmeans = new SDSeqKmeans(args);
            } else if (flavor.equalsIgnoreCase("sd-sim")) {
                kmeans = new SDSimKmeans(args);
            } else {
                System.out.println(USAGE);
                return;
            }
            System.out.println(kmeans.toString());
            kmeans.run();
        }
    }
}
