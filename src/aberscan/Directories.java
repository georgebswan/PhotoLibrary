package aberscan;
import java.io.File;


public class Directories {
	File imageDirectory;
	File exportDirectory;
	File editDirectory;
	File rootDirectory;
	
	public Directories() { 
		rootDirectory = new File("C:\\AberscanInProgress");
		exportDirectory = rootDirectory;
		editDirectory = rootDirectory;
		imageDirectory = rootDirectory; 
	}
	
	public File getImageDirectory() { return imageDirectory; }
	public File getExportDirectory() { return exportDirectory; }
	public File getRootDirectory() { return rootDirectory; }
	public File getEditDirectory() { return editDirectory; }
	
	public void setExportDirectory(File dir) { exportDirectory = dir; }
	public void setEditDirectory(File dir) { editDirectory = dir; }
	public void setImageDirectory(File dir) { imageDirectory = dir; }
	
	public void print() {
		System.out.println("PhotoDirectory:");
		System.out.println("\timageDirectory = '" + imageDirectory.getAbsolutePath() + "'");
		System.out.println("\teditDirectory = '" + editDirectory.getAbsolutePath() + "'");
		System.out.println("\texportDirectory = '" + exportDirectory.getAbsolutePath() + "'");
		System.out.println("\trootDirectory = '" + rootDirectory.getAbsolutePath() + "'");
	}
}
