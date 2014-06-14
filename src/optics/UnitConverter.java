package optics;


//���[�g���s�N�Z���Ԃ̕ϊ�
//�X�P�[�������[�U�[���ύX����ꍇ�͂����ł͂Ȃ�param.txt�ŕύX����
public class UnitConverter {
	static double scaleX=1e5;
	static double scaleY=1e3;

	static int offsetX=0;
	static int offsetY=0;

	static double PixelToMeterX(int pix){
		return pix/scaleY;
	}
	static int MeterToPixelX(double met){
		return (int) (met*scaleY);
	}
	static double PixelToMeterY(int pix){
		return pix/scaleX;
	}
	static int MeterToPixelY(double met){
		return (int) (met*scaleX);
	}
	static int addPixelOffsetX(int pix){
		return pix+offsetX;
	}
	static int addPixelOffsetY(int pix){
		return pix+offsetY;
	}

	static void setScaleX(double sc){scaleX=sc;}
	static void setScaleY(double sc){scaleY=sc;}
	static void setOffsetX(int ofs){offsetX=ofs;}
	static void setOffsetY(int ofs){offsetY=ofs;}
}
