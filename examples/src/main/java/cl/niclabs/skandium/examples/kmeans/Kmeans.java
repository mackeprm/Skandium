package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.hybridpartition.SHPKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.SMMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequentialmaximization.SSMKmeans;

public class Kmeans {

    public static void main(String[] args) throws Exception {
        //<flavor> <threads> <n> <k> <d>
        final String flavor = args[0];
        final String[] sub = {args[1], args[2], args[3], args[4]};
        final AbstractKmeans kmeans;
        if (flavor.equalsIgnoreCase("sm")) {
            kmeans = new SSMKmeans(sub);
        } else if (flavor.equalsIgnoreCase("mm")) {
            kmeans = new SMMKmeans(sub);
        } else if (flavor.equalsIgnoreCase("hp")) {
            kmeans = new SHPKmeans(sub);
        } else {
            System.out.println("Usage: kmeans <flavor> <threads> <n> <k> <d>");
            return;
        }
        System.out.println(kmeans.toString());
        kmeans.run();
    }
}
