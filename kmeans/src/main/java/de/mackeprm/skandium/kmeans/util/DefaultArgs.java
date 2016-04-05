package de.mackeprm.skandium.kmeans.util;

public class DefaultArgs {

    public static String[] getOrDefault(String[] args, String flavor) {
        final String[] defaultArgs;
        if (args == null || args.length == 0) {
            defaultArgs = new String[]{"-f", flavor};
        } else {
            defaultArgs = args;
        }
        return defaultArgs;
    }
}
