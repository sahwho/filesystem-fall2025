import java.util.ArrayList;

// FileSystem Project (ADSB Fall 2024) Starter Code
// Author @sahwho

public class FileSystem {
	
	private Directory root;
	private Directory workingDirectory;
	
	public static final String TOUCH_NEW_SUCCESSFUL = "file was created successfully.";
	public static final String TOUCH_UPDATE_SUCCESSFUL = "file properties were updated.";
	public static final String TOUCH_UNSUCCESSFUL_DIR_WITH_SAME_NAME_EXISTS = "a directory with that name exists already.";
	public static final String MKDIR_FILE_EXISTS_ERR = "Error: a file with that name already exists.";
	public static final String MKDIR_DIR_EXISTS_ERR = "Error: a directory with that name already exists.";
	public static final String MKDIR_SUCCESSFUL = "directory was created successfully.";
	
	// Default constructor, should set workingDirectory to root
	public FileSystem() {
		
	}
	
	public Directory getWorkingDirectory() {
		return workingDirectory;
	}
	
	public void echo(String[] parts) {
		for (int i=1; i<parts.length; i++) {
			System.out.print(parts[i] + " ");
		}
	}
	
	// ls only goes one level deep when showing filenames
	// this is discussed in the spec
	public void ls() {
		
	}
	
	public String pwd() {
		return "todo: implement pwd";
	 }
	
	// creates an empty file with this name
	// if a file with the specified name already exists, simply
	// update the lastAccessed and lastModified timestamps, without
	// modifying the contents at all.
	public String touch(String fileName) {
		return "todo: implement touch";
	}
	
	// in the case where the dir already exists, an error message is returned. 
	// a "status message" is returned when the dir is successfully created
	public String mkdir(String directoryName) {
		return "todo: implement mkdir";
	}
	
	public String tree(Directory currentDir) {
		return "todo: implement tree";
	}
	
	public void cd(String arg) {
		System.out.println("todo: implement cd");
	}
	
	public ArrayList<String> find(String fileName) {
		ArrayList<String> list = new ArrayList<String>();
		list.add("todo: implement find");
		return list;
	}
	
	public void processCommand(String [] parts) {
		if (parts.length==0)
			return;
		
		String command = parts[0]; // parts is an array of Strings
		if (command.equals("echo")) {
			echo(parts);
			System.out.println();
		} else if (command.equals("ls")) {
			ls();
			System.out.println();
		} else if (command.equals("touch")) {
			// in the case where the user enters "touch abc xyz",
			// the code below would create a new file "abc xyz".
			String touchArgs = "";
			for (int i=1; i<parts.length; i++) {
				touchArgs += parts[i];
			}
			touch(touchArgs);
		} else if (command.equals("mkdir")) {
			mkdir(parts[1]);
		} else if (command.equals("cd")) {
			if (parts.length==1) { // means the command was just "cd"
				cd("");
			} else {
				cd(parts[1]);
			}
		} else if (command.equals("tree")) {
			String fullTree = tree(root);
			System.out.println(fullTree);
		} else if (command.equals("pwd")) {
			System.out.println(pwd());
		} else if (command.equals("find")) {
			if (parts.length==1)
				System.out.println("Error: the find command must be followed by the name of a file to find.");
			else {
				System.out.println(find(parts[1]));
			}
		} else if (command.equals("help") || command.equals("\"help\"")) {
			System.out.println("Supported commands: echo, ls, touch, mkdir, cd, tree, pwd, find");
		}
		else {
			System.out.println("command not supported: " + command);
		}
	}
}
