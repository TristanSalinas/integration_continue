import bibliotheque.Client;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class ClientTest {

  private static Connection conn;
  private Client client;

  private Integer findJean() {
    return client.findId("Dupont", "Jean");
  }

  @BeforeAll
  static void initConnection() throws SQLException {

    conn = bibliotheque.BDDbilbio.getConnection();
    assertNotNull(conn, "Connexion à la BDD échouée !");
  }

  @BeforeEach
  void setup() {
    try {
      Statement stmt = conn.createStatement();
      stmt.execute("DELETE FROM Clients");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    client = new Client(conn);
    client.addToBDD("Dupont", "Jean");
  }

  @Test
  void testCreateClient() throws SQLException {
    client.addToBDD("Michel", "Test");
    Integer id = client.findId("Michel", "Test");
    assertNotNull(id, "Le Client Michel n'a pas été trouvé après insertion !");
  }

  @Test
  void testFindClient() throws SQLException {
    Integer id = findJean();
    assertNotNull(id, "Le Client n'a pas été trouvé après insertion !");
  }

  @Test
  void testDeleteClient() throws SQLException {
    client.deleteFromBDD("Dupont", "Jean");
    Integer id = client.findId("Dupont", "Jean");
    assertNull(id, "Le Client n'a pas été supprimé après insertion !");
  }

  @AfterAll
  static void closeConn() throws SQLException {
    Statement stmt = conn.createStatement();
    stmt.execute("DELETE FROM Clients");
    conn.close();
  }
}
