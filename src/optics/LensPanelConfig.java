package optics;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

public class LensPanelConfig extends JPanel{
	ArrayList<Lens> lens;
	ArrayList<Beam> beam;

	JButton buttonAddBeam;
	JButton buttonAddLens;
	JButton buttonAddVLens;
	JButton buttonAddHLens;
	JButton buttonRecalc;
	JButton buttonClear;
	JSlider sliderScaleX;
	JSlider sliderScaleY;
	JSlider sliderOffsetX;
	JSlider sliderOffsetY;
	int currentRow;
	JSlider sliderTable1;
	JSlider sliderTable2;
	boolean isSliderNotifierTable1;
	boolean isSliderNotifierTable2;
	JTable jt;
	DefaultTableModel tableModel;
	ButtonActionListener bal;

	ActionListener al;
	LensPanelConfig myInstance;
	public static final int sliderRange=10000;

	private class MyTableModel extends DefaultTableModel{
		MyTableModel(Object[] colNames,int rowCount){
			super(colNames,rowCount);
		}
		@Override
		public void setValueAt(Object aValue,int rowIndex,int columnIndex){

			try{
//				System.out.println(aValue);
				Double.parseDouble((String)aValue);
				super.setValueAt(aValue,rowIndex,columnIndex);
//				System.out.println("table value changed");
				recheckBeam();
				recheckLens();
				isSliderNotifierTable1 =true;
				isSliderNotifierTable2 =true;
				retargetSlider(jt.getSelectedRow());
			}catch(Exception e){
				System.out.println(String.valueOf(rowIndex));
				if(columnIndex==2)
					super.setValueAt("0",rowIndex,columnIndex);
				else
					super.setValueAt("0.1",rowIndex,columnIndex);
//				System.out.println("table value changed but invalid format");
				recheckBeam();
				recheckLens();
				isSliderNotifierTable1 =true;
				isSliderNotifierTable2 =true;
				retargetSlider(jt.getSelectedRow());
			}
			al.actionPerformed(new ActionEvent(myInstance,0,""));
		}
		@Override
		public boolean isCellEditable(int row,int col){
			if(col==0)return false;
			else return true;
		}
	}

	LensPanelConfig(ActionListener _al){
		al = _al;
		myInstance=this;

		this.setPreferredSize(new Dimension(640,300));

		lens = new ArrayList<Lens>();
		beam = new ArrayList<Beam>();

		bal = new ButtonActionListener();
		buttonAddBeam = new JButton("Add Beam");
		buttonAddLens = new JButton("Add Lens");
		buttonAddVLens = new JButton("Add V Cyl Lens");
		buttonAddHLens = new JButton("Add H Cyl Lens");
		buttonRecalc = new JButton("Recalc");
		buttonClear = new JButton("Clear Lens");

		sliderScaleX = new JSlider((int)(Constant.VIEW_SCALE_X_MIN*1e2),(int)(Constant.VIEW_SCALE_X_MAX*1e2),(int)(Constant.VIEW_SCALE_X_DEF*1e2));
		sliderScaleY = new JSlider((int)(Constant.VIEW_SCALE_Y_MIN*1e2),(int)(Constant.VIEW_SCALE_Y_MAX*1e2),(int)(Constant.VIEW_SCALE_Y_DEF*1e2));
		sliderOffsetX = new JSlider(-Constant.VIEW_OFFSET_RANGE_X,Constant.VIEW_OFFSET_RANGE_X,0);
		sliderOffsetY = new JSlider(-Constant.VIEW_OFFSET_RANGE_Y,Constant.VIEW_OFFSET_RANGE_Y,0);

//		sliderScaleX = new JSlider(300,700,500);
//		sliderScaleY = new JSlider(200,400,300);
//		sliderOffsetX = new JSlider(-800,800,0);
//		sliderOffsetY = new JSlider(-300,300,0);
		currentRow=-1;
		sliderTable1 = new JSlider(0,sliderRange,sliderRange/2);
		sliderTable2 = new JSlider(0,sliderRange,sliderRange/2);

		String[] colNames = {"ID","Polarize","Position(cm)","FocusLength/BeamWaist(mm)"};
		tableModel = new MyTableModel(colNames,0);
		jt = new JTable(tableModel);
		jt.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jt.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e){
				if(e.getValueIsAdjusting())return;
//				System.out.println("table changed");
				isSliderNotifierTable1 =true;
				isSliderNotifierTable2 =true;
				int sc=jt.getSelectedRow();
				retargetSlider(sc);
			}
		});
		jt.putClientProperty("terminateEditOnFocusLost", true);
		JScrollPane sp = new JScrollPane(jt);
		sp.setPreferredSize(new Dimension(500,120));
		String[][] rowData = {
				{"Beam1","V",String.valueOf(Constant.DEFAULT_BEAM_POSITION*1e2),String.valueOf(Constant.DEFAULT_BEAM_WAIST*1e3)},
				{"Beam2","H",String.valueOf(Constant.DEFAULT_BEAM_POSITION*1e2),String.valueOf(Constant.DEFAULT_BEAM_WAIST*1e3)}
			};
		tableModel.addRow(rowData[0]);
		tableModel.addRow(rowData[1]);
		beam.add(new Beam(PolarizeType.V,Constant.DEFAULT_BEAM_POSITION,Constant.DEFAULT_BEAM_WAIST,Constant.WAVE_LENGTH));
		beam.add(new Beam(PolarizeType.H,Constant.DEFAULT_BEAM_POSITION,Constant.DEFAULT_BEAM_WAIST,Constant.WAVE_LENGTH));

		buttonAddBeam.addActionListener(bal);
		buttonAddLens.addActionListener(bal);
		buttonAddVLens.addActionListener(bal);
		buttonAddHLens.addActionListener(bal);
		buttonRecalc.addActionListener(bal);
		buttonClear.addActionListener(bal);
		sliderScaleX.addChangeListener(bal);
		sliderScaleY.addChangeListener(bal);
		sliderOffsetX.addChangeListener(bal);
		sliderOffsetY.addChangeListener(bal);
		sliderTable1.addChangeListener(bal);
		sliderTable2.addChangeListener(bal);

		this.setLayout(new GridLayout(4,1));

		JPanel paneLensButton = new JPanel(new GridLayout(1,4));
		paneLensButton.setBorder(new EtchedBorder());
		paneLensButton.add(buttonAddLens);
		paneLensButton.add(buttonAddVLens);
		paneLensButton.add(buttonAddHLens);
		paneLensButton.add(buttonClear);
		this.add(paneLensButton);

		JPanel paneLensSlider = new JPanel(new GridLayout(4,2));
		paneLensSlider.setBorder(new EtchedBorder());
		paneLensSlider.add(new JLabel("Scale X"));
		paneLensSlider.add(sliderScaleY);
		paneLensSlider.add(new JLabel("Scale Y"));
		paneLensSlider.add(sliderScaleX);
		paneLensSlider.add(new JLabel("Offset X"));
		paneLensSlider.add(sliderOffsetX);
		paneLensSlider.add(new JLabel("Offset Y"));
		paneLensSlider.add(sliderOffsetY);
		this.add(paneLensSlider);

		this.add(sp);

		JPanel paneTableSlider = new JPanel(new GridLayout(2,2));
		paneTableSlider.add(new JLabel("Position"));
		paneTableSlider.add(sliderTable1);
		paneTableSlider.add(new JLabel("Focus/Waist"));
		paneTableSlider.add(sliderTable2);
		this.add(paneTableSlider);
		isSliderNotifierTable1=false;
		isSliderNotifierTable2=false;

//		this.add(buttonRecalc);
	}

	public ArrayList<Beam> getBeamList(){
		return beam;
	}
	public ArrayList<Lens> getLensList(){
		return lens;
	}

	private void addBeam(){
		beam.add(new Beam(PolarizeType.C,Constant.DEFAULT_BEAM_POSITION,Constant.DEFAULT_BEAM_WAIST,Constant.WAVE_LENGTH));
		String[] data = {"Beam"+String.valueOf(beam.size()),String.valueOf(Constant.DEFAULT_BEAM_POSITION*1e2),String.valueOf(Constant.DEFAULT_BEAM_WAIST*1e3)};
		tableModel.addRow(data);
	}
	private void addLens(){
		lens.add(new Lens(PolarizeType.C,Constant.DEFAULT_LENSE_POSITION,Constant.DEFAULT_LENSE_FOCUS));
		String[] data = {"Lens"+String.valueOf(lens.size()),"C",String.valueOf(Constant.DEFAULT_LENSE_POSITION*1e2),String.valueOf(Constant.DEFAULT_LENSE_FOCUS*1e3)};
		tableModel.addRow(data);
	}
	private void addVLens(){
		lens.add(new Lens(PolarizeType.V,Constant.DEFAULT_LENSE_POSITION,Constant.DEFAULT_LENSE_FOCUS));
		String[] data = {"Lens"+String.valueOf(lens.size()),"V",String.valueOf(Constant.DEFAULT_LENSE_POSITION*1e2),String.valueOf(Constant.DEFAULT_LENSE_FOCUS*1e3)};
		tableModel.addRow(data);
	}
	private void addHLens(){
		lens.add(new Lens(PolarizeType.H,Constant.DEFAULT_LENSE_POSITION,Constant.DEFAULT_LENSE_FOCUS));
		String[] data = {"Lens"+String.valueOf(lens.size()),"H",String.valueOf(Constant.DEFAULT_LENSE_POSITION*1e2),String.valueOf(Constant.DEFAULT_LENSE_FOCUS*1e3)};
		tableModel.addRow(data);
	}
	private void recheckBeam(){
//		System.out.println("beam updated");
		beam.clear();
		for(int i=0;i<tableModel.getRowCount();i++){
			if(tableModel.getValueAt(i, 0).toString().substring(0, 1).equals("B")){
				if(tableModel.getValueAt(i,1).toString().equals("C")){
					beam.add(new Beam(
							PolarizeType.C,
							Double.valueOf(tableModel.getValueAt(i,2).toString())*1e-2,
							Double.valueOf(tableModel.getValueAt(i,3).toString())*1e-3,
							Constant.WAVE_LENGTH
							));
				}
				if(tableModel.getValueAt(i,1).toString().equals("V")){
					beam.add(new Beam(
							PolarizeType.V,
							Double.valueOf(tableModel.getValueAt(i,2).toString())*1e-2,
							Double.valueOf(tableModel.getValueAt(i,3).toString())*1e-3,
							Constant.WAVE_LENGTH
							));
				}
				if(tableModel.getValueAt(i,1).toString().equals("H")){
					beam.add(new Beam(
							PolarizeType.H,
							Double.valueOf(tableModel.getValueAt(i,2).toString())*1e-2,
							Double.valueOf(tableModel.getValueAt(i,3).toString())*1e-3,
							Constant.WAVE_LENGTH
							));
				}
			}
		}
	}
	private void recheckLens(){
//		System.out.println("lens updated");
		lens.clear();
		for(int i=0;i<tableModel.getRowCount();i++){
			if(tableModel.getValueAt(i, 0).toString().substring(0, 1).equals("L")){
				if(tableModel.getValueAt(i,1).toString().equals("C")){
					lens.add(new Lens(
							PolarizeType.C,
							Double.valueOf(tableModel.getValueAt(i,2).toString())*1e-2,
							Double.valueOf(tableModel.getValueAt(i,3).toString())*1e-3
							));
				}
				if(tableModel.getValueAt(i,1).toString().equals("V")){
					lens.add(new Lens(
							PolarizeType.V,
							Double.valueOf(tableModel.getValueAt(i,2).toString())*1e-2,
							Double.valueOf(tableModel.getValueAt(i,3).toString())*1e-3
							));
				}
				if(tableModel.getValueAt(i,1).toString().equals("H")){
					lens.add(new Lens(
							PolarizeType.H,
							Double.valueOf(tableModel.getValueAt(i,2).toString())*1e-2,
							Double.valueOf(tableModel.getValueAt(i,3).toString())*1e-3
							));
				}
			}
		}
	}
	private void clearLens(){
		lens.clear();
		while(tableModel.getRowCount()>beam.size()){
			tableModel.removeRow(tableModel.getRowCount()-1);
		}
	}
	private void retargetSlider(int row){
		currentRow = row;
		double pos,val;
		pos = Double.valueOf(jt.getValueAt(row,2).toString())*1e-2;
		val = Double.valueOf(jt.getValueAt(row,3).toString())*1e-3;
		sliderTable1.setValue((int)((pos-Constant.VIEW_POSITION_SLIDER_MIN)/(Constant.VIEW_POSITION_SLIDER_MAX-Constant.VIEW_POSITION_SLIDER_MIN)*sliderRange));
		if(isRowBeam(row)){
			sliderTable2.setValue((int)((val-Constant.VIEW_WAIST_SLIDER_MIN)/(Constant.VIEW_WAIST_SLIDER_MAX-Constant.VIEW_WAIST_SLIDER_MIN)*sliderRange));
		}else{
			sliderTable2.setValue((int)((val-Constant.VIEW_FOCUS_SLIDER_MIN)/(Constant.VIEW_FOCUS_SLIDER_MAX-Constant.VIEW_FOCUS_SLIDER_MIN)*sliderRange));
		}
	}
	private void setTablePosition(){
		if(currentRow!=-1){
			double pos = (Constant.VIEW_POSITION_SLIDER_MIN+(Constant.VIEW_POSITION_SLIDER_MAX-Constant.VIEW_POSITION_SLIDER_MIN)
				*(sliderTable1.getValue()*1.0/sliderRange))*1e2;
			jt.setValueAt(String.valueOf(pos), currentRow, 2);
		}
	}
	private void setTableValue(){
		if(currentRow!=-1){
			double val;
			if(isRowBeam(currentRow)){
				val = (Constant.VIEW_WAIST_SLIDER_MIN+(Constant.VIEW_WAIST_SLIDER_MAX-Constant.VIEW_WAIST_SLIDER_MIN)
						*(sliderTable2.getValue()*1.0/sliderRange))*1e3;
			}else{
				val = (Constant.VIEW_FOCUS_SLIDER_MIN+(Constant.VIEW_FOCUS_SLIDER_MAX-Constant.VIEW_FOCUS_SLIDER_MIN)
						*(sliderTable2.getValue()*1.0/sliderRange))*1e3;
			}
			jt.setValueAt(String.valueOf(val), currentRow, 3);
		}
	}
	private boolean isRowBeam(int row){
		if(row<0)return false;
		return tableModel.getValueAt(row, 0).toString().substring(0, 1).equals("B");
	}

	private class ButtonActionListener implements ActionListener,ChangeListener{
		@Override
		public void actionPerformed(ActionEvent ae){
			if(ae.getSource()==buttonAddBeam){
				addBeam();
			}
			if(ae.getSource()==buttonAddLens){
				addLens();
			}
			if(ae.getSource()==buttonAddVLens){
				addVLens();
			}
			if(ae.getSource()==buttonAddHLens){
				addHLens();
			}
			if(ae.getSource()==buttonClear){
				clearLens();
			}
			if(ae.getSource()==buttonRecalc){
				recheckBeam();
				recheckLens();
			}
			al.actionPerformed(new ActionEvent(myInstance,0,""));
		}

		@Override
		public void stateChanged(ChangeEvent ce) {
//			System.out.println("slider bar moved");
			if(ce.getSource()==sliderScaleX){
				UnitConverter.setScaleX(Math.pow(10.0,sliderScaleX.getValue()/100.0));
			}else if(ce.getSource()==sliderScaleY){
				UnitConverter.setScaleY(Math.pow(10.0,sliderScaleY.getValue()/100.0));
			}else if(ce.getSource()==sliderOffsetX){
				UnitConverter.setOffsetX(sliderOffsetX.getValue());
			}else if(ce.getSource()==sliderOffsetY){
				UnitConverter.setOffsetY(sliderOffsetY.getValue());
			}
			if(ce.getSource()==sliderTable1){
				if(isSliderNotifierTable1){
					isSliderNotifierTable1=false;
				}else{
					setTablePosition();
					recheckBeam();
					recheckLens();
				}
			}
			if(ce.getSource()==sliderTable2){
				if(isSliderNotifierTable2){
					isSliderNotifierTable2=false;
				}else{
					setTableValue();
					recheckBeam();
					recheckLens();
				}
			}
			al.actionPerformed(new ActionEvent(myInstance,0,""));
		}
	}
}
