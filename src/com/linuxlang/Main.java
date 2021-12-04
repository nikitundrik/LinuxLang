package com.linuxlang;

public class Main {
	public static void main(String[] args) {
		// Creates a launcher
		Launcher launcher = new Launcher();
		// Try to get an arguments
		try {
			if (args[0].equalsIgnoreCase("e")) {
				launcher.launchCode(args[1]);
			} else {
				System.out.println("Invalid argument!");
				launcher.printCommands();
			}
		// If not, do this
		} catch (ArrayIndexOutOfBoundsException e) {
			launcher.printCommands();
		}
	}

}
