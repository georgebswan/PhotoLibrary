package aberscan;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;


public class FilePrompterGUI extends JFrame {
	static final long serialVersionUID = 4;
	boolean fileSelected = false;
	File imageDir;
	File startFile;
	
	public boolean isFileSelected() { return (fileSelected); }
	public File getImageDir() {return imageDir; }
	public File getStartFile() {return startFile; }
	
	public FilePrompterGUI(boolean dirsOnly, File rootDir, String prompt, String[] fileExtensionFilters) {

		final JFileChooser chooser = new JFileChooser();
		
		//add a filter for just photos
		FileNameExtensionFilter filter = new FileNameExtensionFilter(prompt, fileExtensionFilters);		
		chooser.addChoosableFileFilter(filter);
		chooser.setFileFilter(filter);
		
		//if only interested in directories, then set the flag and don't set up the preview pane
		if(dirsOnly == true) {
	        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		}
		else {
	        //Add the preview pane.
	        chooser.setAccessory(new ImagePreview(chooser));
		}
		
		chooser.setMultiSelectionEnabled(false);
		chooser.setCurrentDirectory(rootDir);
		chooser.setDialogTitle(prompt);
		int retVal = chooser.showOpenDialog(this);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			fileSelected = true;
            imageDir = chooser.getCurrentDirectory();
            startFile = chooser.getSelectedFile();
        }
		else {
			fileSelected = false;
        	JOptionPane.showMessageDialog(null, "Warning: No file was selected - please try again");
        }
	}
	
	public void print() {
		System.out.println("FilePrompterGUI:");
			System.out.println("\timageDir = '" + imageDir.getAbsolutePath() + "'");
			System.out.println("\tstartFile = '" + startFile.getAbsolutePath() + "'");
	}
}

