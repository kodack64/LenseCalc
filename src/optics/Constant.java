package optics;

public class Constant {

	//���U�������̂��߂̏œ_�����̋ɑ�ɏ��l
	public static double MIN_FOCUS_LENGTH = 1e-3;
	public static double MAX_FOCUS_LENGTH = 1000;

	//�r�[�����v�Z���鍶�E�[
	public static double LEFT_BOUND = -30;
	public static double RIGHT_BOUND = 30;

	//�g��
	public static double WAVE_LENGTH= 780e-9;


	//�r���[�̍���
	public static int VIEW_HEIGHT = 400;
	public static int VIEW_WIDTH = 800;

	//�r���[�̃X�P�[���w��
	public static double VIEW_SCALE_X_MIN = 3;
	public static double VIEW_SCALE_X_MAX = 7;
	public static double VIEW_SCALE_X_DEF = 5;
	public static double VIEW_SCALE_Y_MIN = 2;
	public static double VIEW_SCALE_Y_MAX = 4;
	public static double VIEW_SCALE_Y_DEF = 3;

	//�r���[�̃I�t�Z�b�g�͈�
	public static int VIEW_OFFSET_RANGE_X = 800;
	public static int VIEW_OFFSET_RANGE_Y = 800;

	//�r�[���⃌���Y�̃f�t�H���g�ʒu�ƃX���C�_�[���͈�
	public static double DEFAULT_BEAM_POSITION = 0;
	public static double DEFAULT_LENSE_POSITION = 5e-2;
	public static double VIEW_POSITION_SLIDER_MIN=-0.1;
	public static double VIEW_POSITION_SLIDER_MAX=0.1;

	//�r�[���̃E�F�X�g�⃌���Y�̏œ_�����̃f�t�H���g�ƃX���C�_�[���͈�
	public static double DEFAULT_LENSE_FOCUS = 1e-2;
	public static double VIEW_FOCUS_SLIDER_MIN=-0.1;
	public static double VIEW_FOCUS_SLIDER_MAX=0.1;
	public static double DEFAULT_BEAM_WAIST = 1e-4;
	public static double VIEW_WAIST_SLIDER_MIN=1e-4;
	public static double VIEW_WAIST_SLIDER_MAX=1e-3;
}
