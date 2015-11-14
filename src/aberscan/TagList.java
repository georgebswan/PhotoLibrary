package aberscan;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TagList {
	String[] tags;
	final int numTags = 10;
	
	public String getText(int i) {return tags[i]; }
	public void setText(int i, String tag) { tags[i] = tag.trim();}
	public int getNumTags() {return numTags;}

	
	public TagList() {
		tags = new String[numTags];
		
		resetTags();
	}
	
	public TagList(String[] srcTags) {
		tags = new String[numTags];
		for(int i = 0 ; i < numTags; i++ ) {
			tags[i] = srcTags[i].trim();
		}
	}
	
	public void resetTags() {
		for(int i = 0 ; i < numTags ; i++){
			tags[i] = "";
		}
	}
	
	public boolean containsValidText(JFrame frame) {
		//check that each tag in the tagList contains legal characters. double quote and , are not allowed
		for(int i = 0 ; i < numTags ; i++){
			if(!validText(tags[i])) {
				JOptionPane.showMessageDialog(frame, "Text entered for Tag " + (i + 1) + " contains illegal characters. Fix before continuing");
				return(false);
			}
		}
		return(true);
	}
	
	public boolean containsNonNullTag() {
		//if all tags are null, return false. Otherwise return true
		for(int i = 0 ; i < numTags ; i++){
			if(!tags[i].equals("")) {
				return(true);
			}
		}
		return false;
	}
	
	public boolean validText(String text) {
		if(text.contains(",") || text.contains("\"")) {
			return(false);
		}
		else {
			return(true);
		}
	}
	
	public void print() {
		System.out.print("Tags: ");
		for(int i = 0 ; i < numTags ; i++){
			System.out.print("'" + tags[i] + "'" + ";" );
		}
		System.out.println("");
	}
}
