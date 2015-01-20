package optics;

import java.io.File;
import java.util.Scanner;


//ƒpƒ‰ƒ[ƒ^“Ç‚Ýž‚Ý
public class ParameterLoader {
	public static void loadParameter(){
		try{
			Scanner scan = new Scanner(new File("param.txt"));
			while(scan.hasNextLine()){
				String line = scan.nextLine();
				String[] param = line.split("=");
				if(param.length>=2){

					if(param[0].trim().equals("DEFAULT_LENSE_POSITION")){
						Constant.DEFAULT_LENSE_POSITION=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("DEFAULT_LENSE_FOCUS")){
						Constant.DEFAULT_LENSE_FOCUS=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("DEFAULT_BEAM_POSITION")){
						Constant.DEFAULT_BEAM_POSITION=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("DEFAULT_BEAM_WAIST")){
						Constant.DEFAULT_BEAM_WAIST=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("MIN_FOCUS_LENGTH")){
						Constant.MIN_FOCUS_LENGTH=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("MAX_FOCUS_LENGTH")){
						Constant.MAX_FOCUS_LENGTH=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("LEFT_BOUND")){
						Constant.LEFT_BOUND=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("RIGHT_BOUND")){
						Constant.RIGHT_BOUND=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("WAVE_LENGTH")){
						Constant.WAVE_LENGTH=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_HEIGHT")){
						Constant.VIEW_HEIGHT=Integer.parseInt(param[1].trim());
					}else if(param[0].trim().equals("VIEW_WIDTH")){
						Constant.VIEW_WIDTH=Integer.parseInt(param[1].trim());
					}else if(param[0].trim().equals("VIEW_SCALE_X_MIN")){
						Constant.VIEW_SCALE_X_MIN=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_SCALE_X_MAX")){
						Constant.VIEW_SCALE_X_MAX=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_SCALE_X_DEF")){
						Constant.VIEW_SCALE_X_DEF=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_SCALE_Y_MIN")){
						Constant.VIEW_SCALE_Y_MIN=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_SCALE_Y_MAX")){
						Constant.VIEW_SCALE_Y_MAX=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_SCALE_Y_DEF")){
						Constant.VIEW_SCALE_Y_DEF=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_OFFSET_RANGE_X")){
						Constant.VIEW_OFFSET_RANGE_X=Integer.parseInt(param[1].trim());
					}else if(param[0].trim().equals("VIEW_OFFSET_RANGE_Y")){
						Constant.VIEW_OFFSET_RANGE_Y=Integer.parseInt(param[1].trim());
					}else if(param[0].trim().equals("VIEW_POSITION_SLIDER_MIN")){
						Constant.VIEW_POSITION_SLIDER_MIN=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_POSITION_SLIDER_MAX")){
						Constant.VIEW_POSITION_SLIDER_MAX=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_FOCUS_SLIDER_MIN")){
						Constant.VIEW_FOCUS_SLIDER_MIN=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_FOCUS_SLIDER_MAX")){
						Constant.VIEW_FOCUS_SLIDER_MAX=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_WAIST_SLIDER_MIN")){
						Constant.VIEW_WAIST_SLIDER_MIN=Double.parseDouble(param[1].trim());
					}else if(param[0].trim().equals("VIEW_WAIST_SLIDER_MAX")){
						Constant.VIEW_WAIST_SLIDER_MAX=Double.parseDouble(param[1].trim());
					}else{
						System.out.println("unknown parameter"+param[0]);
					}
				}
			}
		}catch(Exception e){
			// no param file
		}
	}
}
