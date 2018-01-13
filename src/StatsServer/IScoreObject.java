package StatsServer;

import java.io.Serializable;

public interface IScoreObject extends Serializable {
    String getUsername();
    void setUsername(String username);

    long getFinishTime();
    void setFinishTime(long _finishTime);
}
