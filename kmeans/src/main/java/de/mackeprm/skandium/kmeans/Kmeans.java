package de.mackeprm.skandium.kmeans;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import de.mackeprm.skandium.kmeans.implementations.lloyd.hybridpartition.HpKmeans;
import de.mackeprm.skandium.kmeans.implementations.lloyd.mapmaximization.MmKmeans;
import de.mackeprm.skandium.kmeans.implementations.lloyd.partialmerge.PmKmeans;
import de.mackeprm.skandium.kmeans.implementations.lloyd.sequential.SeqKmeans;
import de.mackeprm.skandium.kmeans.implementations.lloyd.sequential.SeqPmKmeans;
import de.mackeprm.skandium.kmeans.implementations.lloyd.sequentialmaximization.SmKmeans;
import de.mackeprm.skandium.kmeans.util.configuration.AbstractKmeans;
import de.mackeprm.skandium.kmeans.util.configuration.KMeansRunConfiguration;

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
            case "sm":
                result = new SmKmeans(config);
                break;
            case "mm":
                result = new MmKmeans(config);
                break;
            case "hp":
                result = new HpKmeans(config);
                break;
            case "seq":
                result = new SeqKmeans(config);
                break;
            case "seq-pm":
                result = new SeqPmKmeans(config);
                break;
            /*case "manual-pm":
                result = new SDManualPmKMeans(config);
                break;
            case "manual-sm":
                result = new SDManualSmKmeans(config);
                break;*/
            case "pm":
                result = new PmKmeans(config);
                break;
            /*case "kmd-seq":
                result = new SDSeqKmedian(config);
                break;
            case "kmd-mm":
                result = new SDMMKmedian(config);
                break;
            case "kmd-sm":
                result = new SDSMKmedian(config);
                break;
            case "fcm-seq":
                result = new SDSeqcmeans(config);
                break;
            case "fcm-sm":
                result = new SDSMFCMeans(config);
                break;
            case "kd-seq":
                result = new SdKDSeqKmeans(config);
                break;
            case "kd-rd":
                result = new SdKDRDKmeans(config);
                break;*/
            default:
                throw new IllegalStateException("unrecognized flavor:" + flavour);
        }
        return result;
    }
}