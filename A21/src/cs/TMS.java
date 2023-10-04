package cs;

import java.awt.BorderLayout;
import java.io.Serial;

import javax.swing.JFrame;

public class TMS extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;
	
	public TMS () {
		
		// Create the main application window
        JFrame frame = new JFrame("Turing Machine Server");
        frame.setLayout(new BorderLayout());
        
        frame.setVisible(true);
	}
}
