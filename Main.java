import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		
		FileSystem fs = new FileSystem();
		
		Scanner in = new Scanner(System.in);
		System.out.println("ADSB fileSystem Program");
		System.out.println("enter a command (enter \"help\" to see available commands)");
		while (true) {
			System.out.println("~ " + fs.getWorkingDirectory().getPathToDir());
			System.out.print("$ ");
			String input = in.nextLine();
			input = input.trim();
			
			String[] parts = input.split(" "); // if there is no space, the array will just contain the command, like ["cd"]
			
			fs.processCommand(parts);
		}
	}
}
