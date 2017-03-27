package n.play.calc;

/**
 * 计算结果 
 * @author onsou
 *
 */
public class WinResult {
	
	private Boolean isWin = Boolean.FALSE;
	
	private Integer winCount = 0;
	
	private Double winAmount = 0D;

	public Boolean getIsWin() {
		return isWin;
	}

	public void setIsWin(Boolean isWin) {
		this.isWin = isWin;
	}

	public Integer getWinCount() {
		return winCount;
	}

	public void setWinCount(Integer winCount) {
		this.winCount = winCount;
	}

	public Double getWinAmount() {
		return winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}
	
	

}
