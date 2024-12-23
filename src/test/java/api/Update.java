package api;

public class Update {

    private String name;
    private String job;

    public Update(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public Update() {

    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }
}
