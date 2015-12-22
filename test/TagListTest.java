
import static org.junit.Assert.*;

import org.junit.Test;

import aberscan.TagList;

public class TagListTest {
	String[] origTags = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten"};
	TagList tags = new TagList(origTags);

	@Test
	public void getText() {
		//text
		for(int i = 0 ; i < tags.getNumTags(); i++ ) {
			assertEquals("getText", origTags[i], tags.getText(i));
		}
	}
	
	@Test
	public void setText() {
		for(int i = 0 ; i < tags.getNumTags(); i++ ) {
			tags.setText(i, "test");
			assertEquals("setText","test", tags.getText(i));
		}
	}
	
	@Test
	public void getNumTags() {
		assertEquals("numTags", 10, tags.getNumTags());
	}
	
	@Test
	public void resetTags() {
		TagList tmpTags = new TagList(origTags);
		tmpTags.resetTags();
		
		for(int i = 0 ; i < tmpTags.getNumTags(); i++ ) {
			assertEquals("resetTags", "", tmpTags.getText(i));
		}
	}
	
	@Test
	public void validText() {
		assertTrue("Valid text", tags.validText("abc"));
		assertFalse("Invalid text", tags.validText("abc,def"));
		assertFalse("Invalid text",tags.validText("abc\"def"));
	}
	
	@Test
	public void nonNullTag() {
		TagList nullTags = new TagList();
		
		assertTrue("Tags Exist", tags.containsNonNullTag());
		assertFalse("No Tags Exist", nullTags.containsNonNullTag());
	}
}
