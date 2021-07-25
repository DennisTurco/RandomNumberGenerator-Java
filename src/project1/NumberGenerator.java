package project1;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.swing.*;


import java.util.Random; //per il random number generator

class NumberGenerator extends JFrame{
	
	private JTextField text1, text2;
	private JLabel ris;
	private Timer timer;
	private JButton btnGenerate;
	
	
	public NumberGenerator() {  //costruttore senza parametri
		
		//crazione finestra
		setTitle("Number Generator");
		setSize(500, 400);
		setLocation(700, 300);
		setLayout(new BorderLayout());
		setResizable(false);  //in questo modo la finestra non cambia dimensione
		getContentPane().setBackground(new Color(123, 50, 250)); //setta il colore dello sfondo
		
		ImageIcon image = new ImageIcon(".//res//logo.png"); //crea un'icona
		setIconImage(image.getImage());	//cambia l'icona del frame
		
				
		//creazione oggetto actionlistener
		NumberGeneratorListener g = new NumberGeneratorListener(this);
		
		
		//creazione Pannello1 --> Generate
		JPanel pan1 = new JPanel();
		pan1.setLayout(new GridLayout(7, 0, 0, 5));
		pan1.setBackground(new Color(18, 15, 37));
		this.add(pan1, BorderLayout.CENTER);
		
		JLabel title1 = new JLabel("From");
		title1.setFont(new Font("Serif", Font.BOLD, 20));
		title1.setForeground(Color.ORANGE);
		pan1.add(title1);
		
		text1 = new JTextField();
		text1.setFont(new Font("Comic Sans ms", Font.BOLD, 20));	
		pan1.add(text1);		

		JLabel title2 = new JLabel("To");
		title2.setFont(new Font("Serif", Font.BOLD, 20));
		title2.setForeground(Color.ORANGE);
		pan1.add(title2);
		
		text2 = new JTextField();
		text2.setFont(new Font("Comic Sans ms", Font.BOLD, 20));	
		pan1.add(text2);
		
		
		
		btnGenerate = new JButton("Generate");
		btnGenerate.setFont(new Font("Comic Sans ms", Font.BOLD, 20));
		//btnGenerate.setCursor();
		btnGenerate.setSize(40, 30);
		pan1.add(btnGenerate);
		btnGenerate.addActionListener(g);
		
		ris = new JLabel("", JLabel.CENTER);
		ris.setForeground(Color.GREEN);
		ris.setFont(new Font("Serif", Font.BOLD, 30));
		pan1.add(ris);
		
		
		
		//BORDI LATERALI
		JPanel panEast = new JPanel();
		panEast.setLayout(new FlowLayout(1, 50, 50));
		panEast.setBackground(new Color(18, 15, 37));
		this.add(panEast, BorderLayout.EAST);
		
		JPanel panWest = new JPanel();
		panWest.setLayout(new FlowLayout(1, 50, 50));
		panWest.setBackground(new Color(18, 15, 37));
		this.add(panWest, BorderLayout.WEST);
		
		JPanel panNorth = new JPanel();
		panNorth.setLayout(new FlowLayout(1, 20, 20));
		panNorth.setBackground(new Color(18, 15, 37));
		this.add(panNorth, BorderLayout.NORTH);		
		
		
		//creazione Pannello2 --> Exit & Clear
		JPanel pan2 = new JPanel();
		pan2.setLayout(new FlowLayout());
		pan2.setBackground(new Color(18, 15, 37));
		this.add(pan2, BorderLayout.SOUTH);
		
		JButton btnExit = new JButton("Exit");
		btnExit.setFont(new Font("Comic Sans ms", Font.BOLD, 15));
		pan2.add(btnExit);
		btnExit.addActionListener(g);
		
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Comic Sans ms", Font.BOLD, 15));
		pan2.add(btnClear);
		btnClear.addActionListener(g);
		
		
		//author
		JLabel author = new JLabel("Author: DennisTurco");
		author.setFont(new Font("Arial", Font.BOLD, 15));
		author.setHorizontalTextPosition(0);
		panNorth.add(author);
	}
	
	
	//METODI ASSOCIATI AI BOTTONI
	void Exit() { 	//Button Exit
		System.out.println("Event --> exit");
		System.exit(0);
		return;
	}
	
	void Clear(){ 	//Button Clear
		System.out.println("Event --> clear");
		text1.setText("");
		text2.setText("");
		ris.setVisible(false);
		return;
	}
	
	void Generate() throws UnsupportedAudioFileException, IOException, LineUnavailableException, NumberFormatException{	//Button Generate
		ris.setText(""); //pulisco tutto per evitare bugs
		
		
		String text = text1.getText();  //ottengo il testo dal JField
		if(control_error(text) == true) return;	//passo la stringa alla funzione "control_error" per fare tutti i controlli sulla correttezza
		int from = Integer.parseInt(text); //salvo su x il suo val di tipo int
		
			
		text = text2.getText();
		if(control_error(text) == true) return;	
		int to = Integer.parseInt(text);

		
		if (from > to) {
			ris.setVisible(true);
			ris.setForeground(Color.RED);
			ris.setFont(new Font("Serif", Font.BOLD, 14));
			ris.setText("First value must be shorter than the second!");
			System.out.println("EventError --> First value must be shorter than the second!");
			return;		
		}
		
		
		else {
			btnGenerate.setEnabled(false); //in questo modo il bottone non è cliccabile
			ris.setVisible(true);
			ris.setForeground(Color.GREEN);
			ris.setFont(new Font("Serif", Font.BOLD, 30));	
			
			// -------- Apertura Sound ----------
			File file = new File(".//res//dice.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
			Clip clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.start();
			
			
			// -------- Chiamata al timer ----------
			SimpleTimer(from, to);
			timer.start();
		}
	}
	
	void Generate(int from, int to) {	//Funzione Generate	
		Random val = new Random();
		int number;
		number = val.nextInt(to - from + 1) + from;
		System.out.println("Event --> Generate number: " + number);
		ris.setText("" + number); //uso un trucco per farlo vedere come string
	}
	
	
	//funzione timer
	public void SimpleTimer(int from, int to) {
		timer = new Timer(500, new ActionListener() {
			int seconds = 0;
			String loading = ".";
			
			@Override
			public void actionPerformed(ActionEvent e) {		
				ris.setText(loading);
				loading = loading + ".";
				seconds++;
				
				if(seconds == 4) {
					ris.setText("");
					timer.stop();
					Generate(from, to);
					btnGenerate.setEnabled(true); //a questo punto il bottone torna cliccabile
					
				}
			}
			
		});
	}
	
	
	//funzione per controlli
	public boolean control_error(String text) {
		if(text.length() == 0) {
			ris.setVisible(true);
			ris.setForeground(Color.YELLOW);
			ris.setFont(new Font("Serif", Font.BOLD, 14));
			ris.setText("Input number is missing!");
			System.out.println("EventError --> Input number is missing!");
			return true;
		}
		
		for(int i=0; i<text.length(); i++) {
			if(text.charAt(i) != '0' && text.charAt(i) != '1' && text.charAt(i) != '2' && text.charAt(i) != '3' && text.charAt(i) != '4' && text.charAt(i) != '5' && text.charAt(i) != '6' && text.charAt(i) != '7' && text.charAt(i) != '8' && text.charAt(i) != '9') {
				ris.setVisible(true);
				ris.setForeground(Color.RED);
				ris.setFont(new Font("Serif", Font.BOLD, 14));
				ris.setText("Input number must contains only numbers!");
				System.out.println("EventError --> Input number must contains only numbers!");
				return true;
			}	
		}
		
		if(text.length() > 9) {
			ris.setVisible(true);
			ris.setForeground(Color.RED);
			ris.setFont(new Font("Serif", Font.BOLD, 14));
			ris.setText("Input number is to large!");
			System.out.println("EventError --> Input number is to large!");
			return true;
		}
		
		return false;
	}
	
	
}
