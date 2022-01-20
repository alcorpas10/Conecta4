package view;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import controller.MainController;

public class MenuBar extends JMenuBar implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	private JMenu menu1;
	private JMenu exit;
	private JMenuItem terminateM;
	private JMenuItem save;
	private JMenuItem load;
	private JMenuItem loadFile;
	private final static Font WindowsDefFont = new Font("Segoe UI", Font.PLAIN, 14);
	
	private MainController _ctrl;
	
	public MenuBar(MainController ctrl) {
		_ctrl = ctrl;
		this.setVisible(true);
		initMenu();
	}

	private void initMenu() {
		menu1 = new JMenu("File");
		save = new JMenuItem("Save");
		load = new JMenuItem("Load last save");
		loadFile = new JMenuItem("Load from file");
		
		ImageIcon ico = new ImageIcon(getClass().getResource("/resources/save.png"));
		Image resize = ico.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ico = new ImageIcon(resize);
		
		save.setIcon(ico);
		save.setFont(WindowsDefFont);
		save.addActionListener(this);
		
		ico = new ImageIcon(getClass().getResource("/resources/load.png"));
		resize = ico.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ico = new ImageIcon(resize);
		
		load.setIcon(ico);
		load.setFont(WindowsDefFont);
		load.addActionListener(this);
		load.setEnabled(false);
		
		ico = new ImageIcon(getClass().getResource("/resources/loadfile.png"));
		resize = ico.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ico = new ImageIcon(resize);
		
		loadFile.setIcon(ico);
		loadFile.setFont(WindowsDefFont);
		loadFile.addActionListener(this);
		
		menu1.add(save);
		menu1.add(load);
		menu1.add(loadFile);
		
		exit = new JMenu("Match Options");
		terminateM = new JMenuItem("Terminate Match");
		ico = new ImageIcon(getClass().getResource("/resources/home.png"));
		resize = ico.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		ico = new ImageIcon(resize);
		terminateM.setIcon(ico);
		terminateM.setFont(WindowsDefFont);
		terminateM.addActionListener(this);
		
		exit.add(terminateM);
		
		this.add(menu1);
		this.add(exit);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == save) {
			File f = null;
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			
			int sel = fc.showSaveDialog(this);
			
			if(sel == JFileChooser.APPROVE_OPTION) {
				f = fc.getSelectedFile();
				if(f == null)
					_ctrl.save();
				else {
					if(!f.toString().contains(".json"))
						f = new File(f.toString() + ".json");
					_ctrl.saveToFile(f);
				}
				load.setEnabled(true);
			}
			
		}
		else if(e.getSource() == load)
			_ctrl.loadLastSave();
		else if (e.getSource() == terminateM) {
			_ctrl.reset();
			MainView.changeToPanel(1);
		}
		else {
			File f = null;
			JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
			
			int sel = fc.showOpenDialog(this);
			
			if(sel == JFileChooser.APPROVE_OPTION) {
				f = fc.getSelectedFile();
				_ctrl.loadFromFile(f);
			}
		}
	}
}
