package cs;

import java.awt.BorderLayout;
import java.io.Serial;

import javax.swing.JFrame;

public class TMC extends JFrame {

	@Serial
	private static final long serialVersionUID = 1L;

	public TMC () {
		
		// Create the main application window
        JFrame frame = new JFrame("Turing Machine Client");
        frame.setLayout(new BorderLayout());
        
        frame.setVisible(true);
	}
}
