package optics;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.JFrame;

public class LensWindow extends JFrame implements ActionListener{
	LensPanelViewer lpv;
	LensPanelConfig lpc;
	BeamSimulator bs;

	LensWindow(){
		this.setTitle("Polarized Beam Collimator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		loadParameter();
		lpv=new LensPanelViewer();
		lpc=new LensPanelConfig(this);
		bs =new BeamSimulator();

		this.add(lpv,BorderLayout.NORTH);
		this.add(lpc,BorderLayout.SOUTH);
		this.setVisible(true);
		this.pack();
		this.actionPerformed(new ActionEvent(lpc,0,""));
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==lpc);{
			lpv.setBoundedBeams(bs.calc(lpc.getBeamList(),lpc.getLensList()));
			lpv.repaint();
		}
	}

	public void loadParameter(){
		try{
			Scanner scan = new Scanner(new File("param.txt"));
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] param = line.split("=");
				if(param.length>=2){

					if(param[0].trim().equals("DEFAULT_LENSE_POSITION")){
						Constant.DEFAULT_LENSE_POSITION=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("DEFAULT_LENSE_FOCUS")){
						Constant.DEFAULT_LENSE_FOCUS=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("DEFAULT_BEAM_POSITION")){
						Constant.DEFAULT_BEAM_POSITION=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("DEFAULT_BEAM_WAIST")){
						Constant.DEFAULT_BEAM_WAIST=Double.parseDouble(param[1].trim());
					}

					if(param[0].trim().equals("MIN_FOCUS_LENGTH")){
						Constant.MIN_FOCUS_LENGTH=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("MAX_FOCUS_LENGTH")){
						Constant.MAX_FOCUS_LENGTH=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("LEFT_BOUND")){
						Constant.LEFT_BOUND=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("RIGHT_BOUND")){
						Constant.RIGHT_BOUND=Double.parseDouble(param[1].trim());
					}

					if(param[0].trim().equals("WAVE_LENGTH")){
						Constant.WAVE_LENGTH=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_HEIGHT")){
						Constant.VIEW_HEIGHT=Integer.parseInt(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_SCALE_X_MIN")){
						Constant.VIEW_SCALE_X_MIN=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_SCALE_X_MAX")){
						Constant.VIEW_SCALE_X_MAX=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_SCALE_X_DEF")){
						Constant.VIEW_SCALE_X_DEF=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_SCALE_Y_MIN")){
						Constant.VIEW_SCALE_Y_MIN=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_SCALE_Y_MAX")){
						Constant.VIEW_SCALE_Y_MAX=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_SCALE_Y_DEF")){
						Constant.VIEW_SCALE_Y_DEF=Double.parseDouble(param[1].trim());
					}

					if(param[0].trim().equals("VIEW_OFFSET_RANGE_X")){
						Constant.VIEW_OFFSET_RANGE_X=Integer.parseInt(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_OFFSET_RANGE_Y")){
						Constant.VIEW_OFFSET_RANGE_Y=Integer.parseInt(param[1].trim());
					}

					if(param[0].trim().equals("VIEW_POSITION_SLIDER_MIN")){
						Constant.VIEW_POSITION_SLIDER_MIN=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_POSITION_SLIDER_MAX")){
						Constant.VIEW_POSITION_SLIDER_MAX=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_FOCUS_SLIDER_MIN")){
						Constant.VIEW_FOCUS_SLIDER_MIN=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_FOCUS_SLIDER_MAX")){
						Constant.VIEW_FOCUS_SLIDER_MAX=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_WAIST_SLIDER_MIN")){
						Constant.VIEW_WAIST_SLIDER_MIN=Double.parseDouble(param[1].trim());
					}
					if(param[0].trim().equals("VIEW_WAIST_SLIDER_MAX")){
						Constant.VIEW_WAIST_SLIDER_MAX=Double.parseDouble(param[1].trim());
					}
}
			}
		}catch(Exception e){
			// no param file
		}
	}
}
