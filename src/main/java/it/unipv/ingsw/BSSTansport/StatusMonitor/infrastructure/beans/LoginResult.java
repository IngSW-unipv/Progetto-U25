package it.unipv.ingsw.BSSTansport.StatusMonitor.infrastructure.beans;

public class LoginResult {
    private final String name;
    private final String surname;
    private final boolean success;
    private String token=null;

    public static LoginResult failed() {
        return new LoginResult(null, null, false);
    }

    public static LoginResult success(String name, String surname) {
        return new LoginResult(name, surname, true);
    }

    private LoginResult(String name, String surname, boolean success) {
        this.name = name;
        this.surname = surname;
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
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
