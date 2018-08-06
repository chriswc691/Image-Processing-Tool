package final_hw;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class FilterFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelLP = new JPanel();
	JPanel cotrolPanelHP = new JPanel();
	ImagePanel imagePanel;
	ImagePanel imagePanel2;
	JButton btnShow;
	JButton btnLP = new JButton("Blur");
	JButton btnHP = new JButton("Sharp");
	int[][][] data;
	int[][][] newData;
	int height;
	int width;
	BufferedImage img = null;

	FilterFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("HW 8: Image Filterings");
		
		try {
			img = ImageIO.read(new File("yw.jpg"));
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
		
		
		
		btnShow = new JButton("Show Original");
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(btnLP);
		cotrolPanelShow.add(btnHP);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(20, 220, 1500, 1500);
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

		btnLP.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

				for (int i = 1; i < height - 1; i++) {
					for (int j = 1; j < width - 1; j++) {

						int[] arrayR = new int[9];
						
						arrayR[0] = Util.getR(img.getRGB(j - 1, i - 1));
						arrayR[1] = Util.getR(img.getRGB(j - 1, i));
						arrayR[2] = Util.getR(img.getRGB(j - 1, i + 1));
						arrayR[3] = Util.getR(img.getRGB(j, i - 1));
						arrayR[4] = Util.getR(img.getRGB(j, i));
						arrayR[5] = Util.getR(img.getRGB(j, i + 1));
						arrayR[6] = Util.getR(img.getRGB(j + 1, i - 1));
						arrayR[7] = Util.getR(img.getRGB(j + 1, i));
						arrayR[8] = Util.getR(img.getRGB(j + 1, i + 1));
						
						int avgR = Util.checkPixelBounds((arrayR[0]+arrayR[1]+arrayR[2]+arrayR[3]+arrayR[4]+arrayR[5]+arrayR[6]+arrayR[7]+arrayR[8])/9);

						int[] arrayG = new int[9];
						
						arrayG[0] = Util.getG(img.getRGB(j - 1, i - 1));
						arrayG[1] = Util.getG(img.getRGB(j - 1, i));
						arrayG[2] = Util.getG(img.getRGB(j - 1, i + 1));
						arrayG[3] = Util.getG(img.getRGB(j, i - 1));
						arrayG[4] = Util.getG(img.getRGB(j, i));
						arrayG[5] = Util.getG(img.getRGB(j, i + 1));
						arrayG[6] = Util.getG(img.getRGB(j + 1, i - 1));
						arrayG[7] = Util.getG(img.getRGB(j + 1, i));
						arrayG[8] = Util.getG(img.getRGB(j + 1, i + 1));
						
						int avgG = Util.checkPixelBounds((arrayG[0]+arrayG[1]+arrayG[2]+arrayG[3]+arrayG[4]+arrayG[5]+arrayG[6]+arrayG[7]+arrayG[8])/9);
						
						int[] arrayB = new int[9];
						
						arrayB[0] = Util.getB(img.getRGB(j - 1, i - 1));
						arrayB[1] = Util.getB(img.getRGB(j - 1, i));
						arrayB[2] = Util.getB(img.getRGB(j - 1, i + 1));
						arrayB[3] = Util.getB(img.getRGB(j, i - 1));
						arrayB[4] = Util.getB(img.getRGB(j, i));
						arrayB[5] = Util.getB(img.getRGB(j, i + 1));
						arrayB[6] = Util.getB(img.getRGB(j + 1, i - 1));
						arrayB[7] = Util.getB(img.getRGB(j + 1, i));
						arrayB[8] = Util.getB(img.getRGB(j + 1, i + 1));

						int avgB = Util.checkPixelBounds((arrayB[0]+arrayB[1]+arrayB[2]+arrayB[3]+arrayB[4]+arrayB[5]+arrayB[6]+arrayB[7]+arrayB[8])/9);

						int color = Util.makeColor(avgR, avgG, avgB);
						newImg.setRGB(j, i, color);
					}
				}
				
				Graphics g = imagePanel2.getGraphics();
				g.drawImage(newImg, 0, 0, null);
			}
        });
		
		
		btnHP.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
            	
            	BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

				for (int i = 1; i < height - 1; i++) {
					for (int j = 1; j < width - 1; j++) {

						int[] arrayR = new int[9];
						
						arrayR[0] = Util.getR(img.getRGB(j - 1, i - 1));
						arrayR[1] = Util.getR(img.getRGB(j - 1, i));
						arrayR[2] = Util.getR(img.getRGB(j - 1, i + 1));
						arrayR[3] = Util.getR(img.getRGB(j, i - 1));
						arrayR[4] = Util.getR(img.getRGB(j, i));
						arrayR[5] = Util.getR(img.getRGB(j, i + 1));
						arrayR[6] = Util.getR(img.getRGB(j + 1, i - 1));
						arrayR[7] = Util.getR(img.getRGB(j + 1, i));
						arrayR[8] = Util.getR(img.getRGB(j + 1, i + 1));
						
						int newR = Util.checkPixelBounds(arrayR[4] + (arrayR[0]*(-1)+arrayR[1]*(-1)+arrayR[2]*(-1)+arrayR[3]*(-1)+arrayR[4]*8+arrayR[5]*(-1)+arrayR[6]*(-1)+arrayR[7]*(-1)+arrayR[8]*(-1))/9);

						int[] arrayG = new int[9];
						
						arrayG[0] = Util.getG(img.getRGB(j - 1, i - 1));
						arrayG[1] = Util.getG(img.getRGB(j - 1, i));
						arrayG[2] = Util.getG(img.getRGB(j - 1, i + 1));
						arrayG[3] = Util.getG(img.getRGB(j, i - 1));
						arrayG[4] = Util.getG(img.getRGB(j, i));
						arrayG[5] = Util.getG(img.getRGB(j, i + 1));
						arrayG[6] = Util.getG(img.getRGB(j + 1, i - 1));
						arrayG[7] = Util.getG(img.getRGB(j + 1, i));
						arrayG[8] = Util.getG(img.getRGB(j + 1, i + 1));
						
						int newG = Util.checkPixelBounds(arrayG[4] + (arrayG[0]*(-1)+arrayG[1]*(-1)+arrayG[2]*(-1)+arrayG[3]*(-1)+arrayG[4]*8+arrayG[5]*(-1)+arrayG[6]*(-1)+arrayG[7]*(-1)+arrayG[8]*(-1))/9);
						
						int[] arrayB = new int[9];
						
						arrayB[0] = Util.getB(img.getRGB(j - 1, i - 1));
						arrayB[1] = Util.getB(img.getRGB(j - 1, i));
						arrayB[2] = Util.getB(img.getRGB(j - 1, i + 1));
						arrayB[3] = Util.getB(img.getRGB(j, i - 1));
						arrayB[4] = Util.getB(img.getRGB(j, i));
						arrayB[5] = Util.getB(img.getRGB(j, i + 1));
						arrayB[6] = Util.getB(img.getRGB(j + 1, i - 1));
						arrayB[7] = Util.getB(img.getRGB(j + 1, i));
						arrayB[8] = Util.getB(img.getRGB(j + 1, i + 1));

						int newB = Util.checkPixelBounds(arrayB[4] + (arrayB[0]*(-1)+arrayB[1]*(-1)+arrayB[2]*(-1)+arrayB[3]*(-1)+arrayB[4]*8+arrayB[5]*(-1)+arrayB[6]*(-1)+arrayB[7]*(-1)+arrayB[8]*(-1))/9);

						int color = Util.makeColor(newR, newG, newB);
						newImg.setRGB(j, i, color);
					}
				}
            	
            	Graphics g = imagePanel2.getGraphics();
				g.drawImage(newImg, 0, 0, null);
            }
        });
		
		
		
		
	}
	public static void main(String[] args) {
		JFrame frame;
		frame = new FilterFrame();
		frame.setSize(1500, 1500);
		frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
