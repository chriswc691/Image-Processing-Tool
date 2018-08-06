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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class RotationFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelBackColor = new JPanel();;
	JPanel cotrolPanelRotate = new JPanel(); 
	ImagePanel imagePanel;
	JButton btnShow;
	JButton btnRotate;
	JTextField tfRed = new JTextField(5);
	JTextField tfGreen = new JTextField(5);
	JTextField tfBlue = new JTextField(5);
	JTextField tfTheta = new JTextField(5);
	JLabel lbBk = new JLabel("Background Color");
	JLabel lbRed = new JLabel("Red (R)");
	JLabel lbGreen = new JLabel("Green (G)");
	JLabel lbBlue = new JLabel("Blue (B)");
	JLabel lbTheta = new JLabel("Angle(0~89)");
	BufferedImage img = null;
	final int[][][] data;
	int height;
	int width;

	
	
	RotationFrame(){
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		tfRed.setText("0");
		tfGreen.setText("0");
		tfBlue.setText("0");
	    tfTheta.setText("0");
	    setTitle("影像處理 作業 3");
	    
	    
	    try {
		    img = ImageIO.read(new File("rv.jpg"));
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		
		height = img.getHeight();
		width = img.getWidth();
		data = new int[height][width][3]; 
		
		for (int y=0; y<height; y++){
	    	for (int x=0; x<width; x++){
	    		int rgb = img.getRGB(x, y);
	    		data[y][x][0] = Util.getR(rgb);
	    		data[y][x][1] = Util.getG(rgb);
	    		data[y][x][2] = Util.getB(rgb);
	    	}
	    }
	    
		btnShow = new JButton("show");
		btnRotate = new JButton("Rotate");
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(1,7));
		cotrolPanelShow.add(btnShow);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelBackColor.add(lbBk); 
		cotrolPanelBackColor.add(lbRed);
		cotrolPanelBackColor.add(tfRed);
		cotrolPanelBackColor.add(lbGreen);
		cotrolPanelBackColor.add(tfGreen);
		cotrolPanelBackColor.add(lbBlue);
		cotrolPanelBackColor.add(tfBlue);
		cotrolPanelMain.add(cotrolPanelBackColor);
		cotrolPanelRotate.add(lbTheta);
		cotrolPanelRotate.add(tfTheta);
		cotrolPanelRotate.add(btnRotate);
		cotrolPanelMain.add(cotrolPanelRotate);
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		cotrolPanelMain.add(new JPanel());
		
		btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				Graphics g = imagePanel.getGraphics();
				g.drawImage(img, 0 , 0 , null);
			}
		});
		
		
		btnRotate.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				
				int theta = Integer.parseInt(tfTheta.getText());
				
				double angle = theta*(Math.PI/180);
				int newWidth = (int)Math.round(Math.abs(width*Math.cos(angle))+Math.abs(height*Math.sin(angle)));
				int newHeight = (int)Math.round(Math.abs(width*Math.sin(angle))+Math.abs(height*Math.cos(angle)));
				
				
				BufferedImage newImg = new BufferedImage(newWidth,newHeight,img.getType()); 
				
				
				for (int y=0; y<height; y++){       
			    	for (int x=0; x<width; x++){
			    		
			    		int newX = (int) (Math.cos(angle)*x + Math.sin(angle)*y);
			    		int newY = (int) (-Math.sin(angle)*x + Math.cos(angle)*y);
			    		
			    		int r = data[y][x][0];
			    		int g = data[y][x][1];
			    		int b = data[y][x][2];

			    		newX = Util.checkImageBounds(newX, newWidth);
			    		newY = Util.checkImageBounds((int)(newY + (Math.sin(angle)*width)), newHeight);
			    		
			    		newImg.setRGB(newX, newY, Util.makeColor(r, g, b));

					}
				}
				
				
				int[][][] newData = new int[newHeight][newWidth][3];
				
				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						newData[i][j][0] = data[i][j][0];
						newData[i][j][1] = data[i][j][1];
						newData[i][j][2] = data[i][j][2];
					}
				}
				
				
				fillBgColor(newData);
				Graphics g = imagePanel.getGraphics();
				g.drawImage(newImg, 0 , 0 , null);
			}
		});
		
		
		
		cotrolPanelMain.setBounds(0, 0,1200,200);
		getContentPane().add(cotrolPanelMain);
	    imagePanel = new ImagePanel();
	    imagePanel.setBounds(0,180, 1500,1500);
	    getContentPane().add(imagePanel);
	}
	
	
	public void fillBgColor(int [][][] fdata) {
		int bgR = Util.checkPixelBounds(Integer.parseInt(tfRed.getText()));
		int bgG = Util.checkPixelBounds(Integer.parseInt(tfGreen.getText()));
		int bgB = Util.checkPixelBounds(Integer.parseInt(tfBlue.getText()));
		
		for (int i = 0; i < fdata.length; i++){
			for (int j = 0; j < fdata[i].length; j++){
				fdata[i][j][0] = bgR;
				fdata[i][j][1] = bgG;
				fdata[i][j][2] = bgB;
			}
		}
		Graphics g = imagePanel.getGraphics();
		imagePanel.paintComponent(g, fdata);
	}

	
}

