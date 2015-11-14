

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import aberscan.Photo;
import aberscan.PhotoList;

public class PhotoListTest {
	String rootEclipseTestDir = "D:\\MyJavaApplications\\eclipseworkspace\\PhotoLibrary\\testdata";
	File rootTestDir = new File(rootEclipseTestDir);
	PhotoList photos = new PhotoList();
	int numPhotos = 6;

	@Test
	public void loadPhotos() {
		
		//covers 'loadPhotos' and 'getNumPhotos'
		photos.loadPhotos(rootTestDir);
		assertEquals(numPhotos, photos.getNumPhotos());
		
		//test the 'emptyList' function
		photos.loadPhotos(rootTestDir);
		assertEquals(2*numPhotos, photos.getNumPhotos());
		
		photos.emptyList();
		photos.loadPhotos(rootTestDir);
		assertEquals(numPhotos, photos.getNumPhotos());
	}
	
	@Test
	public void limits() {
		//covers 'atBeginning', 'atEnd', and 'setIndex'
		photos.loadPhotos(rootTestDir);
		
		//beginning
		photos.setCurIndex(0);
		assertTrue(photos.atBeginning());
		photos.setCurIndex(1);
		assertFalse(photos.atBeginning());
		
		
		//beginning
		photos.setCurIndex(numPhotos-1);
		assertTrue(photos.atEnd());
		photos.setCurIndex(0);
		assertFalse(photos.atEnd());
	}
	
	@Test
	public void getCurIndex() {
		//covers 'getCurIndex'
		photos.loadPhotos(rootTestDir);

		for(int i = 0 ; i < numPhotos; i++ ) {
			photos.setCurIndex(i);
			assertEquals(i, photos.getCurIndex());
		}
	}
	
	@Test
	public void photos() {
		Photo firstPhoto = new Photo( new File(rootEclipseTestDir + "\\049-11.jpg"));
		Photo secondPhoto = new Photo( new File(rootEclipseTestDir + "\\049-11.tif"));
		Photo thirdPhoto = new Photo( new File(rootEclipseTestDir + "\\049-14.jpg"));
		Photo noPhoto = new Photo( new File(rootEclipseTestDir + "\\george.jpg"));
		int foundPhoto;
		photos.loadPhotos(rootTestDir);
		
		//numPhotos
		assertEquals(numPhotos, photos.getNumPhotos());
		
		//getPhoto
		photos.setCurIndex(0);
		assertEquals(firstPhoto.getName(), photos.getPhoto().getName());
		photos.setCurIndex(1);
		assertEquals(secondPhoto.getName(), photos.getPhoto().getName());;
		assertEquals(firstPhoto.getName(), photos.getPhoto(0).getName());
		assertEquals(secondPhoto.getName(), photos.getPhoto(1).getName());
		
		//find photo
		assertEquals(0, photos.findPhoto(firstPhoto));
		assertEquals(1, photos.findPhoto(secondPhoto));
		assertEquals(2, photos.findPhoto(thirdPhoto));
		assertEquals(-1, photos.findPhoto(noPhoto));
		
		//check 'first' photo
		photos.setCurIndex(1);
		assertEquals(firstPhoto.getName(), photos.getFirstPhoto().getName());  //should reset curIndex back to zero even though set to 1 here
		photos.setStartPhoto(secondPhoto);
		assertEquals(secondPhoto.getName(), photos.getUserSelectedFirstPhoto().getName()); //should start from where user started
		
		//getFirst/Next/Prev
		photos.setCurIndex(1);
		assertEquals(firstPhoto.getName(), photos.getFirstPhoto().getName());
		assertEquals(secondPhoto.getName(), photos.getNextPhoto().getName());
		assertEquals(thirdPhoto.getName(), photos.getNextPhoto().getName());
		assertEquals(secondPhoto.getName(), photos.getPrevPhoto().getName());
		assertEquals(firstPhoto.getName(), photos.getPrevPhoto().getName());
	}

}
