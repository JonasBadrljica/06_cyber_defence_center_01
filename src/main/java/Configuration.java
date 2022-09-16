public enum Configuration {
    INSTANCE;

    public final MersenneTwister randomNumberGenerator = new MersenneTwister(System.currentTimeMillis());

    public final String fileSeparator = System.getProperty("file.separator");
    public final String lineSeparator = System.getProperty("line.separator");

    public final String dataPath = "data" + fileSeparator;
    public final String logPath = "log" + fileSeparator;

    public final int maximumNumberOfHosts = 1000;
    public final int maximumNumberOfRecords = 1000000;
}