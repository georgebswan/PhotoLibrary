package aberscan;

import java.awt.Image;
import java.awt.List;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class Photo {
	File srcFile;
	Image image;
	Rectangle imageRect;
	Rectangle adjustedImageRect;
	boolean copyFlag;
	boolean editFlag;
	boolean exportedFlag;
	TagList tags;

    
    public Photo(File sFile) {
    	srcFile = sFile;
       	imageRect = new Rectangle(0,0,0,0); 	//don't known size until loaded
    	adjustedImageRect = new Rectangle(0,0,0,0);
    	this.copyFlag = false;
    	this.editFlag = false;
    	this.exportedFlag = false;
    	this.image = null;
    	tags = new TagList();
    }
    
    public String getName() { return srcFile.getAbsolutePath();}
    public String getShortName() {return srcFile.getName(); }
    public File getSrcFile() { return srcFile; }
    public Image getImage() { return image; }
    public void setImage(Image img) { image = img; }
    public Rectangle getImageRect() { return imageRect; }
    public Rectangle getAdjustedImageRect() { return adjustedImageRect; }
    public boolean getCopyFlag() { return copyFlag; }
    public boolean getEditFlag() { return editFlag; }
    public boolean getExportedFlag() { return exportedFlag; }
    public TagList getTags() { return tags; }
    public void setTags(TagList tags) { 
    	for(int i = 0 ; i < tags.getNumTags(); i++ ) {
    		this.tags.setText(i,  tags.getText(i));
    	}
    }
    
    public void setImageRect(int width, int height) { imageRect = new Rectangle(0,0,width, height); }
    public void setAdjustedImageRect(double x, double y, int width, int height) { adjustedImageRect = new Rectangle((int) x, (int) y, width, height);}
    public Photo reset() { return( new Photo(srcFile)); }
    public void setCopyFlag(boolean flag) { copyFlag = flag; }
    public void setEditFlag(boolean flag) { editFlag = flag; }
    public void setExportedFlag(boolean flag) { exportedFlag = flag; }
    public void setTagText(int i, String tag) { tags.setText(i, tag); }
    public String getTagText(int i) { return (tags.getText(i) ); }
    
    public void copyPhotoToExportDir(Directories photoDir) throws IOException {
    	//System.out.println("Copying file : '" + srcFile.getAbsolutePath() + "' to Folder '" + destDir.getAbsolutePath() + "'");
    	//copy if file if it has not already been written to the export dir
    	if(exportedFlag == false) {
    		//if the photo has been marked for edit, then copy it to the edit dir. If not, then copy to the export dir.
    		//I could just do two copies, but this gets flow with the large AAPS tif files
    		if(editFlag == true) {
    			FileUtils.copyFileToDirectory(srcFile, photoDir.getEditDirectory());
    		}
    		else {
    			FileUtils.copyFileToDirectory(srcFile, photoDir.getExportDirectory());
    		}
    		exportedFlag = true;
    	}
    	
    }
    
    public void rotatePhoto(boolean rotateLeft) throws IOException {
		String exePath = "D:\\Program Files (x86)\\ImageMagick-6.8.7-Q16\\convert.exe";
		String direction;
		if(rotateLeft == true)
			direction = "-90";
		else
			direction = "90";
    		
		// construct the cmdLine
		String [] cmdLine = new String [] { exePath, "-rotate", direction, srcFile.getAbsolutePath(), srcFile.getAbsolutePath() };
		
		//Print out the cmdLine doing the rotate
		//print();
		//System.out.println("cmdLine = ");
		//for(int i = 0 ; i < cmdLine.length ; i++ ) {
		//	System.out.println(cmdLine[i]);
		//}

		try {
			Process p = Runtime.getRuntime().exec(cmdLine);
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} //wait for the exec to finish
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void flipPhoto(boolean flipVert) throws IOException {
		String exePath = "D:\\Program Files (x86)\\ImageMagick-6.8.7-Q16\\convert.exe";
		String direction;
		if(flipVert == true)
			direction = "-flip";
		else
			direction = "-flop";
    		
		// construct the cmdLine
		String [] cmdLine = new String [] { exePath, direction, srcFile.getAbsolutePath(), srcFile.getAbsolutePath() };
		
		//Print out the cmdLine doing the rotate
		//print();
		//System.out.println("cmdLine = ");
		//for(int i = 0 ; i < cmdLine.length ; i++ ) {
		//	System.out.println(cmdLine[i]);
		//}

		try {
			Process p = Runtime.getRuntime().exec(cmdLine);
			try {
				p.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} //wait for the exec to finish
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void tagPhoto() throws IOException {
		String exePath = "D:\\Aberscan\\PerlApplications\\exiv2\\exiv2.exe";
		String firstTag = "-M\"set";
		String nextTag = "-M\"add";
		String delTag = "-di";
		String tagName = "Iptc.Application2.Keywords";
		boolean firstFlag = true;
		String [] cmdLine;
		
		//if exiv2 can't be found, then don't even bother with this method
		File tmpFile = new File(exePath);
		if(tmpFile.exists()) {
			
			//first of all, delete any existing tags in the photo
			cmdLine = new String [] { exePath, delTag, srcFile.getAbsolutePath() };
			try {
				Process p = Runtime.getRuntime().exec(cmdLine);
				try {
					p.waitFor();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} //wait for the exec to finish
				//srcFile = new File(srcFile.getAbsolutePath());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//write out each non-empty tag
			for(int i = 0 ; i < tags.getNumTags(); i++) {
				if(!tags.getText(i).equals("")) {
					if(firstFlag == true) {
						// construct the cmdLine
						cmdLine = createTagCmdLine(exePath, firstTag, tagName, tags.getText(i));
						//cmdLine = new String [] { exePath, firstTag, tagName, tags.getText(i) + "\"", srcFile.getAbsolutePath() };
						firstFlag = false;
					}
					else {
						// construct the cmdLine
						cmdLine = createTagCmdLine(exePath, nextTag, tagName, tags.getText(i));
						//cmdLine = new String [] { exePath, nextTag, tagName, tags.getText(i) + "\"", srcFile.getAbsolutePath() };
					}
					
					//Print out the cmdLine doing the tagging
					//print();
					//System.out.println("cmdLine = ");
					//for(int j = 0 ; j < cmdLine.length ; j++ ) {
					//	System.out.println(cmdLine[j]);
					//}
					
					try {
						Process p = Runtime.getRuntime().exec(cmdLine);
						try {
							p.waitFor();
						} catch (InterruptedException e) {
							e.printStackTrace();
						} //wait for the exec to finish
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
    }
    private String[] createTagCmdLine(String exePath, String tagType, String tagName, String tagText) {
    	ArrayList <String> words = new ArrayList<String>();
    	String[] wordArray;
    	
    	//build up the words. I have to do it this way because the tag text could contain separate words and each must be stored separately
    	words.add(exePath);
    	words.add(tagType);
    	words.add(tagName);
    	
    	//append "\"" on to the end of the tagText
    	tagText = tagText + "\"";
    	
    	String[] textArray = tagText.split("\\s");
    	for(int i = 0 ; i < textArray.length; i++ ) {
    		words.add(textArray[i]);
    	}
    	
    	//add filename
    	words.add(srcFile.getAbsolutePath());
    	wordArray = words.toArray(new String[words.size()]);
    	return (wordArray);
    }
    
    public void printTagsToFile(PrintWriter csvFile) {
    	//print out the tags in csv format as one line
    	csvFile.print(srcFile.getName() + ",");
    	for(int i = 0 ; i < tags.getNumTags()-1; i++ ) {
    		csvFile.print(tags.getText(i) + ",");
    	}
   		csvFile.println(tags.getText(tags.getNumTags() - 1));
    	
    }
    public void print(){
    	System.out.println("Photo: \n\tfileName = " + getName() + 
    			"\n\timageRect = " + imageRect.toString() + 
    			"\n\tadjustedImageRect = " + adjustedImageRect.toString() +
    			"\n\teditFlag = " + editFlag + 
    			"\n\tcopyFlag = " + copyFlag + 
				"\n\timage = " + image);
    	tags.print();
    }
}
