package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure;

import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.CheckpointSuLineaBean;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.LoginResult;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.OrariBean;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.nomeCheckBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class DBManager {
    private static final Logger log = LoggerFactory.getLogger("DBManager");

    private static volatile DBManager instance = null;

    private Connection connection;

    private DBManager() {
        try {
            this.openConnection();
        } catch (SQLException e) {
            log.error(e.getMessage());
            log.error("DB connection unsuccessful");
        } // TODO: handle
    }

    public static DBManager getInstance() {
        DBManager result = DBManager.instance;
        if (result == null) {
            synchronized (DBManager.class) {
                if (result == null) {
                    DBManager.instance = result = new DBManager();
                }
            }
        }
        return instance;
    }

    private void openConnection() throws SQLException {
        log.info("Opening connection to database...");
        this.connection = DriverManager.getConnection(
                "jdbc:mariadb://localhost:3306/statusMonitorDB",
                "admin", "adminpass"); // TODO: environment variables
        log.info("Database connection opened!");
    }

    private void closeConnection() throws SQLException {
        this.connection.close();
    }

    public nomeCheckBean[] getCheckpointNames() {
        ArrayList<nomeCheckBean> nomi = new ArrayList<>();

        try (PreparedStatement statement = this.connection.prepareStatement("SELECT id, nome FROM checkpoint")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                nomeCheckBean n = new nomeCheckBean(resultSet.getInt("id"), resultSet.getString("nome"));

                nomi.add(n);
            }
        } catch (SQLException e) {
            System.err.println(e); // TODO handle
        }
        return nomi.toArray(new nomeCheckBean[0]);
    }

    public Integer[] getLinee() {
        ArrayList<Integer> linee = new ArrayList<Integer>();

        try (PreparedStatement statement = this.connection.prepareStatement("SELECT nome FROM linea")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                linee.add(resultSet.getInt("nome"));
            }
        } catch (SQLException e) {
            System.err.println(e); // TODO handle
        }

        return linee.toArray(new Integer[0]);
    }

    public OrariBean[] getOrari(int linea) {
        ArrayList<OrariBean> orari = new ArrayList<OrariBean>();

        try (PreparedStatement statement = this.connection
                .prepareStatement("SELECT orario, capolinea FROM orari_inizio WHERE linea = ?")) {
            statement.setInt(1, linea);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                orari.add(new OrariBean(resultSet.getTime("orario").toLocalTime(), resultSet.getInt("capolinea")));
            }
        } catch (SQLException e) {
            System.err.println(e); // TODO handle
        }

        return orari.toArray(new OrariBean[0]);
    }

    public LoginResult checkAuth(String username, String passHash) throws SQLException {
        PreparedStatement statement = this.connection
                .prepareStatement("SELECT nome, cognome FROM conducente WHERE cf=? and pass_hash = ?");
        statement.setString(1, username);
        statement.setString(2, passHash);
        ResultSet resultSet = statement.executeQuery();

        // check number of lines returned, if !=1, the username and password are wrong
        resultSet.last();
        if (resultSet.getRow() != 1)
            return LoginResult.failed();

        return LoginResult.success(resultSet.getString("nome"), resultSet.getString("cognome"));
    }

    public CheckpointSuLineaBean[] getCheckpointSuLinea(int linea) throws SQLException {
        ArrayList<CheckpointSuLineaBean> checkpoints = new ArrayList<CheckpointSuLineaBean>();

        PreparedStatement statement = this.connection.prepareStatement("""
                SELECT cl.checkpoint, cl.numero, cl.delayS, tc.nome AS tipo, s.lunghezza, s.durata_fermataS
                    FROM checkpoint_su_linea AS cl
                    LEFT JOIN checkpoint AS c ON  cl.checkpoint=c.id
                    JOIN tipi_checkpoint tc ON c.tipo=tc.id
                    LEFT JOIN stazione AS s ON c.id=s.id
                    WHERE cl.linea=?""");
        statement.setInt(1, linea);
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            checkpoints.add(new CheckpointSuLineaBean(
                    resultSet.getInt("checkpoint"),
                    resultSet.getInt("numero"),
                    resultSet.getInt("delayS"),
                    resultSet.getString("tipo"),
                    resultSet.getInt("lunghezza"),
                    resultSet.getInt("durata_fermataS")));
        }

        return checkpoints.toArray(new CheckpointSuLineaBean[0]);
    }
}
