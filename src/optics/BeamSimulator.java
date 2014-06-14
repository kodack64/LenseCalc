package optics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

//ビームとレンズの設定からレンズ区間ごとのビームの性質を計算する
public class BeamSimulator {

	private class LenseComparator implements Comparator<Lens>{
		public int compare(Lens o1,Lens o2){
			double dif = o1.getPosition()-o2.getPosition();
			if(dif<0)return -1;
			else if(dif>0)return 1;
			else return 0;
		}
	}

	ArrayList<BoundedBeam> bbl = new ArrayList<BoundedBeam>();

	public ArrayList<BoundedBeam> calc(ArrayList<Beam> bl, ArrayList<Lens> ll){
		Collections.sort(ll,new LenseComparator());
		bbl.clear();
		for(Beam b : bl){

			ArrayList<Lens> myLense = new ArrayList<Lens>();
			for(Lens lense : ll){
				if(lense.getPolarize()==b.getPolarize() || lense.getPolarize() == PolarizeType.C){
					myLense.add(lense);
				}
			}

			int myDivision=0;
			BoundedBeam currentBeam = new BoundedBeam(b,true,getDivisionLeft(myLense,myDivision),getDivisionRight(myLense,myDivision));
			bbl.add(currentBeam);
			for(int myCursor=myDivision+1;myCursor<=myLense.size();myCursor++){
				currentBeam = getNextBoundedBeam(currentBeam,myLense,myCursor);
				bbl.add(currentBeam);
			}
		}
		return bbl;
	}

	private double getDivisionLeft(ArrayList<Lens> ll, int division){
		if(division==0)return Constant.LEFT_BOUND;
		else return ll.get(division-1).getPosition();
	}
	private double getDivisionRight(ArrayList<Lens> ll, int division){
		if(division==ll.size())return Constant.RIGHT_BOUND;
		else return ll.get(division).getPosition();
	}

	private BoundedBeam getNextBoundedBeam(Beam beam,ArrayList<Lens> ll,int division ){

		Lens lense = ll.get(division-1);
		double distance = lense.getPosition()-beam.getBeamPosition();
		double focus = lense.focusLength;
		double rayleight = beam.getRayleightLength();
		double waist = beam.getBeamWaist();

		//発散が起きた場合は例外を処理
		double beamPosition;
		double rtninv;
		try{
			rtninv = 1/focus-1/(distance+Math.pow(rayleight, 2)/(distance-focus));
		}catch (ArithmeticException e){
			System.out.println("Error");
			rtninv = Constant.MIN_FOCUS_LENGTH;
		}
		if(rtninv==0){
			beamPosition=Constant.MAX_FOCUS_LENGTH;
			System.out.println("Error");
		}
		else beamPosition = lense.getPosition() + 1/rtninv;

		double beamWaist;
		beamWaist = waist/Math.sqrt(Math.pow(1-distance/focus,2)+ Math.pow(rayleight/focus,2));

		return new BoundedBeam(beam.type,beamPosition,beamWaist,beam.waveLength,false,getDivisionLeft(ll,division),getDivisionRight(ll,division));
	}
}
