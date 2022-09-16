public class Record {
    private final int id;
    private final String hostname;
    private final int downtimeInMinutes;
    private final String severity;
    private final String attackType;
    private final int source;
    private final int shift;

    public Record(int id, String hostname, int downtimeInMinutes, String severity, String attackType, int source, int shift) {
        this.id = id;
        this.hostname = hostname;
        this.downtimeInMinutes = downtimeInMinutes;
        this.severity = severity;
        this.attackType = attackType;
        this.source = source;
        this.shift = shift;
    }

    public int getId() {
        return id;
    }

    public String getHostname() {
        return hostname;
    }

    public int getDowntimeInMinutes() {
        return downtimeInMinutes;
    }

    public String getSeverity() {
        return severity;
    }

    public String getAttackType() {
        return attackType;
    }

    public int getSource() {
        return source;
    }

    public int getShift() {
        return shift;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(id).append(";").append(hostname).append(";").append(downtimeInMinutes).append(";");
        stringBuilder.append(severity).append(";").append(attackType).append(";").append(source).append(";");
        stringBuilder.append(shift);
        return stringBuilder.toString();
    }
}