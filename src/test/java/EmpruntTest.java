import bibliotheque.*;

import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class EmpruntTest {
  private static Connection conn;
  private Livre livre;
  private Emprunt emprunt;
  private Client client;

  @BeforeAll
  static void initConnection() throws SQLException {
    conn = bibliotheque.BDDbilbio.getConnection();
    assertNotNull(conn, "Connexion à la BDD échouée !");
  }

  @BeforeEach
  void setup() {

    try {
      Statement stmt = conn.createStatement();
      stmt.execute("DELETE FROM Emprunts");
      stmt.execute("DELETE FROM Clients");
      stmt.execute("DELETE FROM Livres");

      client = new Client(conn);
      client.addToBDD("Dupont", "Jean");
      livre = new Livre(conn);
      livre.addToBDD("oui", "non");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testCreateEmprunt() throws SQLException {

    Statement stmt = conn.createStatement();
    emprunt = new Emprunt(conn);
    emprunt.addToBDD(client.findId("Dupont", "Jean"), livre.findId("oui", "non"),
        LocalDate.now(), 2);
    Integer id_client = client.findId("Dupont", "Jean");
    Integer id_livre = livre.findId("oui", "non");
    ResultSet rs = stmt
        .executeQuery("SELECT * FROM Emprunts WHERE id_client = " + id_client + " AND id_livre = " + id_livre);
    Integer result_id = null;
    while (rs.next()) {
      result_id = rs.getInt("id_emprunt");
    }

    assertNotNull(result_id, "L'emprunt n'existe pas ???");

  }

  @AfterAll
  static void closeConn() throws SQLException {
    Statement stmt = conn.createStatement();
    stmt.execute("DELETE FROM Emprunts");
    conn.close();
  }

}
