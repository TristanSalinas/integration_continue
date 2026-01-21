import bibliotheque.Livre;
import java.sql.Statement;
import org.junit.jupiter.api.*;
import java.sql.Connection;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

public class LivreTest {
    private Connection conn;
    private Livre livre;

    @BeforeEach
    void setup() throws SQLException {
        // Créer une NOUVELLE connexion pour chaque test
        conn = bibliotheque.BDDbilbio.getConnection();
        assertNotNull(conn, "Connexion à la BDD échouée !");

        // Nettoyer la table AVANT chaque test
        try (Statement stmt = conn.createStatement()) {
            stmt.execute("DELETE FROM Emprunts");
            stmt.execute("DELETE FROM Clients");
            stmt.execute("DELETE FROM Livres");
        }

        livre = new Livre(conn);
    }

    @AfterEach
    void tearDown() throws SQLException {
        // Fermer la connexion APRÈS chaque test
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    @Test
    void testAddAndFindLivre() throws SQLException {
        livre.addToBDD("TestLivre", "AuteurTest");
        Integer id = livre.findId("TestLivre", "AuteurTest");
        assertNotNull(id, "Le livre n'a pas été trouvé après insertion !");
    }

    @Test
    void testDeleteLivre() throws SQLException {
        // D'abord AJOUTER le livre avant de le supprimer !
        livre.addToBDD("TestLivre", "AuteurTest");

        // Maintenant on peut le supprimer
        livre.deleteFromBDD("TestLivre", "AuteurTest");
        Integer id = livre.findId("TestLivre", "AuteurTest");
        assertNull(id, "Le livre devrait avoir été supprimé !");
    }
}
