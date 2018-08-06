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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.Arrays;


public class NoiseFrame  extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();
	
	ImagePanel imagePanel;
	ImagePanel imagePanel2;
	ImagePanel imagePanel3;
	
	
	JButton btnShow = new JButton("Show Nonised Image");
	JButton btnMedianGray = new JButton("Remove Noise");
	JSlider slider;
	JLabel lbLess = new JLabel("Less Noise");
	JLabel lbMore = new JLabel("More Noise");
	
	int[][][] data;
	int[][][] noisedData;
	int[][][] noiseRemovedData;
	int height;
	int width;
	BufferedImage img = null;
	int thresh;
	
	 NoiseFrame (){
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
	    setTitle("HW 6: Removing Noise with a Median Filter");
		
		try {
			img = ImageIO.read(new File("Munich_gray_noised.png"));
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
		cotrolPanelMain.setLayout(new GridLayout(6,1));
		cotrolPanelShow.add(btnShow);
		cotrolPanelShow.add(btnMedianGray);
		cotrolPanelShow.add(lbLess);
		slider = new JSlider(0, 200,100);
		cotrolPanelShow.add(slider);
		cotrolPanelShow.add(lbMore);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelMain.setBounds(0, 0,1200,200);
		getContentPane().add(cotrolPanelMain);
	    imagePanel = new ImagePanel();
	    imagePanel.setBounds(20,50, 620,450);
	    getContentPane().add(imagePanel);
	    imagePanel2 = new ImagePanel();
	    imagePanel2.setBounds(630,50, 1230,450);
	    getContentPane().add(imagePanel2);
	    imagePanel3 = new ImagePanel();
	    imagePanel3.setBounds(630,470, 1200,1050);
	    getContentPane().add(imagePanel3);
	    
	    
	    btnShow.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
            	Graphics g = imagePanel.getGraphics();
				g.drawImage(img, 0, 0, null);
            }
        });
		
		slider.addChangeListener(new ChangeListener(){

            public void stateChanged(ChangeEvent arg0) {
                thresh = slider.getValue();
            }
        });
		
		btnMedianGray.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent arg0) {

				BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);

				for (int i = 1; i < height-1; i++) {
					for (int j = 1; j < width-1; j++) {

						int[] array = new int[9];
						int rgb = img.getRGB(j, i);
						int red = Util.getR(rgb);
						array[0] = Util.getR(img.getRGB(j - 1, i - 1));
						array[1] = Util.getR(img.getRGB(j - 1, i));
						array[2] = Util.getR(img.getRGB(j - 1, i + 1));
						array[3] = Util.getR(img.getRGB(j, i - 1));
						array[4] = Util.getR(img.getRGB(j, i));
						array[5] = Util.getR(img.getRGB(j, i + 1));
						array[6] = Util.getR(img.getRGB(j + 1, i - 1));
						array[7] = Util.getR(img.getRGB(j + 1, i));
						array[8] = Util.getR(img.getRGB(j + 1, i + 1));

						Arrays.sort(array);

						if (red - array[4] > thresh) {
							int color = Util.makeColor(array[4],array[4],array[4]);
							newImg.setRGB(j, i, color);

						}else{
							int color = Util.makeColor(red,red,red);
							newImg.setRGB(j, i, color);
						}
					}
				}
				
				Graphics g = imagePanel2.getGraphics();
				g.drawImage(newImg, 0, 0, null);	
			}
		});
		
		
		
	
	}

	public static void main(String[] args) {
		NoiseFrame frame = new NoiseFrame();
		frame.setSize(1500, 1500);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

}



