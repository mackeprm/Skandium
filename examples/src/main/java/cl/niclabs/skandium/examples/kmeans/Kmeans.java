package cl.niclabs.skandium.examples.kmeans;

import cl.niclabs.skandium.examples.kmeans.cmeans.SDSMFCMeans;
import cl.niclabs.skandium.examples.kmeans.cmeans.SDSeqcmeans;
import cl.niclabs.skandium.examples.kmeans.kmedian.SDMMKmedian;
import cl.niclabs.skandium.examples.kmeans.kmedian.SDSMKmedian;
import cl.niclabs.skandium.examples.kmeans.kmedian.SDSeqKmedian;
import cl.niclabs.skandium.examples.kmeans.lloyd.hybridpartition.SDHPKmeans;
import cl.niclabs.skandium.examples.kmeans.lloyd.manual.SDManualPmKMeans;
import cl.niclabs.skandium.examples.kmeans.lloyd.manual.SDManualSmKmeans;
import cl.niclabs.skandium.examples.kmeans.lloyd.mapmaximization.SDMMKmeans;
import cl.niclabs.skandium.examples.kmeans.lloyd.partialmerge.SDPMKmeans;
import cl.niclabs.skandium.examples.kmeans.lloyd.sequential.SDSeqKmeans;
import cl.niclabs.skandium.examples.kmeans.lloyd.sequential.SdSeqPmKmeans;
import cl.niclabs.skandium.examples.kmeans.lloyd.sequentialmaximization.SDSMKmeans;
import cl.niclabs.skandium.examples.kmeans.lloyd.simulated.SDSimKmeans;
import cl.niclabs.skandium.examples.kmeans.model.AbstractKmeans;
import cl.niclabs.skandium.examples.kmeans.treebased.randomdecomposition.SdKDRDKmeans;
import cl.niclabs.skandium.examples.kmeans.treebased.sequential.SdKDSeqKmeans;
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
            case "sd-seq-pm":
                result = new SdSeqPmKmeans(config);
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
            case "kmd-sd-sm":
                result = new SDSMKmedian(config);
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