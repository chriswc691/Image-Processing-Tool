package final_hw;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicFrame extends JFrame {
	JPanel cotrolPanel;
	ImagePanel imagePanel;// You must create your own ImagePanel
	JButton btnShow;
	JButton btnInverse;
	JButton btnGray;

	JButton btnUpDown;
	JButton btnLeftRight;
	JButton btnRotatetRight;
	JButton btnRotatetLeft;
	JButton btnRotate180;
	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;
	
	
	BasicFrame() {
		setBounds(0, 0, 1500, 1500);
		

		try {
			img = ImageIO.read(new File("rv.jpg"));
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		
		height = img.getHeight();
		width = img.getWidth();
		data = new int[height][width][3];
		
		for (int i = 0; i < data.length; i++){
			for (int j = 0; j < data[i].length; j++){
				int rgb = img.getRGB(j, i);
				data[i][j][0] = Util.getR(rgb);
				data[i][j][1] = Util.getG(rgb);
				data[i][j][2] = Util.getB(rgb);
			}
		}
		

		btnShow = new JButton("顯示");
		btnInverse = new JButton("反白 (負片效果)");
		btnGray = new JButton("灰階 (黑白片效果)");
		btnUpDown = new JButton("上下顚倒");
		btnLeftRight = new JButton("左右相反");
		btnRotatetRight = new JButton("向右90度");
		btnRotatetLeft = new JButton("向左90度");
		btnRotate180 = new JButton("旋轉180度");
		cotrolPanel = new JPanel();
		cotrolPanel.add(btnShow);
		cotrolPanel.add(btnInverse);
		cotrolPanel.add(btnGray);
		cotrolPanel.add(btnUpDown);
		cotrolPanel.add(btnLeftRight);
		cotrolPanel.add(btnRotatetRight);
		cotrolPanel.add(btnRotatetLeft);
		cotrolPanel.add(btnRotate180);
		
		btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, data);
			}
		});

		btnInverse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int [][][] newData = new int [height][width][3];
				
				for (int i = 0; i < data.length; i++){
					for (int j = 0; j < data[i].length; j++){
						newData[i][j][0] = 255 - data[i][j][0];
						newData[i][j][1] = 255 - data[i][j][1];
						newData[i][j][2] = 255 - data[i][j][2];
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
		});

		btnGray.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[][][] newData = new int[height][width][3];

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						
						int gray =  (int) (data[i][j][0]*0.299 + data[i][j][1]*0.587 + data[i][j][2]*0.11);
						newData[i][j][0] = gray;
						newData[i][j][1] = gray;
						newData[i][j][2] = gray;
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
		});
		
		btnUpDown.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int[][][] newData = new int[height][width][3];

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						
						newData[height-i-1][j][0] = data[i][j][0];
						newData[height-i-1][j][1] = data[i][j][1];
						newData[height-i-1][j][2] = data[i][j][2];
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
		});
		
		btnLeftRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int[][][] newData = new int[height][width][3];

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {

						newData[i][width-j-1][0] = data[i][j][0];
						newData[i][width-j-1][1] = data[i][j][1];
						newData[i][width-j-1][2] = data[i][j][2];
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
		});
		
		btnRotatetRight.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int[][][] newData = new int[width][height][3];

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						
						newData[j][height-i-1][0] = data[i][j][0];
						newData[j][height-i-1][1] = data[i][j][1];
						newData[j][height-i-1][2] = data[i][j][2];
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
		});
		
		btnRotatetLeft.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int[][][] newData = new int[width][height][3];

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						
						newData[width-j-1][i][0] = data[i][j][0];
						newData[width-j-1][i][1] = data[i][j][1];
						newData[width-j-1][i][2] = data[i][j][2];
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
		});
		
		btnRotate180.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				int[][][] newData = new int[height][width][3];

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						
						newData[height-i-1][width-j-1][0] = data[i][j][0];
						newData[height-i-1][width-j-1][1] = data[i][j][1];
						newData[height-i-1][width-j-1][2] = data[i][j][2];
					}
				}
				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent(g, newData);
			}
		});
		
		
		setLayout(new BorderLayout());
		add(cotrolPanel, BorderLayout.PAGE_START);

		imagePanel = new ImagePanel();
		add(imagePanel);
	}
}
