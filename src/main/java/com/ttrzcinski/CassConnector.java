package com.ttrzcinski;

import java.net.InetSocketAddress;
import java.nio.file.Paths;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

public class CassConnector {

    private static String contactPoint = "https://secure-point.astra.datastax.com";
    private static int port = 9042;
    private static String keySpace = "CassMessages";
    private static String dataCenter = "datacenter1";

    private static String dbUser = "CassAdmin";
    private static String dbPass = "Cass!Pass4";
    private static String region = "europe-west-1";

    public void read() {
        // Call cassandra and read from table
        try (CqlSession session = CqlSession.builder().addContactPoint(new InetSocketAddress(contactPoint, port))
                .withLocalDatacenter(dataCenter).withKeyspace(keySpace).build()) {
            ResultSet rs = session.execute("select * from messages limit 5");
            for (Row row : rs.all()) {
                System.out.println(row.getString("title"));
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sys_info() {
        // Create the CqlSession object:
        try (CqlSession session = CqlSession.builder()
                .withCloudSecureConnectBundle(Paths.get("<</PATH/TO/>>secure-connect-CassMessages.zip"))
                .withAuthCredentials("username", "password")
                .build()) {
            // Select the release_version from the system.local table:
            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            //Print the results of the CQL query to the console:
            if (row != null) {
                System.out.println(row.getString("release_version"));
            } else {
                System.out.println("An error occurred.");
            }
        }
    }

    public void insert() {
        // Add new message to box
        try (CqlSession session = CqlSession.builder().addContactPoint(new InetSocketAddress(contactPoint, port))
                .withLocalDatacenter(dataCenter).withKeyspace(keySpace).build()) {

            PreparedStatement prepared = session.prepare("insert into messages (email, title) values (?,?)");
            BoundStatement bound = prepared.bind("somone@example.com", "test title 1");
            session.execute(bound);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
