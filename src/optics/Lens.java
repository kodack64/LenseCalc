package optics;

public class Lens {

	protected PolarizeType type;
	protected double focusLength;
	protected double position;

	Lens(PolarizeType _type,double _position,double _focusLength){
		focusLength=_focusLength;
		position=_position;
		type=_type;
		if(focusLength==0)focusLength=Constant.MIN_FOCUS_LENGTH;
	}
	public PolarizeType getPolarize(){return type;}
	public double getPosition(){return position;}
	public double getFocusLength(){return focusLength;}
}
