package optics;

public class Constant {

	//発散した時のための焦点距離の極大極小値
	public static double MIN_FOCUS_LENGTH = 1e-3;
	public static double MAX_FOCUS_LENGTH = 1000;

	//ビームを計算する左右端
	public static double LEFT_BOUND = -30;
	public static double RIGHT_BOUND = 30;

	//波長
	public static double WAVE_LENGTH= 780e-9;


	//ビューの高さ
	public static int VIEW_HEIGHT = 400;
	public static int VIEW_WIDTH = 800;

	//ビューのスケール指数
	public static double VIEW_SCALE_X_MIN = 3;
	public static double VIEW_SCALE_X_MAX = 7;
	public static double VIEW_SCALE_X_DEF = 5;
	public static double VIEW_SCALE_Y_MIN = 2;
	public static double VIEW_SCALE_Y_MAX = 4;
	public static double VIEW_SCALE_Y_DEF = 3;

	//ビューのオフセット範囲
	public static int VIEW_OFFSET_RANGE_X = 800;
	public static int VIEW_OFFSET_RANGE_Y = 800;

	//ビームやレンズのデフォルト位置とスライダー可動範囲
	public static double DEFAULT_BEAM_POSITION = 0;
	public static double DEFAULT_LENSE_POSITION = 5e-2;
	public static double VIEW_POSITION_SLIDER_MIN=-0.1;
	public static double VIEW_POSITION_SLIDER_MAX=0.1;

	//ビームのウェストやレンズの焦点距離のデフォルトとスライダー可動範囲
	public static double DEFAULT_LENSE_FOCUS = 1e-2;
	public static double VIEW_FOCUS_SLIDER_MIN=-0.1;
	public static double VIEW_FOCUS_SLIDER_MAX=0.1;
	public static double DEFAULT_BEAM_WAIST = 1e-4;
	public static double VIEW_WAIST_SLIDER_MIN=1e-4;
	public static double VIEW_WAIST_SLIDER_MAX=1e-3;
}
