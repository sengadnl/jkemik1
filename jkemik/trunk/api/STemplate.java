package api;

import java.io.Serializable;


public class STemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean autoCapture = true;
	private boolean hh = true;
	private boolean ch = true;
	private boolean net = true;
	private boolean manualCapture = false;
	private boolean autoPass = true;
	private boolean gameSetupMode = true;
	private boolean playMode = false;
	private boolean systemSetupMode = false;
	private String theme = "Geeky";
	private int maxWinVal = 4;
	private double MaxPointPerPlayer = 0.0;
	private String language = "ENGLISH";
	private boolean[] memo = { true, false };

	public STemplate() {
		
	}

	public boolean isHh() {
		return hh;
	}

	public void setHh(boolean hh) {
		this.hh = hh;
		this.ch = !hh;
		this.net = !hh;
	}

	public boolean isCh() {
		return ch;
	}

	public void setCh(boolean ch) {
		this.hh = !ch;
		this.ch = ch;
		this.net = !ch;
	}

	public boolean isNet() {
		return net;
	}

	public void setNet(boolean net) {
		this.hh = !net;
		this.ch = !net;
		this.net = net;
	}

	public boolean isPlayMode() {
		return playMode;
	}

	public void setPlayMode(boolean playMode) {
		this.playMode = playMode;
		this.systemSetupMode = !playMode;
		this.gameSetupMode = !playMode;
	}

	public boolean isGameSetupMode() {
		return gameSetupMode;
	}

	public void setGameSetupMode(boolean gameSetupMode) {
		this.gameSetupMode = gameSetupMode;
		this.playMode = !gameSetupMode;
		this.systemSetupMode = !gameSetupMode;
	}

	public boolean isSystemSetupMode() {
		return systemSetupMode;
	}

	public void setSystemSetupMode(boolean systemSetupMode) {
		this.gameSetupMode = !systemSetupMode;
		this.playMode = !systemSetupMode;
		this.systemSetupMode = systemSetupMode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public double getMaxPointPerPlayer() {
		return MaxPointPerPlayer;
	}

	public void setMaxPointPerPlayer(double maxPointPerPlayer) {
		MaxPointPerPlayer = maxPointPerPlayer;
	}

	public boolean[] getMemo() {
		return memo;
	}

	public void setMemo(boolean c, boolean p) {
		if (c) {
			this.memo[0] = true;
		} else {
			this.memo[0] = false;
		}
		if (p) {
			this.memo[1] = true;
		} else {
			this.memo[1] = false;
		}
	}

	public void restaureMemo() {
		if (this.memo[0]) {
			this.autoCapture = true;
		} else {
			this.autoCapture = false;
		}

		if (this.memo[1]) {
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
	public String toString(){
		return "MaxPointPerPlayer: " + this.MaxPointPerPlayer + "\nMaxWinVal :" + this.maxWinVal;
	}

}
