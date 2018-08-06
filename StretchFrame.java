package final_hw;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class StretchFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;

	ImagePanel imagePanel;
	ImagePanel imagePanel2;

	JButton btnShow = new JButton("Show Original");
	JButton btnStretch1 = new JButton("Stretch 1 (min-max)");
	JButton btnStretch2 = new JButton("Stretch 2 (histogram)");

	int[][][] data;
	int[][][] newData;
	int height;
	int width;
	BufferedImage img = null;

	StretchFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("HW 7: Stretching");

		try {

			img = ImageIO.read(new File("Munich_gray_dark.png"));
			// img = ImageIO.read(new
			// File("file/Munich_gray_dark_white_noised.png"));
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		
		height = img.getHeight();
		width = img.getWidth();
		
		data = new int[height][width][3];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				data[y][x][0] = Util.getR(rgb);
				data[y][x][1] = Util.getG(rgb);
				data[y][x][2] = Util.getB(rgb);
			}
		}
		
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(btnStretch1);
		cotrolPanelShow.add(btnStretch2);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(20, 220, 720, 620);
		getContentPane().add(imagePanel);
		imagePanel2 = new ImagePanel();
		imagePanel2.setBounds(650, 220, 1500, 1500);
		getContentPane().add(imagePanel2);
		
		btnShow.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
            	Graphics g = imagePanel.getGraphics();
				g.drawImage(img, 0, 0, null);
            }
        });
		
		
		btnStretch1.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {
				
				int min = data[0][0][0];
				int max = data[0][0][0];
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
				
				for (int j = 0 ; j < height; j++) {
					for (int i = 0 ; i < width; i++) {
						
						if (data[j][i][0] < min) {
							min = data[j][i][0];
						}
						if (data[j][i][0] > max) {
							max = data[j][i][0];
						}
					}
				}
				System.out.println("min = " + min + "  max = " + max);

				for (int j = 0; j < height; j++) {

					for (int i = 0; i < width; i++) {
						double tmp = 255.0 * (1.0 * (double) data[j][i][0] - (double) min) / (double) (max - min);
						int newValue = Util.checkPixelBounds((int) Math.round(tmp));

						int color = Util.makeColor(newValue, newValue, newValue);
						newImg.setRGB(i, j, color);
					}
					
				}
				
				Graphics g1 = imagePanel2.getGraphics();
				g1.drawImage(newImg, 0, 0, null);
				
			}

        });
		
		btnStretch2.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
            	
            	int min = 0;
            	int temp = 0;
            	
				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
				
				int[] histogram = new int[256];
				int[] cdf = new int[256];

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						histogram[data[y][x][0]]++;
					}
				}
				
				for (int i = 0 ; i < 256 ; i++) {
					temp += histogram[i];
					cdf[i] = temp;
				}
				
				for (int i = 255 ; i>=0 ; i--) {
					if ( cdf[i] > 0 ){
						min = i;
					}
				}
				
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double tmp =  cdf[data[y][x][0]] * 255.0  / (width*height - min);
						
						int newValue = Util.checkPixelBounds((int) Math.round(tmp));

						int color = Util.makeColor(newValue, newValue, newValue);
						newImg.setRGB (x, y, color);
						
					}
				}
				
				Graphics g1 = imagePanel2.getGraphics();
				g1.drawImage(newImg, 0, 0, null);
			}
            
            
        });
		
	}

	public static void main(String[] argas) {
		JFrame frame;
		frame = new StretchFrame();
		frame.setSize(1500, 1500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
