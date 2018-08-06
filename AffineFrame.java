package final_hw;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class AffineFrame extends JFrame {
	JPanel cotrolPanelMain = new JPanel();
	JPanel cotrolPanelShow = new JPanel();;
	JPanel cotrolPanelBackColor = new JPanel();;
	JPanel cotrolPanelTranslate = new JPanel();;
	JPanel cotrolPanelScale = new JPanel();
	JPanel cotrolPanelShear = new JPanel();;
	ImagePanel imagePanel;
	JButton btnShow;
	JButton btnTranslate;
	JButton btnScale;
	JButton btnShear;
	JTextField tfRed = new JTextField(5);
	JTextField tfGreen = new JTextField(5);
	JTextField tfBlue = new JTextField(5);
	JTextField tfDeltaX = new JTextField(5);
	JTextField tfDeltaY = new JTextField(5);
	JTextField tfAmpX = new JTextField(5);
	JTextField tfAmpY = new JTextField(5);
	JTextField  tfShearX= new JTextField(5);
	JTextField  tfShearY= new JTextField(5);
	JLabel lbRed = new JLabel("BG (R)");
	JLabel lbGreen = new JLabel("BG (G)");
	JLabel lbBlue = new JLabel("BG (B)");
	JLabel lbDeltaX = new JLabel("x-delta");
	JLabel lbDeltaY = new JLabel("y-delta");
	JLabel lbAmpX = new JLabel("x-ratio");
	JLabel lbAmpY = new JLabel("y-artio");
	JLabel lbShearY = new JLabel("x-ratio");
	JLabel lbShearX = new JLabel("y-ratio ");
	BufferedImage img = null;
	final int[][][] data;
	int height;
	int width;

	AffineFrame(){
		setBounds(0, 0, 1500, 1500);
		getContentPane().setLayout(null);
		tfRed.setText("0");
		tfGreen.setText("0");
		tfBlue.setText("0");
	    tfDeltaX.setText("0");
	    tfDeltaY.setText("0");
	    tfAmpX.setText("1.0");
	    tfAmpY.setText("1.0");
	    tfShearX.setText("0.0");
	    tfShearY.setText("0.0");

	    setTitle("Image Processing Homework 2: Affine Transforms");

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
		
		btnShow = new JButton("Show");
		btnTranslate = new JButton("Translation");
		btnScale = new JButton("Scaling");
		btnShear = new JButton("Shearing");
	
		cotrolPanelMain = new JPanel();
		cotrolPanelMain.setLayout(new GridLayout(1,7));
		
		cotrolPanelShow.add(btnShow);
		cotrolPanelMain.add(cotrolPanelShow);
		cotrolPanelBackColor.add(lbRed);
		cotrolPanelBackColor.add(tfRed);
		cotrolPanelBackColor.add(lbGreen);
		cotrolPanelBackColor.add(tfGreen);
		cotrolPanelBackColor.add(lbBlue);
		cotrolPanelBackColor.add(tfBlue);
		cotrolPanelMain.add(cotrolPanelBackColor);
		
		cotrolPanelTranslate.add(lbDeltaX);
		cotrolPanelTranslate.add(tfDeltaX);
		cotrolPanelTranslate.add(lbDeltaY);
		cotrolPanelTranslate.add(tfDeltaY);
		cotrolPanelTranslate.add(btnTranslate);
		cotrolPanelMain.add(cotrolPanelTranslate);
		
		cotrolPanelScale.add(lbAmpX);
		cotrolPanelScale.add(tfAmpX);
		cotrolPanelScale.add(lbAmpY);
		cotrolPanelScale.add(tfAmpY);
		cotrolPanelScale.add(btnScale);
		cotrolPanelMain.add(cotrolPanelScale);
		
		cotrolPanelShear.add(lbShearY);
		cotrolPanelShear.add(tfShearY);
		cotrolPanelShear.add(lbShearX);
		cotrolPanelShear.add(tfShearX);
		cotrolPanelShear.add(btnShear);
		cotrolPanelMain.add(cotrolPanelShear);
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

		
		
		btnTranslate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int dx = Integer.parseInt(tfDeltaX.getText());
				int dy = Integer.parseInt(tfDeltaY.getText());

				int[][][] newData = new int[height+dy][width+dx][3];

				for (int i = 0; i < data.length; i++) {
					for (int j = 0; j < data[i].length; j++) {
						newData[i][j][0] = data[i][j][0];
						newData[i][j][1] = data[i][j][1];
						newData[i][j][2] = data[i][j][2];
					}
				}
				
				fillBgColor(newData);
				Graphics g = imagePanel.getGraphics();
				g.drawImage(img, dx, dy , null);
				
			}
		});

		btnScale.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Double ax = Double.parseDouble(tfAmpX.getText());
				Double ay = Double.parseDouble(tfAmpY.getText());
				
				int scaledHeight = (int) (height * ax);
				int scaledWidth = (int) (width * ay);
				
				Image newImage = img.getScaledInstance(scaledWidth, scaledHeight, img.SCALE_DEFAULT);

				Graphics g = imagePanel.getGraphics();
				imagePanel.paintComponent2(g);
				g.drawImage(newImage, 0 , 0 , null);
			}
		});
		

		btnShear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {

				Double sx = Double.parseDouble(tfShearX.getText());
				Double sy = Double.parseDouble(tfShearY.getText());

				int newHeight = (int) (height + height*sy);
				int newWidth= (int) (width + width*sx);
				
				AffineTransform tx = new AffineTransform();
				tx.shear(sx,sy);
				
				AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
				Image newImage = op.filter(img, null);

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
				g.drawImage(newImage, 0, 0, null);
				
			}
		});

		
		cotrolPanelMain.setBounds(0, 0,1200,150);
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
