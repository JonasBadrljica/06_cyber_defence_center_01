import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class HSQLDBCSVFileImport {
    private Connection connection;

    /* PLEASE DO NOT MODIFY
    public static void main(String... args) {
        HSQLDBCSVFileImport hsqldbcsvFileImport = new HSQLDBCSVFileImport();
        hsqldbcsvFileImport.init();
        hsqldbcsvFileImport.importCSVFile(Configuration.INSTANCE.dataPath + "records.csv");
        hsqldbcsvFileImport.shutdown();
    }*/

    public void startup() {
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            String driverName = "jdbc:hsqldb:";
            String databaseURL = driverName + Configuration.INSTANCE.dataPath + "records.db";
            String username = "sa";
            String password = "";
            connection = DriverManager.getConnection(databaseURL, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public synchronized void update(String sqlStatement) {
        try {
            Statement statement = connection.createStatement();
            int result = statement.executeUpdate(sqlStatement);

            if (result == -1) {
                System.out.println("error executing " + sqlStatement);
            }

            statement.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }

    public void dropTable() {
        System.out.println("--- dropTable");

        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("DROP TABLE data");
        System.out.println("sqlStringBuilder : " + sqlStringBuilder);

        update(sqlStringBuilder.toString());
    }

    public void createTable() {
        StringBuilder sqlStringBuilder = new StringBuilder();
        sqlStringBuilder.append("CREATE TABLE data ").append(" ( ");
        sqlStringBuilder.append("id BIGINT NOT NULL").append(",");
        sqlStringBuilder.append("hostname VARCHAR(5) NOT NULL").append(",");
        sqlStringBuilder.append("downtimeInMinutes INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("severity VARCHAR(8) NOT NULL").append(",");
        sqlStringBuilder.append("attackType VARCHAR(1) NOT NULL").append(",");
        sqlStringBuilder.append("source INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("shift INTEGER NOT NULL").append(",");
        sqlStringBuilder.append("PRIMARY KEY (id)");
        sqlStringBuilder.append(" )");
        update(sqlStringBuilder.toString());
    }

    public void init() {
        startup();
        dropTable();
        createTable();
    }

    public String buildSQLStatement(long id, String hostname, int downtimeInMinutes, String severity, String attackType, int source, int shift) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO data (id,hostname,downtimeInMinutes,severity,attackType,source,shift) VALUES (");
        stringBuilder.append(id).append(",");
        stringBuilder.append("'").append(hostname).append("'").append(",");
        stringBuilder.append(downtimeInMinutes).append(",");
        stringBuilder.append("'").append(severity).append("'").append(",");
        stringBuilder.append("'").append(attackType).append("'").append(",");
        stringBuilder.append(source).append(",");
        stringBuilder.append(shift);
        stringBuilder.append(")");
        //System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public void insert(long id, String hostname, int downtimeInMinutes, String severity, String attackType, int source, int shift) {
        update(buildSQLStatement(id, hostname, downtimeInMinutes, severity, attackType, source, shift));
    }

    public void importCSVFile(String fileName) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] strings = line.split(";");
                insert(Integer.parseInt(strings[0]), strings[1], Integer.parseInt(strings[2]),
                        strings[3], strings[4], Integer.parseInt(strings[5]), Integer.parseInt(strings[6]));
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    public void shutdown() {
        try {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            connection.close();
        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
        }
    }
}