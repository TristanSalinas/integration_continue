import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import bibliotheque.Client;
import bibliotheque.Emprunt;
import bibliotheque.Livre;

public class FunctionnalTest {
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
      stmt.execute("DELETE FROM Clients");
      stmt.execute("DELETE FROM Livres");
      stmt.execute("DELETE FROM Emprunts");
      client = new Client(conn);
      client.addToBDD("Dupont", "Jean");
      livre = new Livre(conn);
      livre.addToBDD("oui", "non");
      emprunt = new Emprunt(conn);
      emprunt.addToBDD(
          client.findId("Dupont", "Jean"),
          livre.findId("oui", "non"),
          LocalDate.now(), 2);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testCreateEmprunt() throws SQLException {

    Statement stmt = conn.createStatement();
    emprunt = new Emprunt(conn);
    emprunt.addToBDD(
        client.findId("Dupont", "Jean"),
        livre.findId("oui", "non"),
        LocalDate.now(), 2);

    Integer id_client = client.findId("Dupont", "Jean");
    Integer id_livre = livre.findId("oui", "non");

    ResultSet rs = stmt
        .executeQuery("SELECT * FROM Emprunts WHERE id_client = " + id_client + " AND id_livre = " + id_livre);

    ResultSet rs2 = stmt
        .executeQuery("SELECT disponible FROM Livres WHERE id_livre = " + id_livre);
    Integer result_id = null;
    Boolean result_livre_aviability = null;

    while (rs.next()) {
      result_id = rs.getInt("id_emprunt");
    }
    while (rs2.next()) {
      result_livre_aviability = rs.getBoolean("id_livre");
    }

    assertNotNull(result_id, "L'emprunt n'existe pas ???");
    assertNotNull(result_livre_aviability, "Le livre est encore disponible ???");

  }

  @Test
  void testDeleteEmpruntByLivre() throws SQLException {

  }

}