import javax.swing.*;
import javax.swing.text.html.Option;
import java.awt.*;

/**
 * taken from https://github.com/javiergs/NielsenSkeleton
 *
 * Main class is a JFrame that contains a JMenuBar, a ViewPanel and a StatusBar.
 * It also contains an Engine that is a Runnable.
 *
 * @author javiergs
 * @version 1.0
 */
public class Main extends JFrame {
	
	private Engine engine;
	
	private JMenuBar createMenuBar() {
		// controller
		Controller controller = new Controller(this);
		// construct
		JMenuBar menuBar = new JMenuBar();
		JMenu connectMenu = new JMenu("Connection");
		JMenu helpMenu = new JMenu("Help");
		JMenuItem aboutMenuItem = new JMenuItem("About");
		JMenuItem startMenuItem = new JMenuItem("Connect");
		JMenuItem stopMenuItem = new JMenuItem("Disconnect");
		connectMenu.add(startMenuItem);
		connectMenu.add(stopMenuItem);
		helpMenu.add(aboutMenuItem);
		startMenuItem.addActionListener(controller);
		stopMenuItem.addActionListener(controller);
		aboutMenuItem.addActionListener(controller);
		menuBar.add(connectMenu);
		menuBar.add(helpMenu);
		return menuBar;
	}
	
	public Main() {
		// model
		engine = new Engine();
		Thread modelThread = new Thread(engine);
		modelThread.start();
		// construct
		setJMenuBar(createMenuBar());
		ViewPanel centralPanel = new ViewPanel();
		StatusBar viewStatusBar = new StatusBar();
		setLayout(new BorderLayout());
		add(centralPanel, BorderLayout.CENTER);
		add(viewStatusBar, BorderLayout.SOUTH);
		Blackboard.getInstance().addPropertyChangeListener(centralPanel);
		Blackboard.getInstance().addPropertyChangeListener(viewStatusBar);
	}
	
	public void about() {
		// view
		JOptionPane.showMessageDialog(this, "This implements a Graphical User Interface (GUI) to enhance\n" +
				"usability and functionality and follow Nielsen’s Usability Heuristics.");
	}
	
	public void pauseThread(boolean b) {
		// controller
		if (b){
			// confirmation for disconnect
			JOptionPane pane = new JOptionPane();
			Integer option = pane.showConfirmDialog(this, "Are you sure you want to disconnect?", "Confirmation", JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION){
				engine.pause(b);
			}
		}
		else {
			engine.pause(b);
		}
	}
	
	public static void main(String[] args) {
		Main main = new Main();
		main.setSize(800, 600);
		main.setLocationRelativeTo(null);
		main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		main.setVisible(true);
		main.setTitle("Assignment 01. HCISE");
	}
	
}

