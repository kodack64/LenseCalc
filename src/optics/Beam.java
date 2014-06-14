package optics;

public class Beam {
	protected PolarizeType type;
	protected double beamWaist;
	protected double beamPosition;
	protected double waveLength;
	Beam(PolarizeType _type,double _beamPosition,double _beamWaist,double _waveLength){
		type=_type;
		beamPosition=_beamPosition;
		beamWaist=_beamWaist;
		waveLength=_waveLength;
	}
	public double getRayleightLength(){
		return Math.PI*Math.pow(beamWaist,2)/waveLength;
	}
	public double getBeamWaist(double z){
		return beamWaist*Math.sqrt(1+Math.pow((z-beamPosition)/getRayleightLength(),2));
	}
	public PolarizeType getPolarize(){return type;}
	public double getBeamPosition(){return beamPosition;}
	public double getWaveLength(){return waveLength;}
	public double getBeamWaist(){
		return beamWaist;
	}
	public double getCurvature(double z){
		return (z-beamPosition)*(1+Math.pow(getRayleightLength()/(z-beamPosition),2));
	}
}
