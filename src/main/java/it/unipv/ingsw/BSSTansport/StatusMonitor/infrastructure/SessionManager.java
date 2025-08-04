package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure;

import it.unipv.ingsw.BSSTansport.StatusMonitor.data.Veicolo;
import it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans.LoginResult;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager {
    private static SessionManager instance;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder();

    private ConcurrentHashMap<String, String> sessions;

    private SessionManager() {
        sessions = new ConcurrentHashMap<>();
    }

    public static SessionManager getInstance() {
        SessionManager result = SessionManager.instance;
        if (result == null) {
            synchronized (SessionManager.class) {
                if (result == null) {
                    SessionManager.instance = result = new SessionManager();
                }
            }
        }
        return instance;
    }

    public String newToken(String vId) {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        String token = base64Encoder.encodeToString(randomBytes);
        sessions.put(token, vId);

        return token;
    }

    public boolean checkToken(String token) {
        return this.sessions.containsKey(token);
    }

    public String getVId(String token) {
        return this.sessions.get(token);
    }

    public void logout(String token) {
        this.sessions.remove(token);
    }

    public LoginResult login(String username, String password, String vId) throws Exception {
        // hash password
        String passHash = DigestUtils.sha256Hex(password);

        // checkDB
        LoginResult result = DBManager.getInstance().checkAuth(username, passHash);

        // generate token
        if (result.isSuccess()) {
            result.setToken(this.newToken(vId));
        }

        return result;
    }

    public String trovaToken(String vId) {
        String token = null;
        for (HashMap.Entry<String, String> entry : this.sessions.entrySet()) {
            if (entry.getValue().equals(vId)) {
                token = entry.getKey();
            }
        }
        return token;
    }
}
