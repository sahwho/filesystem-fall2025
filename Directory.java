import java.util.ArrayList;

public class Directory extends File {
	private ArrayList<File> files;
	private String pathToDir;
	
	public Directory(String fileName, Directory parentReference, String pathToHere) {
		// TODO
	}
	
	public ArrayList<File> getFiles() {
		return files;
	}
	
	public String getPathToDir() {
		return pathToDir;
	}
}
