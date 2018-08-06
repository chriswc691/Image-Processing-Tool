package final_hw;

import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class HSIFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelHue = new JPanel();
	JPanel cotrolPanelSat = new JPanel();
	JPanel cotrolPanelInt = new JPanel();
	JPanel cotrolPanelBin = new JPanel();
	JPanel cotrolPanelHSI = new JPanel();

	ImagePanel imagePanel;
	JButton btnShow;
	JButton btnHue = new JButton("Hue  Only");
	JButton btnSat = new JButton("Saturation  Only");
	JButton btnInt = new JButton("Intensity  Only");
	JButton btnHSI = new JButton("HSI  ALL");
	

	JSlider sliderHue;
	JSlider sliderSat;
	JSlider sliderInt;
	

	JLabel lbFile = new JLabel("File Name");
	JLabel lbHueBeging = new JLabel("-180");
	JLabel lbHueEnd = new JLabel("180");
	JLabel lbSatBeging = new JLabel("-100(%)");
	JLabel lbSatEnd = new JLabel("100(%)");;
	JLabel lbIntBeging = new JLabel("-100(%)");;
	JLabel lbIntEnd = new JLabel("100(%)");;
	JLabel lbBinBeging = new JLabel("0");;
	JLabel lbBinEnd = new JLabel("255");;

	JTextField tfFile = new JTextField(20);
	JTextField tfHueValue = new JTextField(3);
	JTextField tfSatValue = new JTextField(3);
	JTextField tfIntValue = new JTextField(3);
	JTextField tfBinValue = new JTextField(3);

	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;

	HSIFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Homework 4");
		tfFile.setText("D:/eclipse/WORKSPACE/photo/rv.jpg");
		exec();
		try {
		    img = ImageIO.read(new File(tfFile.getText()));
		} catch (IOException e1) {
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
	}

	void exec() {
		btnShow = new JButton("Show");
		tfHueValue.setText("0");
		tfSatValue.setText("0");
		tfIntValue.setText("0");
		tfBinValue.setText("127");
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(6, 1));
		sliderHue = new JSlider(-180, 180, 0);
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(lbFile);
		cotrolPanelShow.add(tfFile);
		cotrolPanelHue.add(lbHueBeging);
		cotrolPanelHue.add(sliderHue);
		cotrolPanelHue.add(lbHueEnd);
		cotrolPanelHue.add(tfHueValue);
		cotrolPanelHue.add(btnHue);
		sliderSat = new JSlider(-100, 100, 0);
		cotrolPanelSat.add(lbSatBeging);
		cotrolPanelSat.add(sliderSat);
		cotrolPanelSat.add(lbSatEnd);
		cotrolPanelSat.add(tfSatValue);
		cotrolPanelSat.add(btnSat);
		sliderInt = new JSlider(-100, 100, 0);
		cotrolPanelInt.add(lbIntBeging);
		cotrolPanelInt.add(sliderInt);
		cotrolPanelInt.add(lbIntEnd);
		cotrolPanelInt.add(tfIntValue);
		cotrolPanelInt.add(btnInt);
		cotrolPanelHSI.add(btnHSI);
		
		cotrolPanelBin.add(tfBinValue);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.add(cotrolPanelHue);
		cotrolPanelMain.add(cotrolPanelSat);
		cotrolPanelMain.add(cotrolPanelInt);
		cotrolPanelMain.add(cotrolPanelHSI);

		
		btnShow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
							
				Graphics g = imagePanel.getGraphics();
				g.drawImage(img, 0 , 0 , null);
			}
		});
		
		sliderHue.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		        tfHueValue.setText(Integer.toString(sliderHue.getValue()));
		      }
		});
		
		btnHue.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		        double hValue = sliderHue.getValue();
		        BufferedImage newImg = new BufferedImage(width,height,img.getType());
		        
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double h = hValue + Util.getHueFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						double s = Util.getSatFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						double i = Util.getIntFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						
						int color=Util.getRGBFromHSI(h,s,i).getRGB();
						newImg.setRGB(x, y, color);
			    	}
				}

				Graphics g = imagePanel.getGraphics();
				g.drawImage(newImg, 0, 0, null);
		      }
	    });
		
		
		sliderSat.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		        tfSatValue.setText(Integer.toString(sliderSat.getValue()));
		      }
		});
		
		btnSat.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		        double sValue = sliderSat.getValue();
		        BufferedImage newImg = new BufferedImage(width,height,img.getType());
		        
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double h = Util.getHueFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						double s = (1 + sValue/100) * Util.getSatFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						double i = Util.getIntFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						
						int color=Util.getRGBFromHSI(h,s,i).getRGB();
						newImg.setRGB(x, y, color);
			    	}
				}

				Graphics g = imagePanel.getGraphics();
				g.drawImage(newImg, 0, 0, null);
		      }
	    });
		
		
		sliderInt.addChangeListener(new ChangeListener() {
		      public void stateChanged(ChangeEvent e) {
		        tfIntValue.setText(Integer.toString(sliderInt.getValue()));
		      }
		});
		
		btnInt.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent arg0) {
		        double iValue = sliderInt.getValue();
		        BufferedImage newImg = new BufferedImage(width,height,img.getType());
		        
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double h = Util.getHueFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						double s = Util.getSatFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						double i = (1 + iValue/100) * Util.getIntFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						
						int color=Util.getRGBFromHSI(h,s,i).getRGB();
						newImg.setRGB(x, y, color);
			    	}
				}

				Graphics g = imagePanel.getGraphics();
				g.drawImage(newImg, 0, 0, null);
		      }
	    });

		btnHSI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				double hValue = sliderHue.getValue();
				double sValue = sliderSat.getValue();
				double iValue = sliderInt.getValue();
				BufferedImage newImg = new BufferedImage(width, height, img.getType());

				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double h = hValue + Util.getHueFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						double s = (1 + sValue/100) * Util.getSatFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						double i = (1 + iValue/100) * Util.getIntFromRGB(data[y][x][0], data[y][x][1], data[y][x][2]);
						
						int color=Util.getRGBFromHSI(h,s,i).getRGB();
						newImg.setRGB(x, y, color);
			    	}
				}

				Graphics g = imagePanel.getGraphics();
				g.drawImage(newImg, 0, 0, null);
		      }
	    });
		
		
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(0, 220, 1500, 1500);
		getContentPane().add(imagePanel);
		
	}
}
