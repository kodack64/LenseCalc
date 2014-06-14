package optics;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class LensWindow extends JFrame implements ActionListener{
	LensPanelViewer lpv;
	LensPanelConfig lpc;
	BeamSimulator bs;

	LensWindow(){
		this.setTitle("Polarized Beam Collimator");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		ParameterLoader.loadParameter();
		lpv=new LensPanelViewer();
		lpc=new LensPanelConfig(this);
		bs =new BeamSimulator();

		this.add(lpv,BorderLayout.NORTH);
		this.add(lpc,BorderLayout.SOUTH);
		this.setVisible(true);
		this.pack();
		this.actionPerformed(new ActionEvent(lpc,0,""));
	}

	//�ĕ`�悪�K�v�ɂȂ����ۂ�ConfigPane����Ă΂���ViewPane�ɍĕ`����w�����郊�X�i
	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource()==lpc);{
			lpv.setBoundedBeams(bs.calc(lpc.getBeamList(),lpc.getLensList()));
			lpv.repaint();
		}
	}
}
