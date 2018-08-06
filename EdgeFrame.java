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
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class EdgeFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelSlider = new JPanel();
	JPanel cotrolPanelEdgeEnh = new JPanel();
	JSlider sliderThresh;
	ImagePanel imagePanel;
	ImagePanel imagePanel2;
	JButton btnShow;
	JButton btnEdgeDet = new JButton("Edge Detect");
	JTextField tfThresh = new JTextField(5);
	JLabel labThreshLow = new JLabel("Thresh: Low");
	JLabel labThreshHigh = new JLabel("High");

	int[][][] data;
	int height;
	int width;
	BufferedImage img = null;
	int thresh;

	EdgeFrame() {
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);

		try {
			img = ImageIO.read(new File("rv.jpg"));
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
		cotrolPanelShow.add(btnEdgeDet);
		cotrolPanelMain.add(cotrolPanelShow);
		sliderThresh = new JSlider(0, 10000, 1000);
		cotrolPanelSlider.add(labThreshLow);
		cotrolPanelSlider.add(sliderThresh);
		cotrolPanelSlider.add(labThreshHigh);
		tfThresh.setText("1000");
		cotrolPanelSlider.add(tfThresh);
		cotrolPanelMain.add(cotrolPanelSlider);
		cotrolPanelMain.setBounds(0, 0, 1200, 200);
		getContentPane().add(cotrolPanelMain);
		imagePanel = new ImagePanel();
		imagePanel.setBounds(20, 220, 720, 620);
		getContentPane().add(imagePanel);
		imagePanel2 = new ImagePanel();
		imagePanel2.setBounds(650, 220, 1500, 1500);
		getContentPane().add(imagePanel2);

		btnShow.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				Graphics g = imagePanel.getGraphics();
				g.drawImage(img, 0, 0, null);
			}
		});

		btnEdgeDet.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {

				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

				int thresh = Integer.parseInt(tfThresh.getText());

				int[][] gray = new int[height][width];

				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {
						gray[i][j] = Util
								.checkPixelBounds(Util.covertToGray(data[i][j][0], data[i][j][1], data[i][j][2]));
					}
				}

				for (int i = 0; i < height; i++) {
					for (int j = 0; j < width; j++) {

						int eX = gray[i][Util.checkImageBounds(j + 1, width)] - gray[i][j];
						int eY = gray[Util.checkImageBounds(i + 1, height)][j] - gray[i][j];

						if (eX * eX + eY * eY >= thresh) {
							newImg.setRGB(j, i, Util.makeColor(255, 255, 255));
						}
						else{
							newImg.setRGB(j, i, Util.makeColor(0, 0, 0));
						}
					}
				}
				
				Graphics g = imagePanel2.getGraphics();
				g.drawImage(newImg, 0, 0, null);
			}

			
			
		});

		sliderThresh.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				tfThresh.setText(String.valueOf(sliderThresh.getValue()));
			}
		});

	}// end of the constructor

	public static void main(String[] args) {
		JFrame frame = new EdgeFrame();
		frame.setTitle("HW 9: Edge Detection");
		frame.setSize(1500, 1500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}// end of the class
