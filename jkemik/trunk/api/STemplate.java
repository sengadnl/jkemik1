package api;

import java.awt.Dimension;
import java.io.Serializable;
import java.util.ArrayList;


import utilities.Globals;
import utilities.Tools;

public class STemplate implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean hh,ch,net, 
                manualCapture,autoCapture,autoPass,
                gameSetupMode,playMode,systemSetupMode;
        private boolean[] memo = { true, true };
        
	private String theme = "Go",language = "ENGLISH";
	private int maxWinVal, backtrackingDistance, starterPoints;// 
	private double maxPointScaler;

	private GridDimension dimesion;
        
	private ArrayList<GridDimension> sizes;

	public STemplate() {
            this.dimesion = new GridDimension(new Dimension(0,0),0);
            hh = false;ch = true; net = false; 
            manualCapture = true; autoCapture = true; autoPass = true;
            gameSetupMode = true; playMode = false;systemSetupMode = false;
            
            backtrackingDistance = 2; starterPoints = 0;
            maxPointScaler = .8;
           
            this.boardSizes(Globals.SIZE_PERCENT);
            this.setGridDimension(sizes.get(3));
            this.maxWinVal = (int)(dimesion.positions() * Globals.MAX_WIN_PERCENT_BOARD);
	}
	
	public int getBacktrackingDistance() {
		return backtrackingDistance;
	}

	public void setBacktrackingDistance(int backtrackingDistance) {
		this.backtrackingDistance = backtrackingDistance;
	}

	public double getSqrSize(String str) {
		double sqrSize = 32;
		//System.out.println("GridDimensions: " + sizes);
		for (GridDimension size: sizes) {
			String s = size.toString();
			if(s.equals(str)){
				return size.getSqrSize();
			}
		}
		System.out.println("Grid size: " + sqrSize);
		return sqrSize;
	}
	public final void boardSizes(double grid_percent) {
		sizes = new ArrayList<>();

		// calculate board container dimension (pixels)
		int w_ini = (int) (Globals.FRAME_WIDTH * grid_percent);
		int h_ini = (int) (Globals.FRAME_HEIGHT * grid_percent);
		Dimension gcontainer = new Dimension(w_ini, h_ini);
		System.out.println("" + gcontainer);

		// Get list of square sizes
		ArrayList<Integer> sqrSizes = Tools.sqrtSizeGCD(new Dimension(w_ini, h_ini),
				Globals.SQUARE_MIN_SIZE);

		// Generate board list of board dimensions based on square sizes
		int index = 0;
		for (int temp : sqrSizes) {
			// Skip board container dimensions
			if (index == 0) {
				index++;
				continue;
			}
			System.out.print("SQR Size:" + temp + " -> ");
			sizes.add(new GridDimension(Tools.bSize(gcontainer, temp),temp));
		}
		System.out.println("Possible board sizes: " + sizes);
	}

	public ArrayList<GridDimension> getGridDimensions() {
		return sizes;
	}
	public String[] getGridDimensionsToString() {
		int len = sizes.size(), index = 0;
		String[] list = new String[len];
		for(GridDimension d: this.sizes){
			list[index] = d.toString();
			index++;
		}
		return list;
	}
	public void setSizes(ArrayList<GridDimension> sizes) {
		this.sizes = sizes;
	}
	public GridDimension getGridDimension() {
		return dimesion;
	}
	public final void setGridDimension(GridDimension dimesion) {
		this.dimesion = dimesion;
	}
	public void setGridDimesion(double sqrSize) {
		for(GridDimension d: sizes){
			if(sqrSize == d.getSqrSize()){
				this.dimesion = d;
			}
		}
	}
	public void setDimesion(String sqrSize) {
		for(GridDimension d: sizes){
			if(sqrSize.equals(d.toString())){
				this.dimesion = d;
			}
		}
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
	public boolean getPlayMode() {
		return this.playMode;
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
	/**
	 * @param None
	 * @return a pair integer sum of all plays for both players. The game end 
	 * when these play are completed*/
	public int getMaxPointPerPlayer() {
		int result = (int) (dimesion.positions() * maxPointScaler);
		if(result%2 == 0){
			return result;
		}
		return result + 1;
	}

	public double getMaxPointScaler() {
		return maxPointScaler;
	}

	public void setMaxPointScaler(double maxPointScaler) {
		this.maxPointScaler = maxPointScaler;
	}

	public boolean[] getMemo() {
		return memo;
	}

	/**
	 * @param c
	 *            = capture mode and p = pass mode
	 * @return void
	 * */
	public void setMemo(boolean c, boolean p) {
            this.memo[0] = c;
            this.memo[1] = p;
	}
//        public void setMemo(boolean c, boolean p) {
//		if (c) {
//			this.memo[0] = true;
//		} else {
//			this.memo[0] = false;
//		}
//                
//		if (p) {
//			this.memo[1] = true;
//		} else {
//			this.memo[1] = false;
//		}
//	}

	/**
	 * Remember the current game mode in case it is temporarily switched
	 * 
	 * @param none
	 * @return void
	 */
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

        public int getStarterPoints() {
            return starterPoints;
        }

        public void setStarterPoints(int starterPoints) {
            this.starterPoints = starterPoints;
        }
        
        @Override
	public String toString() {
		return  "\nMaxWinVal :" + this.maxWinVal;
	}

}
