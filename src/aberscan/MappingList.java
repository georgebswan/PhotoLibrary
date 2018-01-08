package aberscan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MappingList {
	ArrayList <Map> dirPhotoMappings;
	String mapFileName = "\\AberscanPhotoMappings.txt";
	
	public MappingList() {
    	dirPhotoMappings = new ArrayList<Map>();
	}
	
    public int getNumMaps() {return dirPhotoMappings.size();}
    public Map getMap(int i) { return dirPhotoMappings.get(i);}
    
    public void reset() {
    	dirPhotoMappings.clear();
    }
    
    public void addMapping(String dir, String file) {
    	  //System.out.println("AddMapping: dir = '" + dir + "', file = '" + file + "'" );
		  dirPhotoMappings.add(new Map(dir, file));
    }
    
    public void createMappingsFile(File toDir) throws IOException {
	    BufferedWriter out;
	    //if first time here, open file instead of append
	    out = new BufferedWriter(new FileWriter(toDir.getAbsolutePath() + mapFileName, false));
	    
   	 	for (int i = 0 ; i < dirPhotoMappings.size(); i++){
   	 		Map map = dirPhotoMappings.get(i);
   	 		
	   	 	try {
	    		out.write(map.folderName + "," + map.fileName);
	        	out.newLine();
	        } catch (IOException e) {
	  			// TODO Auto-generated catch block
	  			e.printStackTrace();
	  		}
        }
   	 	
   	 	
   	 	
	    //File[] contents = copyFromDir.listFiles();
	    //for (File file : contents) {
		    //if (file.isFile() && (file.getName().toLowerCase().endsWith("jpg") ||
		    //	file.getName().toLowerCase().endsWith("jpeg") ||
		    //	file.getName().toLowerCase().endsWith("tif") ||
		    //	file.getName().toLowerCase().endsWith("tiff"))) {
		    	
		    	//first, check if fragDirName needs to be updated
		    //	if((fragment == true) && ((photoCount - 1) % 200) == 0) {
	    	//		fragDirName = String.format( "dir%04d\\", photoCount - 1);
	    	//		//System.out.println("DirName = " + newToDir.getAbsolutePath());
	    	//	}
		    	
		    //	try {
		    //		out.write(subFolder + "," + fragDirName + file.getName());
		    //    	out.newLine();
		    //    } catch (IOException e) {
		  	//		// TODO Auto-generated catch block
		  	//		e.printStackTrace();
		  	//	}
			//photoCount++;
		    //}
	    //}
	    	
	    out.close();
	}
	
    public void loadMappingsFromFile(File folder) {
		 try{
			  FileInputStream fstream = new FileInputStream(folder.getAbsolutePath() + "\\" + mapFileName);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
				  String[] splits = strLine.split(",");
				  dirPhotoMappings.add(new Map(splits[0], splits[1]));
				  
				  // Print the content on the console
				  //System.out.println ("folderName = ;" + splits[0] + "', fileName = '" + splits[1] + "'");
			  }
			  //Close the input stream
			  in.close();
		}
		catch (Exception e) {//Catch exception if any
			  System.err.println("Error: Couldn't open the mappings file '" + mapFileName + "'" + e.getMessage());
		}
	 }
    
	public ArrayList <Map> readMappingsFile1(File folder) {
		 try{
			  FileInputStream fstream = new FileInputStream(folder.getAbsolutePath() + "\\" + mapFileName);
			  // Get the object of DataInputStream
			  DataInputStream in = new DataInputStream(fstream);
			  BufferedReader br = new BufferedReader(new InputStreamReader(in));
			  String strLine;
			  //Read File Line By Line
			  while ((strLine = br.readLine()) != null)   {
				  String[] splits = strLine.split(",");
				  dirPhotoMappings.add(new Map(splits[0], splits[1]));
				  
				  // Print the content on the console
				  //System.out.println ("folderName = ;" + splits[0] + "', fileName = '" + splits[1] + "'");
			  }
			  //Close the input stream
			  in.close();
		}
		catch (Exception e) {//Catch exception if any
			  System.err.println("Error: Couldn't open the mappings file '" + mapFileName + "'" + e.getMessage());
		}
		
		return (dirPhotoMappings);
	 }
    
	 public class Map {
		String folderName;
		String fileName;
		
		public String getFolderName() { return (folderName); }
		public String getFileName() { return (fileName); }
		public void addSubDirNameToFile(String dirName) { fileName = dirName + "\\" + fileName; }
		
		public Map(String folderName, String fileName) {
			this.folderName = folderName;
			this.fileName = fileName;
		}
	 }

	public void print() {
		System.out.println("-------------------------------------------------------");
   	 	for (int i = 0 ; i < dirPhotoMappings.size(); i++){
   	 		Map map = dirPhotoMappings.get(i);
   			System.out.println("dirName = " + map.folderName);
   			System.out.println("File = " + map.fileName);
        }
	}
}
