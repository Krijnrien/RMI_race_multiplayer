package StatsServer;

import java.io.Serializable;

public interface IScoreObject extends Serializable {
	String getFinishTime();

	void setFinishTime(String _finishTime);
}
