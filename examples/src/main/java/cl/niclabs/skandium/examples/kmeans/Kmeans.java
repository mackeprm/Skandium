package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.configuration.KMeansRunConfiguration;
import cl.niclabs.skandium.examples.kmeans.manual.SDManualPmKMeans;
import cl.niclabs.skandium.examples.kmeans.manual.SDManualSmKmeans;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData.SDSMFCMeans;
import cl.niclabs.skandium.examples.kmeans.skandium.cmeans.staticData.SDSeqcmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.kmedian.staticData.SDMMKmedian;
import cl.niclabs.skandium.examples.kmeans.skandium.kmedian.staticData.SDSeqKmedian;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.hybridpartition.SDHPKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.mapmaximization.SDMMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.partialmerge.SDPMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequential.SDSeqKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.sequentialmaximization.SDSMKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.simulated.SDSimKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.randomdecomposition.SdKDRDKmeans;
import cl.niclabs.skandium.examples.kmeans.skandium.staticData.treebased.sequential.SdKDSeqKmeans;
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
            case "sd-manual-pm":
                result = new SDManualPmKMeans(config);
                break;
            case "sd-manual-sm":
                result = new SDManualSmKmeans(config);
                break;
            case "sd-sim":
                result = new SDSimKmeans(config);
                break;
            case "sd-pm":
                result = new SDPMKmeans(config);
                break;
            case "kmd-sd-seq":
                result = new SDSeqKmedian(config);
                break;
            case "kmd-sd-mm":
                result = new SDMMKmedian(config);
                break;
            case "fcm-sd-seq":
                result = new SDSeqcmeans(config);
                break;
            case "fcm-sd-sm":
                result = new SDSMFCMeans(config);
                break;
            case "kd-sd-seq":
                result = new SdKDSeqKmeans(config);
                break;
            case "kd-sd-rd":
                result = new SdKDRDKmeans(config);
                break;
            default:
                throw new IllegalStateException("unrecognized flavor:" + flavour);
        }
        return result;
    }
}