package api;

public class UnSuccesRegister {
    private String error;

    public UnSuccesRegister(String error) {
        this.error = error;
    }

    public UnSuccesRegister() {
    }

    public String getError() {
        return error;
    }
}
