package final_hw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;


public class ImagePanel extends JPanel {
	
	public void paintComponent(Graphics g, int [][][] data) {
		super.paintComponent(g);
		
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				g.setColor(new Color(data[i][j][0], data[i][j][1], data[i][j][2]));
				g.drawLine(j, i, j, i);
			}
		}

	}
	
	public void paintComponent2(Graphics g) {
		paint(g);
	}
}