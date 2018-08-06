package final_hw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BinaryFrame extends JFrame {
	final int DEFAULT_THRESH = 128;
	JPanel cotrolPanel;
	HistogramPanel histogramPanel;
	JPanel imagePanel;
	JPanel imagePanelBin;
	JButton btnShow;
	String filename = "D:/Munich.png";
	int thresh = DEFAULT_THRESH;;
	JSlider sliderBin;
	JLabel lbFilename;
	JTextField tfFilename;
	JTextField tfBinValue;
	int[][][] data;
	int[][] gray;
	int[] histogram;
	int height;
	int width;
	int[] arr = new int[256];
	BufferedImage img;

	BinaryFrame() {
		lbFilename = new JLabel("File name (png):");
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		setTitle("Homework 5: Binary Image");
		btnShow = new JButton("Show");
		btnShow.setBounds(10, 8, 80, 25);
		lbFilename.setBounds(110, 8, 100, 25);
		tfFilename = new JTextField();
		tfFilename.setText(filename);
		tfFilename.setBounds(205, 8, 200, 25);
		tfBinValue = new JTextField();
		tfBinValue.setText(thresh + "");
		tfBinValue.setBounds(280, 200, 40, 25);
		int xOffset1 = 7;
		sliderBin = new JSlider(0, 255, DEFAULT_THRESH);
		sliderBin.setBounds(0, 200, 270, 25);
		histogramPanel = new HistogramPanel();
		histogramPanel.setBounds(0 + xOffset1, 40, 256, 150);
		histogramPanel.setBackground(new Color(255, 255, 0));

		cotrolPanel = new JPanel();
		cotrolPanel.setBounds(0, 0, 1500, 500);
		cotrolPanel.setLayout(null);
		cotrolPanel.add(btnShow);
		cotrolPanel.add(lbFilename);
		cotrolPanel.add(tfFilename);
		cotrolPanel.add(sliderBin);
		cotrolPanel.add(histogramPanel);
		cotrolPanel.add(tfBinValue);

		imagePanel = new JPanel();
		imagePanel.setBounds(10, 240, 700, 700);
		imagePanelBin = new JPanel();
		imagePanelBin.setBounds(650, 240, 700, 700);

		add(imagePanel);
		add(cotrolPanel);
		add(imagePanelBin);

		btnShow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filename = tfFilename.getText();
				loadImage();
				Graphics g = imagePanel.getGraphics();
				g.drawImage(img, 0, 0, null);
				gray();
				
				
				for(int i = 0; i < gray.length; i++){
					for(int j = 0; j < gray[i].length; j++){
		                int n = gray[i][j];
		                arr[n]=arr[n]+1;
		            }
				}
				
				int max = findMax(arr);
				double rate = 150.0*0.9/(double)max;
				
				for(int k = 0; k < arr.length; k++){
					arr[k]=(int)(arr[k]*rate);
					System.out.println(arr[k]);
				}
				
				
				Graphics g2 = histogramPanel.getGraphics();
				histogramPanel.paintComponent(g2, 128, arr);
				drawBinary();

				
			}
		});

		sliderBin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				tfBinValue.setText(Integer.toString(sliderBin.getValue()));
				thresh = sliderBin.getValue();
				gray();
				Graphics g = histogramPanel.getGraphics();
                histogramPanel.paintComponent(g, thresh, arr);
				drawBinary();
			}
		});

		tfBinValue.addKeyListener(new KeyAdapter() {

			public void keyReleased(KeyEvent ke) {
				
				String typed = tfBinValue.getText();
				
				if (tfBinValue.getText().equals("")) {
					thresh = 0;
					tfBinValue.setText("0");
                    return;
                }
				
				if (!typed.matches("\\d+")) {
					return;
				}
				int value = Integer.parseInt(typed);
				sliderBin.setValue(value);
			}
		});

	}
	
	

	int[][][] binary() {
		int[][][] newData = new int[height][width][3];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (gray[i][j] >= thresh) {
					newData[i][j][0] = 255;
					newData[i][j][1] = 255;
					newData[i][j][2] = 255;
				} else {
					newData[i][j][0] = 0;
					newData[i][j][1] = 0;
					newData[i][j][2] = 0;
				}
			}

		}
		return newData;
	}

	void gray() {
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				gray[i][j] = Util.checkPixelBounds(Util.covertToGray(data[i][j][0], data[i][j][1], data[i][j][2]));
			}
		}
	}

	void drawBinary() {
		int[][][] newData = binary();
		BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				int color = Util.makeColor(newData[i][j][0], newData[i][j][1], newData[i][j][2]);
				newImg.setRGB(j, i, color);
			}
		}
		Graphics g1 = imagePanelBin.getGraphics();
		g1.drawImage(newImg, 0, 0, width, height, null);
	}

	void loadImage() {
		try {
			img = ImageIO.read(new File(filename));
		} catch (IOException e) {
			System.out.println("IO exception");
		}
		height = img.getHeight();
		width = img.getWidth();
		data = new int[height][width][3];
		gray = new int[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int rgb = img.getRGB(x, y);
				data[y][x][0] = Util.getR(rgb);
				data[y][x][1] = Util.getG(rgb);
				data[y][x][2] = Util.getB(rgb);
			}
		}
	}

	public static int findMax(int[] inputArray) {
		int maxValue = inputArray[0];
		for (int i = 1; i < inputArray.length; i++) {
			if (inputArray[i] > maxValue) {
				maxValue = inputArray[i];
			}
		}
		return maxValue;
	}

	public static void main(String[] args) {
		BinaryFrame frame = new BinaryFrame();
		frame.setSize(1500, 1500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
