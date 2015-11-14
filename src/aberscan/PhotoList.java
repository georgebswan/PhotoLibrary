package aberscan;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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


