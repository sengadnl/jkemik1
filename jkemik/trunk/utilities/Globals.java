/**
 * 
 */
package utilities;
import java.awt.Color;

/**
 * @author Daniel Senga
 *
 */
public class Globals {
	public static final double PLR_N_LEN = 5;
	public static final double FADE_VARIANT = .35;//for fading colors
	public static final double LABEL_VARIANT = 120;//for fading colors
	public static final int FADE_THRESHOLD = 50;
	
	public static final String templateObjectFile = "/tmp/template.obj";
	public static final String tempFile = "tmp";
	public static final String gameObjectFile = "/tmp/game.obj";
	public static final String settingsTemplateObjectFile = "/tmp/stemplate.obj";
	public static final String VERSION = "Beta-5.0.332 ";
	
	public static final int POINT_VALUE = 1;
	public static final int REDEEMED_POINT_VALUE = 1;
	public static int CELL_CAPTURED = 1, CELL_FREE = 0, CELL_REDEEMED = 2;
	
	public static final int RED_INDEX = 0;
	public static final int WHITE_INDEX = 4;
	public static final int GREEN_INDEX = 5;
	public static final int YELLOW_INDEX = 3;
	public static final double MAX_WIN = .9;
	public static final int LIGHT_PURPPLE_INDEX = 3;
	public static final int DARK_PURPPLE_INDEX = 9;
	public static final int SQUARE_MIN_SIZE = 15;
	public static final int SQUARE_MAX_SIZE = 64;
	public static final double FRAME_WIDTH = 1280;
	public static final double FRAME_HEIGHT = 800;
	public static final double SIZE_PERCENT = .75;
	
	public static final Color[] CHEMIK_COLOR = {
		new Color(77, 192, 250),
		new Color(103, 250, 95),
		new Color(250, 250, 57)};
	public static final Color[] ORIGINE_COLOR = {
		new Color(232, 216, 37),
		new Color(99, 196, 235),
		new Color(45, 214, 68)};
	public static final Color[] CLASSIC_COLOR = {
		new Color(0, 255, 0),
		new Color(250, 50, 50),
		new Color(250, 250, 250)};
	public static final Color[] GEECKY_COLOR = {
		new Color(166, 171, 179),
		new Color(206, 156, 151),
		new Color(150, 196, 147)};

	
	public static final Color PENALTY_COL = new Color(224,27,208);
	public static final Color SYSPREFS_BUTTON_BGCOLOR = new Color(255, 150, 245);//
	public static final Color SYSPREFS_BUTTON_FGCOLOR = new Color(160, 29, 100);

	public static final Color EXIT_BUTTON_BGCOLOR = new Color(200, 20, 20);//
	public static final Color EXIT_BUTTON_FGCOLOR = new Color(100, 40, 40);
	
	public static final Color NEWG_BUTTON_BGCOLOR = new Color(30, 200, 30);
	public static final Color NEWG_BUTTON_FRCOLOR = new Color(20, 100, 20);
	
	public static final String[] code = {"en","fr","de"};
	public static final String[] laguageNames = {"ENGLISH","FRANCAIS","DEUTSCH"};
	
}
