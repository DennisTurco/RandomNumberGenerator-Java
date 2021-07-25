package project1;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class NumberGeneratorListener implements ActionListener{
	
	private NumberGenerator nb;
	
	public NumberGeneratorListener() {}
	
	public NumberGeneratorListener(NumberGenerator f) {
		this.nb = f;
	}
	
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton)e.getSource();
		
		if(b.getText().equals("Clear")) {
			nb.Clear();
		}
		
		else if(b.getText().equals("Generate")) {
			try {
				nb.Generate();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
		
		else {
			nb.Exit();
		}
		
	}
}
