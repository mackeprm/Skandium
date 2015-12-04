package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.hybridpartition.SDHPKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.SDMMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequential.SDSeqKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequentialmaximization.SDSMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.simulated.SDSimKmeans;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

import java.net.UnknownHostException;

public class Kmeans {

    public static void main(String[] args) throws Exception {
        try {
            KMeansRunConfiguration config = new KMeansRunConfiguration();
            new JCommander(config, args);
            AbstractKmeans kmeans = getInstanceForConfig(config);
            System.out.println(kmeans.toString());
            kmeans.run();
        } catch (ParameterException e) {
            JCommander jCommander = new JCommander(new KMeansRunConfiguration());
            jCommander.setProgramName("skandium.jar");
            jCommander.usage();
        }
    }

    private static AbstractKmeans getInstanceForConfig(KMeansRunConfiguration config) throws UnknownHostException {
        String flavour = config.flavour;
        AbstractKmeans result;
        switch (flavour) {
            case "sd-sm":
                result = new SDSMKmeans(config);
                break;
            case "sd-mm":
                result = new SDMMKmeans(config);
                break;
            case "sd-hp":
                result = new SDHPKmeans(config);
                break;
            case "sd-seq":
                result = new SDSeqKmeans(config);
                break;
            case "sd-sim":
                result = new SDSimKmeans(config);
                break;
            default:
                throw new IllegalStateException("unrecognized flavor:" + flavour);
        }
        return result;
    }
}