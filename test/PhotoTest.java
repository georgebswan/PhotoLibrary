

import static org.junit.Assert.*;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import aberscan.Directories;
import aberscan.Photo;
import aberscan.TagList;

public class PhotoTest {
	String rootEclipseTestDir = "D:\\MyJavaApplications\\eclipseworkspace\\PhotoLibrary\\testdata";
	String jpgFileName = "371-2.jpg";
	String testDirName = "testdata";
	File testDir = new File(testDirName);
	File editDir = new File(testDirName + "\\tmpEditDir");
	File exportDir = new File(testDirName + "\\tmpExportDir");
	File jpgFile = new File(testDirName + "\\" + jpgFileName);
	Photo photo = new Photo(jpgFile);

	@Test
	public void setGetFile() {
		String fullJpgFileName = rootEclipseTestDir + "\\" + jpgFileName;
		
		//file
		assertEquals(jpgFile, photo.getSrcFile());
		assertEquals(fullJpgFileName, photo.getName());

	}
	
	@Test
	public void setGetImage() {
		Image jpgImage;
		
		//image
		jpgImage = Toolkit.getDefaultToolkit().createImage(jpgFile.getAbsolutePath());
		photo.setImage(jpgImage);
		assertEquals(jpgImage, photo.getImage());
	}
	
	@Test
	public void setGetRects() {

		//rect
		photo.setImageRect(100,300);
		assertEquals("ImageRect", new Rectangle(0,0,100,300), photo.getImageRect());
		
		//adjustedRect
		photo.setAdjustedImageRect(0, 0, 90,290);
		assertEquals("ImageAdjustedRect", new Rectangle(0,0,90, 290), photo.getAdjustedImageRect());
	}
	
	@Test
	public void setGetFlags() {

		//copy
		photo.setCopyFlag(true);
		assertTrue( photo.getCopyFlag());
		photo.setCopyFlag(false);
		assertFalse( photo.getCopyFlag());
		
		//edit
		photo.setEditFlag(true);
		assertTrue( photo.getEditFlag());
		photo.setEditFlag(false);
		assertFalse( photo.getEditFlag());
		
		//exported
		photo.setExportedFlag(true);
		assertTrue( photo.getExportedFlag());
		photo.setExportedFlag(false);
		assertFalse( photo.getExportedFlag());
	}
	
	@Test
	public void copyToPhotoDir() {
		Directories photoDir = new Directories();
		photoDir.setEditDirectory(editDir);
		photoDir.setExportDirectory(exportDir);
		File editFile = new File(editDir.getAbsolutePath() +"//" + jpgFileName );
		File exportFile = new File(exportDir.getAbsolutePath() +"//" + jpgFileName );
		
		//if exported flag is true, then there is no copy
		removeFiles(editFile, exportFile);
		photo.setExportedFlag(true);
		try {
			photo.copyPhotoToExportDir(photoDir);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		assertFalse("No files copied to edit dir while exportflag is false", editFile.exists());
		assertFalse("No files copied to export dir while exportflag is false", exportFile.exists());
		
		//if exported flag is false and edit flag is true, photo goes to the edit Dir
		removeFiles(editFile, exportFile);
		photo.setExportedFlag(false);
		photo.setEditFlag(true);
		try {
			photo.copyPhotoToExportDir(photoDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//photoDir.print();
		//System.out.println(editFile.toPath());
		//System.out.println(exportFile.toPath());
		assertTrue("File exists in the edit Dir", editFile.exists());
		assertFalse("No File exists in the export Dir", exportFile.exists());
		
		//if exported flag is false and edit flag is false, photo goes to the export Dir
		removeFiles(editFile, exportFile);
		photo.setExportedFlag(false);
		photo.setEditFlag(false);
		try {
			photo.copyPhotoToExportDir(photoDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertTrue("File exists in the export Dir", exportFile.exists());
		assertFalse("No File exists in the edit Dir", editFile.exists());
	}
	
	@Test
	public void Tags() {
		String[] origTags = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
		TagList tags = new TagList(origTags);
		
		//getTags and setTags
		photo.setTags(tags);
		TagList newTags = photo.getTags();
		assertEquals("one", newTags.getText(0));
		assertEquals("ten", newTags.getText(9));
		
		photo.setTagText(1,  "george");
		assertEquals("getTagText", "george", photo.getTagText(1));
		
	}
	
	@Test
	public void TagPhotos() {
		//This isn't really a test, since the output has to be checked manually
		String[] origTags = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
		TagList tags = new TagList(origTags);
		
		photo.setTags(tags);
		//tagPhoto
		try {
			photo.tagPhoto();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue("Manually check tags in browser", true);
		
	}
	
	private void removeFiles(File editFile, File exportFile) {
		if(editFile.exists()) {
			try {
				FileUtils.forceDelete(editFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(exportFile.exists()) {
			try {
				FileUtils.forceDelete(exportFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
