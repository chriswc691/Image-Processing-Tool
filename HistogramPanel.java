package final_hw;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class HistogramPanel extends JPanel {
	
	public void paintComponent(Graphics g, int thresh, int [] array) {
        super.paintComponent(g);
        g.setColor(new Color(255, 0, 0));
        g.drawLine(thresh, 0, thresh, 150);
        g.setColor(Color.gray);
        int i = 0;
        while (i < array.length) {
            g.drawLine(i, 150, i, 150 - array[i]);
            i++;
        }
        g.setColor(new Color(255, 0, 0));
        g.drawLine(thresh, 0, thresh, 150);
    }
}
