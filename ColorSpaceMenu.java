package final_hw;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class ColorSpaceMenu {
	private JFrame mainFrame;

	private JPanel controlPanel;

	public ColorSpaceMenu() {
		prepareGUI();
	}

	public static void main(String[] args) {
		ColorSpaceMenu swingMenuDemo = new ColorSpaceMenu();
		swingMenuDemo.showMenuDemo();
	}

	private void prepareGUI() {
		mainFrame = new JFrame("Image Tools final.ver");
		mainFrame.setSize(400, 400);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		controlPanel = new JPanel();
		controlPanel.setLayout(new FlowLayout());
	}

	private void showMenuDemo() {
		final JMenuBar menuBar = new JMenuBar();
		JMenu colorMenu = new JMenu("Color");
		JMenu adjustMenu = new JMenu("Adjust");
		JMenu bwMenu = new JMenu("B&W");
		
		JMenuItem hsiMenuItem = new JMenuItem("HSI");
		hsiMenuItem.setMnemonic(KeyEvent.VK_I);
		hsiMenuItem.setActionCommand("HSI");
		
		JMenuItem affineMenuItem = new JMenuItem("Affine");
		affineMenuItem.setMnemonic(KeyEvent.VK_A);
		affineMenuItem.setActionCommand("Affine");
		
		JMenuItem basicMenuItem = new JMenuItem("Basic");
		basicMenuItem.setMnemonic(KeyEvent.VK_C);
		basicMenuItem.setActionCommand("Basic");
		
		JMenuItem edgeMenuItem = new JMenuItem("Edge");
		edgeMenuItem.setMnemonic(KeyEvent.VK_E);
		edgeMenuItem.setActionCommand("Edge");
		
		JMenuItem filterMenuItem = new JMenuItem("Filter");
		filterMenuItem.setMnemonic(KeyEvent.VK_F);
		filterMenuItem.setActionCommand("Filter");
		
		JMenuItem noiseMenuItem = new JMenuItem("Noise");
		noiseMenuItem.setMnemonic(KeyEvent.VK_N);
		noiseMenuItem.setActionCommand("Noise");
		
		JMenuItem rotationMenuItem = new JMenuItem("Rotation");
		rotationMenuItem.setMnemonic(KeyEvent.VK_R);
		rotationMenuItem.setActionCommand("Rotation");
		
		JMenuItem binaryMenuItem = new JMenuItem("Binary");
		binaryMenuItem.setMnemonic(KeyEvent.VK_B);
		binaryMenuItem.setActionCommand("Binary");
		
		JMenuItem stretchMenuItem = new JMenuItem("Stretch");
		stretchMenuItem.setMnemonic(KeyEvent.VK_S);
		stretchMenuItem.setActionCommand("Stretch");
			
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setMnemonic(KeyEvent.VK_X);
		exitMenuItem.setActionCommand("EXIT");
		
		
		MenuItemListener menuItemListener = new MenuItemListener();
		hsiMenuItem.addActionListener(menuItemListener);
		affineMenuItem.addActionListener(menuItemListener);
		basicMenuItem.addActionListener(menuItemListener);
		edgeMenuItem.addActionListener(menuItemListener);
		filterMenuItem.addActionListener(menuItemListener);
		noiseMenuItem.addActionListener(menuItemListener);
		rotationMenuItem.addActionListener(menuItemListener);
		stretchMenuItem.addActionListener(menuItemListener);
		binaryMenuItem.addActionListener(menuItemListener);
		
		exitMenuItem.addActionListener(menuItemListener);
		
		colorMenu.add(hsiMenuItem);
		colorMenu.addSeparator();
		colorMenu.add(exitMenuItem);
		
		adjustMenu.add(basicMenuItem);
		adjustMenu.add(filterMenuItem);
		adjustMenu.add(rotationMenuItem);
		adjustMenu.add(affineMenuItem);
		
		
		
		bwMenu.add(binaryMenuItem);
		bwMenu.add(edgeMenuItem);
		bwMenu.add(noiseMenuItem);
		bwMenu.add(stretchMenuItem);
		
		
		
		menuBar.add(colorMenu);
		menuBar.add(adjustMenu);
		menuBar.add(bwMenu);
		
		mainFrame.setJMenuBar(menuBar);
		mainFrame.setVisible(true);
	}

	class MenuItemListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("HSI"))
				new HSIFrame().setVisible(true);
			
			if (e.getActionCommand().equals("Affine"))
				new AffineFrame().setVisible(true);
			
			if (e.getActionCommand().equals("Basic"))
				new BasicFrame().setVisible(true);
			
			if (e.getActionCommand().equals("Binary"))
				new BinaryFrame().setVisible(true);
			
			if (e.getActionCommand().equals("Edge"))
				new EdgeFrame().setVisible(true);
			
			if (e.getActionCommand().equals("Noise"))
				new NoiseFrame().setVisible(true);
			
			if (e.getActionCommand().equals("Rotation"))
				new RotationFrame().setVisible(true);
			
			if (e.getActionCommand().equals("Filter"))
				new FilterFrame().setVisible(true);
			
			if (e.getActionCommand().equals("Stretch"))
				new StretchFrame().setVisible(true);
			
			else if (e.getActionCommand().equals("EXIT"))
				System.exit(0);
		}
	}
}