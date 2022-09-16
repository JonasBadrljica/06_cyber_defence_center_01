import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class DataGenerator {
    private final ArrayList<String> hostnameList;
    private final ArrayList<String> severityList;
    private final ArrayList<String> attackTypeList;
    private final ArrayList<Record> recordList;

    public DataGenerator() {
        hostnameList = new ArrayList<>();
        severityList = new ArrayList<>();
        attackTypeList = new ArrayList<>();
        recordList = new ArrayList<>();
    }

    /* PLEASE DO NOT MODIFY
    public static void main(String... args) {
        DataGenerator dataGenerator = new DataGenerator();
        dataGenerator.initHostnameList();
        dataGenerator.initSeverities();
        dataGenerator.initAttackTypes();
        dataGenerator.generateData();
        dataGenerator.generateToCSVFile();
    }*/

    public String generateRandomString(String candidateCharacters, int length) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < length; i++) {
            stringBuilder.append(candidateCharacters.charAt(Configuration.INSTANCE.randomNumberGenerator.nextInt(candidateCharacters.length())));
        }

        return stringBuilder.toString();
    }

    public void initHostnameList() {
        do {
            String randomHostname = generateRandomString("abcdefghijklmnopqrstuvwxyz1234567890", 5);
            if (!hostnameList.contains(randomHostname)) {
                hostnameList.add(randomHostname);
            }
        } while (hostnameList.size() < Configuration.INSTANCE.maximumNumberOfHosts);
    }

    public void initSeverities() {
        severityList.add("minor");
        severityList.add("major");
        severityList.add("critical");
    }

    public void initAttackTypes() {
        attackTypeList.add("a");
        attackTypeList.add("b");
        attackTypeList.add("c");
        attackTypeList.add("d");
        attackTypeList.add("e");
        attackTypeList.add("f");
        attackTypeList.add("g");
        attackTypeList.add("h");
    }

    public void generateData() {
        for (int i = 0; i < Configuration.INSTANCE.maximumNumberOfRecords; i++) {
            int randomHostnameIndex = Configuration.INSTANCE.randomNumberGenerator.nextInt(0, hostnameList.size() - 1);
            int randomSeverityIndex = Configuration.INSTANCE.randomNumberGenerator.nextInt(0, severityList.size() - 1);
            int randomAttackTypeIndex = Configuration.INSTANCE.randomNumberGenerator.nextInt(0, attackTypeList.size() - 1);
            Record record = new Record(i + 1, hostnameList.get(randomHostnameIndex),
                    Configuration.INSTANCE.randomNumberGenerator.nextInt(5, 240),
                    severityList.get(randomSeverityIndex),
                    attackTypeList.get(randomAttackTypeIndex),
                    Configuration.INSTANCE.randomNumberGenerator.nextInt(1, 4),
                    Configuration.INSTANCE.randomNumberGenerator.nextInt(1, 4));
            recordList.add(record);
        }
    }

    public void generateToCSVFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(Configuration.INSTANCE.dataPath + "records.csv"));

            for (Record record : recordList) {
                bufferedWriter.write(record.toString() + Configuration.INSTANCE.lineSeparator);
            }

            bufferedWriter.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
}