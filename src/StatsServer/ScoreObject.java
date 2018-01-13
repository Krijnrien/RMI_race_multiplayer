package StatsServer;

public class ScoreObject implements IScoreObject {
    private String username;
    private long finishTime;

    public ScoreObject(String _username, long _finishTime) {
        this.username = _username;
        this.finishTime = _finishTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(long finishTime) {
        this.finishTime = finishTime;
    }
}