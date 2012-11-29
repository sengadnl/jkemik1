package api;

import java.io.Serializable;

public class STemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean autoCapture = true;
	private boolean manualCapture = false;
	private boolean autoPass = true;
	private String theme = "Geeky";
	private int maxWinVal = 4;

	private int[] memo = { 1, 0 };// new boolean[2];

	public STemplate() {
		//setMemo(autoCapture, autoPass);
	}

	public int[] getMemo() {
		return memo;
	}

	public void setMemo(boolean c, boolean p) {
		if(c){this.memo[0] = 1;}else{this.memo[0] = 0;}
		if(p){this.memo[1] = 1;}else{this.memo[1] = 0;}
	}

	public void restaureMemo() {
		if (this.memo[0] == 1) {
			this.autoCapture = true;
		} else {
			this.autoCapture = false;
		}

		if (this.memo[1] == 1) {
			this.autoPass = true;
		} else {
			this.autoPass = false;
		}
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getMaxWinVal() {
		return maxWinVal;
	}

	public void setMaxWinVal(int maxWinVal) {
		this.maxWinVal = maxWinVal;
	}

	public boolean isManualCapture() {
		return manualCapture;
	}

	public void setManualCapture(boolean manualCapture) {
		this.manualCapture = manualCapture;
	}

	public String getAutoCaptureStatus() {
		if (autoCapture) {
			return "AUTO";
		}
		return "MANUAL";
	}

	public String getAutoPassStatus() {
		if (autoPass) {
			return "AUTO";
		}
		return "MANUAL";
	}

	public String getManualCaptureStatus() {
		if (manualCapture) {
			return "AUTO";
		}
		return "MANUAL";
	}

	public boolean isAutoCapture() {
		return autoCapture;
	}

	public void setAutoCapture(boolean autoCapture) {
		this.autoCapture = autoCapture;
	}

	public boolean isAutoPass() {
		return autoPass;
	}

	public void setAutoPass(boolean autoPass) {
		this.autoPass = autoPass;
	}

}
