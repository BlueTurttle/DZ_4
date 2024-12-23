package api;

public class UpdateResponse extends Update{

    private String updatedAt;

    public UpdateResponse() {
        super();
    }

    public UpdateResponse(String name, String job, String updatedAt) {
        super(name, job);
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }
}
