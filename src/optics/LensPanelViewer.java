package optics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;


//ビューペイン
public class LensPanelViewer extends JPanel implements MouseListener , MouseMotionListener{

	private static int wndWidth=800;
	private static int wndHeight=400;

	private Point clickPoint;
	private Point motionPoint;
	ArrayList<BoundedBeam> bblist;

	LensPanelViewer(){
		wndHeight = Constant.VIEW_HEIGHT;
		this.setPreferredSize(new Dimension(wndWidth,wndHeight));
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		clickPoint=new Point(0,0);
		motionPoint = new Point(0,0);
	}

	public void setBoundedBeams(ArrayList<BoundedBeam> _bblist){
		bblist=_bblist;
	}

	@Override
	public void paintComponent(Graphics g){
		int maxNumberLength = 8;
		int lineNumber=500;

		super.paintComponent(g);

		g.setColor(Color.white);
		g.fillRect(0, 0, wndWidth, wndHeight);

		//軸を表示
		g.setColor(Color.lightGray);
		g.drawLine(0,UnitConverter.offsetY+wndHeight/2,wndWidth,UnitConverter.offsetY+wndHeight/2);
		g.drawLine(UnitConverter.offsetX+wndWidth/2,0,UnitConverter.offsetX+wndWidth/2,wndHeight);

		double meterLeft=UnitConverter.PixelToMeterX(-wndWidth/2-UnitConverter.offsetX);
		double meterRight=UnitConverter.PixelToMeterX(wndWidth/2-UnitConverter.offsetX);


		if(bblist!=null){
			g.setColor(Color.black);
			int count=0;
			String num;
			Point p;

			//クリックされたポイントを表示
			p= new Point(clickPoint.x,clickPoint.y);
			{
				p.x-=UnitConverter.offsetX;
				num = String.valueOf(UnitConverter.PixelToMeterX(p.x)*1e2);
				g.drawString("Clicked Position",0,(count+1)*16);count++;
				g.drawString("   Position : " + num.substring(0, Math.min(maxNumberLength,num.length())) + " cm",0,(count+1)*16);count++;

				//指定した点が含まれるビーム区間を探し、情報（位置、ウェスト、曲率半径）を表示する
				for(BoundedBeam bb : bblist){
					int pixelFrom = UnitConverter.MeterToPixelX(bb.getPositionFrom());
					int pixelTo = UnitConverter.MeterToPixelX(bb.getPositionTo());
					if(pixelFrom < p.x  && p.x<=pixelTo){
						if(bb.type==PolarizeType.H)g.setColor(Color.red);
						if(bb.type==PolarizeType.V)g.setColor(Color.blue);
						if(bb.type==PolarizeType.C)g.setColor(Color.yellow);
						num = String.valueOf(bb.getBeamWaist(UnitConverter.PixelToMeterX(p.x))*1e3);
						g.drawString("   Waist : " + num.substring(0, Math.min(maxNumberLength,num.length())) + " mm", 0, (count+1)*16);count++;
						num = String.valueOf(bb.getCurvature(UnitConverter.PixelToMeterX(p.x))*1e3);
						g.drawString("   Curvature : " + num.substring(0, Math.min(maxNumberLength,num.length())) + " mm", 0, (count+1)*16);count++;

						//曲率半径が発散していなければ曲率半径を表す円を表示
						double curv = bb.getCurvature(UnitConverter.PixelToMeterX(p.x));
						if(!Double.isNaN(curv)){
							double z = UnitConverter.PixelToMeterX(p.x);
							double waist = bb.getBeamWaist(UnitConverter.PixelToMeterX(p.x));
							double theta = Math.asin(waist/curv);
							double centx = z-curv*Math.cos(theta);
							int pixcx=wndWidth/2+UnitConverter.offsetX+UnitConverter.MeterToPixelX(centx);
							int pixcy=wndHeight/2+UnitConverter.offsetY;
							int radius = Math.abs(UnitConverter.MeterToPixelX(curv));
							if(bb.type==PolarizeType.H)g.setColor(new Color(0xff,0xbb,0xbb));
							if(bb.type==PolarizeType.V)g.setColor(new Color(0xbb,0xbb,0xff));
							if(bb.type==PolarizeType.C)g.setColor(new Color(0xff,0xff,0xbb));
							g.drawOval(
									pixcx-radius,
									pixcy-radius,
									radius*2,
									radius*2
									);
							g.setColor(Color.black);
						}
						g.setColor(Color.lightGray);
						g.drawLine(
								p.x+wndWidth/2+UnitConverter.offsetX
								,UnitConverter.offsetY+wndHeight/2
								,p.x+wndWidth/2+UnitConverter.offsetX
								,UnitConverter.offsetY+wndHeight/2-UnitConverter.MeterToPixelY(bb.getBeamWaist(UnitConverter.PixelToMeterX(p.x))));
						g.setColor(Color.black);
					}
				}
			}
			//現在マウスが指す地点を表示
			p= new Point(motionPoint.x,motionPoint.y);
			{
				p.x-=UnitConverter.offsetX;
				num = String.valueOf(UnitConverter.PixelToMeterX(p.x)*1e2);
				g.drawString("Mouse Position",0,(count+1)*16);count++;
				g.drawString("   Position : " + num.substring(0, Math.min(maxNumberLength,num.length())) + " cm",0,(count+1)*16);count++;
				num = String.valueOf(UnitConverter.PixelToMeterX(motionPoint.x-clickPoint.x)*1e2);
				g.drawString("   Distance : " + num.substring(0, Math.min(maxNumberLength,num.length())) + " cm",0,(count+1)*16);count++;
				for(BoundedBeam bb : bblist){
					int pixelFrom = UnitConverter.MeterToPixelX(bb.getPositionFrom());
					int pixelTo = UnitConverter.MeterToPixelX(bb.getPositionTo());
					if(pixelFrom < p.x  && p.x<=pixelTo){
						if(bb.type==PolarizeType.H)g.setColor(Color.red);
						if(bb.type==PolarizeType.V)g.setColor(Color.blue);
						if(bb.type==PolarizeType.C)g.setColor(Color.yellow);
						num = String.valueOf(bb.getBeamWaist(UnitConverter.PixelToMeterX(p.x))*1e3);
						g.drawString("   Waist : " + num.substring(0, Math.min(maxNumberLength,num.length())) + " mm", 0, (count+1)*16);count++;
						num = String.valueOf(bb.getCurvature(UnitConverter.PixelToMeterX(p.x))*1e3);
						g.drawString("   Curvature : " + num.substring(0, Math.min(maxNumberLength,num.length())) + " mm", 0, (count+1)*16);count++;

						double curv = bb.getCurvature(UnitConverter.PixelToMeterX(p.x));
						if(!Double.isNaN(curv)){
							double z = UnitConverter.PixelToMeterX(p.x);
							double waist = bb.getBeamWaist(UnitConverter.PixelToMeterX(p.x));
							double theta = Math.asin(waist/curv);
							double centx = z-curv*Math.cos(theta);
							int pixcx=wndWidth/2+UnitConverter.offsetX+UnitConverter.MeterToPixelX(centx);
							int pixcy=wndHeight/2+UnitConverter.offsetY;
							int radius = Math.abs(UnitConverter.MeterToPixelX(curv));
							if(bb.type==PolarizeType.H)g.setColor(new Color(0xff,0xbb,0xbb));
							if(bb.type==PolarizeType.V)g.setColor(new Color(0xbb,0xbb,0xff));
							if(bb.type==PolarizeType.C)g.setColor(new Color(0xff,0xff,0xbb));
							g.drawOval(
									pixcx-radius,
									pixcy-radius,
									radius*2,
									radius*2
									);
							g.setColor(Color.black);
						}
						g.drawLine(
								p.x+wndWidth/2+UnitConverter.offsetX
								,UnitConverter.offsetY+wndHeight/2
								,p.x+wndWidth/2+UnitConverter.offsetX
								,UnitConverter.offsetY+wndHeight/2-UnitConverter.MeterToPixelY(bb.getBeamWaist(UnitConverter.PixelToMeterX(p.x))));
					}
				}
			}

			//ビームを描画
			for(BoundedBeam bb : bblist){
				if(bb.isSource()){
					g.setColor(Color.black);
					g.drawOval(wndWidth/2+UnitConverter.offsetX+UnitConverter.MeterToPixelX(bb.getBeamPosition())-5,wndHeight/2+UnitConverter.offsetY-5,10,10);
				}

				double meterFrom = Math.max(bb.getPositionFrom(),meterLeft);
				double meterTo = Math.min(bb.getPositionTo(),meterRight);

				double step = (meterTo-meterFrom)/lineNumber;
				if(bb.type==PolarizeType.H)g.setColor(Color.red);
				if(bb.type==PolarizeType.V)g.setColor(Color.blue);
				if(bb.type==PolarizeType.C)g.setColor(Color.yellow);
				for(double cur = meterFrom ; cur < meterTo ; cur+=step){
					double cw = bb.getBeamWaist(cur);
					double ncur = Math.min(cur+step, meterTo);
					double cwn = bb.getBeamWaist(ncur);
					myLineDrawMeter(g,cur,cw,ncur,cwn);
					myLineDrawMeter(g,cur,-cw,ncur,-cwn);
				}
			}
		}
	}
	//画面中心を原点としてピクセルを指定し直線を描画
	private void myLineDrawPixel(Graphics g,int x1,int y1,int x2,int y2,boolean force){
		x1 = UnitConverter.addPixelOffsetX(x1)+wndWidth/2;
		x2 = UnitConverter.addPixelOffsetX(x2)+wndWidth/2;
		y1 = UnitConverter.addPixelOffsetY(y1)+wndHeight/2;
		y2 = UnitConverter.addPixelOffsetY(y2)+wndHeight/2;
		g.drawLine(x1, y1, x2, y2);
	}
	//画面中心を原点としてメートルを指定し直線を描画
	private void myLineDrawMeter(Graphics g,double x1,double y1,double x2,double y2){
		int px1 = UnitConverter.MeterToPixelX(x1);
		int py1 = UnitConverter.MeterToPixelY(y1);
		int px2 = UnitConverter.MeterToPixelX(x2);
		int py2 = UnitConverter.MeterToPixelY(y2);
		myLineDrawPixel(g,px1,py1,px2,py2,false);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		clickPoint = e.getPoint();
		clickPoint.x-=wndWidth/2;
		clickPoint.y-=wndHeight/2;
		this.repaint();
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		motionPoint = e.getPoint();
		motionPoint.x-=wndWidth/2;
		motionPoint.y-=wndHeight/2;
		this.repaint();
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	@Override
	public void mouseDragged(MouseEvent e) {}
}
