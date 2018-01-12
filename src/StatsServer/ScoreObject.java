package StatsServer;

public class ScoreObject implements IScoreObject {
	private String finishTime;

	ScoreObject(String _finishTime) {
		this.finishTime = finishTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String _finishTime) {
		this.finishTime = _finishTime;
	}

}
