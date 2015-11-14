package aberscan;

import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.commons.io.FilenameUtils;

public class PreloadImages extends Thread {
	String str;
	String tmpJPGFileName;
	PhotoList photos;
	int imageCount;
	
	public PreloadImages(String str, String tmpJPGFileName, PhotoList photos) {
		super(str);
		this.str = str;
		this.tmpJPGFileName = tmpJPGFileName;
		this.photos = photos;
		imageCount = 0;
		
	}
	
	public void run() {
		//cmpPane.frame.lPane.print("\nLoading images");
    	//iPane.photos.loadImages(tmpJPGFileName);

		imageCount = 0;	
		// go through each photo.
		for (Photo photo : photos.getPhotos()){
			loadImage(photo, tmpJPGFileName);	
			imageCount++;
		}
    	//cmpPane.frame.lPane.println(" done");
		
    	
    	//JOptionPane.showMessageDialog(null, str + " Complete");
     }
	
	 protected void loadImage(Photo photo, String str) {
	    	String ext;
	    	File tmpJpgFile = null;
	    	File imageFile = photo.srcFile;
	    	//Check to see if the image is a tif file. If yes, convert it to a temporary jpg and display that, since createImage doesn't seem to work with tifs
	    	ext = FilenameUtils.getExtension(imageFile.getName());
	    	if(ext.equals("tif")) {
	        	tmpJpgFile = new File("C:\\Temp\\" + str + imageCount + ".jpg");
	           	String[] args = {"D:\\Program Files (x86)\\ImageMagick-6.8.7-Q16\\convert.exe", imageFile.getAbsolutePath(), tmpJpgFile.getAbsolutePath() };
	           	try {
					Process p = Runtime.getRuntime().exec(args);
					try {
						p.waitFor();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} //wait for the exec to finish
					//System.out.println("AAA - '"  + args[1]);
		    		//fileUsed = tmpJpgFile;
			    	photo.setImage(Toolkit.getDefaultToolkit().createImage(tmpJpgFile.getAbsolutePath()));
		    		//image = Toolkit.getDefaultToolkit().createImage(tmpJpgFile.getAbsolutePath());
				} catch (IOException e) {
					e.printStackTrace();
					//fileUsed = null;
				}
	    	}
	    	else {
		    	photo.setImage(Toolkit.getDefaultToolkit().createImage(imageFile.getAbsolutePath()));
	    		//fileUsed = imageFile;
	    	}
	    	
	    	//photo.setImage(Toolkit.getDefaultToolkit().createImage(fileUsed.getAbsolutePath()));
	    	
	    	//System.out.println("Photo::(loadImage): converted tif '" + imageFile + "' to jpg = '" + tmpJpgFile + "'");
			//photo.print();
	    }
}

