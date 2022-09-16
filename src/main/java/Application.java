import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

public class Application implements IApplication {
    private final List<Record> recordList;

    public Application() {
        recordList = loadRecords();
    }

    public static void main(String... args) {
        Application application = new Application();
        application.loadRecords();

        application.executeQuery01();
        application.executeQuery02();
        application.executeQuery03();
        application.executeQuery04();
        application.executeQuery05();
        application.executeQuery06();
        application.executeQuery07();
        application.executeQuery08();
        application.executeQuery09();
        application.executeQuery10();
        application.executeQuery11();
        application.executeQuery12();
        application.executeQuery13();
        application.executeQuery14();
    }

    public List<Record> loadRecords() {
        List<Record> result = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(Configuration.INSTANCE.dataPath + "records.csv"));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] entries = line.split(";");
                int id = Integer.parseInt(entries[0]);
                int downTimeInMinutes = Integer.parseInt(entries[2]);
                int source = Integer.parseInt(entries[5]);
                int shift = Integer.parseInt(entries[6]);
                result.add(new Record(id, entries[1], downTimeInMinutes, entries[3], entries[4], source, shift));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    public long executeQuery01() {
        System.out.println("--- query 01 (count)");
        System.out.println("SELECT COUNT(*) FROM data");
        long result = recordList.size();
        System.out.println("result : " + result);
        System.out.println();

        return result;
    }

    public void executeQuery02() {
        System.out.println("--- executeQuery02 ---");
        long result = recordList.stream()
                        .filter(x -> x.getSeverity().equals("major"))
                        .filter(x -> x.getAttackType().equals("e"))
                        .filter(x -> x.getSource() <= 2)
                        .filter(x -> x.getShift() == 4)
                        .count();
        System.out.println("result : " + result);
    }

    public void executeQuery03() {
        System.out.println("--- executeQuery03 ---");
        long result = recordList.stream()
                        .filter(x -> x.getSeverity().equals("major") || x.getSeverity().equals("critical"))
                        .filter(x -> x.getAttackType().equals("b"))
                        .filter(x -> x.getSource() == 4)
                        .filter(x -> x.getShift() >= 3)
                        .count();
        System.out.println("result : " + result);
    }

    public void executeQuery04() {
        System.out.println("--- executeQuery04 ---");
        System.out.println();
    }

    public void executeQuery05() {
        System.out.println("--- executeQuery05 ---");
        System.out.println();
    }

    public void executeQuery06() {
        System.out.println("--- executeQuery06 ---");
        System.out.println();
    }

    public void executeQuery07() {
        System.out.println("--- executeQuery07 ---");

        System.out.println();
    }

    public void executeQuery08() {
        System.out.println("--- executeQuery08 ---");
        System.out.println();
    }

    public void executeQuery09() {
        System.out.println("--- executeQuery09 ---");
        Map<String, Long> result = recordList.stream()
                        .collect(groupingBy(Record::getSeverity, Collectors.counting()));
        System.out.println("result : " + result);
    }

    public void executeQuery10() {
        System.out.println("--- executeQuery10 ---");
        Map<Integer, Long> result = recordList.stream()
                        .filter(x -> x.getAttackType().equals("d"))
                        .filter(x -> x.getSeverity().equals("major"))
                        .collect(groupingBy(Record::getShift, Collectors.counting()));
        System.out.println("result : " + result);
    }

    public void executeQuery11() {
        System.out.println("--- executeQuery11 ---");
        Map<String, Long> result= recordList.stream()
                .filter(x -> x.getAttackType().equals("a") || x.getAttackType().equals("b") || x.getAttackType().equals("c"))
                .filter(x -> x.getSource() == 3)
                .collect(groupingBy(Record::getAttackType, Collectors.counting()));
        System.out.println("result : " + result);
    }

    public void executeQuery12() {
        System.out.println("--- executeQuery12 ---");
        List<String> attackTypes = Arrays.asList("b","d","e");
        Map<Integer, Long> result = recordList.stream()
                .filter(x -> !attackTypes.contains(x.getAttackType()))
                .filter(x -> x.getShift() >= 2)
                .filter(x -> x.getDowntimeInMinutes() >= 30)
                .filter(x -> x.getDowntimeInMinutes() <= 90)
                .collect(groupingBy(Record::getSource, Collectors.counting()));

        System.out.println("Result: "+result);
    }

    public void executeQuery13() {
        System.out.println("--- executeQuery13 ---");
        List<String> attackTypes = Arrays.asList("b","d","e");
        Map<String, Integer> result = recordList.stream()
                .filter(x -> !attackTypes.contains(x.getAttackType()))
                .filter(x -> x.getShift() == 1)
                .filter(x -> x.getSource() == 1 || x.getSource() == 3)
                .collect(groupingBy(Record::getAttackType, summingInt(Record::getDowntimeInMinutes)));
        System.out.println("Result: "+ result);
    }

    public void executeQuery14() {
        System.out.println("--- executeQuery14 ---");
        List<String> attackTypes = Arrays.asList("b","d","e");
        Map<String, Double> result = recordList.stream()
                .filter(x -> x.getSeverity().equals("minor") || x.getSeverity().equals("major"))
                .filter(x -> attackTypes.contains(x.getAttackType()))
                .filter(x -> x.getSource() == 1)
                .filter(x -> x.getShift() >= 3)
                .collect(groupingBy(Record::getAttackType, averagingInt(Record::getDowntimeInMinutes))
                );
        System.out.println("Result: "+result);
    }
}
