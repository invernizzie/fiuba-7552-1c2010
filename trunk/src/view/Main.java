package view;

import view.components.MyFrame;

import java.awt.*;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Frame f = new MyFrame("Digital Image Processing");
		f.setSize(new Dimension(800,600));
		f.setLocation(new Point(350,120));
		f.setBackground(new Color(200,200,200));
		f.setVisible(true);
		
	}

}