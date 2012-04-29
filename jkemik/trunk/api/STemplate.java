package api;

import java.io.Serializable;

public class STemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean autoCapture = true;
	private boolean manualCapture = false;
	private boolean autoPass = false;
	private int maxWinVal = 2;

	public STemplate() {

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
		if(autoCapture){
			return "ON";
		}
		return "OFF";
	}
	public String getAutoPassStatus() {
		if(autoPass){
			return "ON";
		}
		return "OFF";
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
