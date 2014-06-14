package optics;

//ÉåÉìÉYãÊä‘Ç…Ç®ÇØÇÈÉrÅ[ÉÄ
public class BoundedBeam extends Beam{
	boolean isSourceFlag;
	double positionFrom;
	double positionTo;
	BoundedBeam(PolarizeType _type,double _beamPosition,double _beamWaist,double _waveLength,
			boolean _isSourceFlag,double _positionFrom,double _positionTo){
		super(_type,_beamPosition,_beamWaist,_waveLength);
		isSourceFlag=_isSourceFlag;
		positionFrom=_positionFrom;
		positionTo=_positionTo;
	}
	BoundedBeam(Beam b,
			boolean _isSourceFlag,double _positionFrom,double _positionTo){
		super(b.type,b.beamPosition,b.beamWaist,b.waveLength);
		isSourceFlag=_isSourceFlag;
		positionFrom=_positionFrom;
		positionTo=_positionTo;
	}
	public boolean isSource(){return isSourceFlag;}
	public double getPositionFrom(){return positionFrom;}
	public double getPositionTo(){return positionTo;}
}
