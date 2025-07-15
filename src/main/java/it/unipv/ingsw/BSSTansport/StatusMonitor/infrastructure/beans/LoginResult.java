package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans;

public class LoginResult {
    private final String nome;
    private final String cognome;
    private final boolean success;
    private String token=null;

    public static LoginResult failed() {
        return new LoginResult(null, null, false);
    }

    public static LoginResult success(String name, String surname) {
        return new LoginResult(name, surname, true);
    }

    private LoginResult(String name, String surname, boolean success) {
        this.nome = name;
        this.cognome = surname;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setToken(String token) throws Exception {
        if (this.token == null) {
            this.token = token;
        }
        else{
            throw new Exception("value is already assigned");
        }

    }

    public String getToken() {
        return token;
    }
}
