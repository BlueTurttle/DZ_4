package api;

public class RegisterSucces {

    private Integer id;
    private String token;

    public RegisterSucces() {
    }

    public RegisterSucces(Integer id, String token) {
        this.id = id;
        this.token = token;
    }

    public Integer getId() {
        return id;
    }

    public String getToken() {
        return token;
    }
}
