package aberscan;

import java.awt.Rectangle;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.commons.io.FileUtils;

public class PhotoList {
	ArrayList <Photo> photos;
	int userSelectedFirstIndex = 0;
	int curIndex = 0;
	
    public PhotoList() {
    	curIndex = 0;
    	userSelectedFirstIndex = 0;
    	emptyList();
    }
    
    public int getCurIndex() {return curIndex; }
    public void setCurIndex(int i) {curIndex = i; }
    public Photo getPhoto() { return photos.get(curIndex); }
    public Photo getPhoto(int i) { return photos.get(i); }
    public int getNumPhotos() {return photos.size(); }
    public ArrayList <Photo> getPhotos() { return photos; }
    public Photo getFirstPhoto() { curIndex = 0; return photos.get(curIndex); }
    public Photo getUserSelectedFirstPhoto() { curIndex = userSelectedFirstIndex; return photos.get(curIndex); }
    public Photo getNextPhoto() { return photos.get(++curIndex); }
    public Photo getPrevPhoto() { return photos.get(--curIndex); }
    public boolean atBeginning() {if(curIndex == 0) return (true); else return(false); }
    public boolean atEnd() {if(curIndex == (photos.size() - 1)) return (true); else return(false); }
    public void emptyList() {photos = new ArrayList<Photo>();}
    
    public void resetCurPhoto() {
    	//add a new photo into the photolist after the current one, then go back and remove the old one
    	photos.add(curIndex, getPhoto().reset()); 
    	photos.remove(curIndex);
	}
    
    public void setStartPhoto(Photo photo) {
    	//Goto find where that Photo is in the list of Photos
    	//String name = photo.getName();
    	//System.out.println("PhotoList (setStartPhoto) name = " + name);
    	//for(int i = 0; i < photos.size(); i++){
    	//	//System.out.println("PhotoList (setStartPhoto) photos" + "[" + i + "] = " + photos.get(i).getName());
    	//	if(name.equals(photos.get(i).getName())) {
    	//		userSelectedFirstIndex = i;
    	//		return;
    	//	}
    	//}
    	int i = findPhoto(photo);
    	if(i != -1) {
    		userSelectedFirstIndex = i;
    		return;
    	}
    	else {
	    	System.out.println("PhotoList ERROR: Can't find match for Photo name");
	    	System.exit(0);
    	}
    }
    
    public int findPhoto(Photo photo) {
    	String name = photo.getName();
    	for(int i = 0; i < photos.size(); i++){
    		if(name.equals(photos.get(i).getName())) {
    			return(i);
    		}
    	}
    	return(-1);
    }
    
    public void loadPhotos(File fileDir){
    	//File fileDir = photoDir.getImageDirectory();
    	File [] fileList;
    	Photo photo;
    	
    	//System.out.println("imageDirectory = " + fileDir.getAbsolutePath());
 
    	//get all the files from a directory
       fileList = fileDir.listFiles(new ImageFileFilter());
       
       for (File file : fileList) {
    	   // store the file, image, and image size in the photo, then add to Photolist
    	   photo = new Photo(file);

    	   
    	   photos.add(photo);
       }

    }
    
    public void cropPhotos() {
    	// go through each photo, work out the crop rectangle based on orig size, then do the crop
    	for (Photo photo : photos){
    		//Is there a crop Rectangle defined for this photo?
    		if(photo.getCropAdjustedImageRect().getWidth() != 0 && photo.getCropAdjustedImageRect().getHeight() != 0 ) {
    			
    			// calculate the cropRectangle back in the original image coordinate system
    			// note that to do the calculation, you have to assume the adjustedImageRect (x,y) is (0,0). 
    			// so the crop x.y calculation is relative to the adjustedImage, not to the actual orgin
    			
    			double widthRatio = photo.getImageRect().getWidth()/photo.getAdjustedImageRect().getWidth();
    			double heightRatio = photo.getImageRect().getHeight()/photo.getAdjustedImageRect().getHeight();
    			
    			Rectangle cropImageRect = new Rectangle((int) ((photo.getCropAdjustedImageRect().getX() - photo.getAdjustedImageRect().getX())  * widthRatio),
    									(int) ((photo.getCropAdjustedImageRect().getY() - photo.getAdjustedImageRect().getY()) * heightRatio),
    									(int) (photo.getCropAdjustedImageRect().getWidth() * widthRatio),
    									(int) (photo.getCropAdjustedImageRect().getHeight() * heightRatio));
    			photo.setCropImageRect(cropImageRect);
    			
    			//System.out.println("Width Ratio = " + widthRatio + "\n\tVertical Ratio = "+ heightRatio); 
    			//System.out.println("Crop Rect = " + cropImageRect.toString());
    			
    			//Now do the crop
    			try {
    				photo.cropPhoto();
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
    		}			
        }
    }
    
    public void copyPhotos(File copyDir) {
    	
		// go through each photo. If flag set, then do the copy
		for (Photo photo : photos){
			//Is there a copy flag set?
			if(photo.getCopyFlag() == true) {
				try {
					//source file comes from a different place based on whether photos were cropped or not
					photo.copyPhoto(copyDir);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}			
		}
    }
    
    
	public void exportPhotoTags(PrintWriter csvFile) {
		//this method goes through all the photos and writes out the tag info into a csv file and writes the tags to the photo
        for (Photo photo : photos){
        	photo.printTagsToFile( csvFile);
        	try {
				photo.tagPhoto();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
	}
    
    public class ImageFileFilter implements FileFilter
    {
    	private final String[] okFileExtensions = 
        new String[] {"jpg", "JPG", "tif", "TIF", "tiff", "TIFF"};

    	public boolean accept(File file)
    	{
    		for (String extension : okFileExtensions)
    		{
    			if (file.getName().toLowerCase().endsWith(extension))
    			{
    				return true;
    			}
    		}
    		return false;
    	}
    }
    
    public void setCopyFlags(boolean flag){
        for (Photo photo : photos){
            photo.setCopyFlag(flag); 
        }
    }

    public void printPhotos(){
    	System.out.println("Number of photos is " + photos.size());
        for (Photo photo : photos){
            photo.print(); 
        }
    }
}


