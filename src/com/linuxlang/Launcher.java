package com.linuxlang;

public class Launcher {
	// Function that prints commands
	public void printCommands() {
		System.out.println("Linux Lang arguments: ");
		System.out.println("e: Execute program");
	}
	
	// Function that crates the interpreter
	public void launchCode(String filename) {
		Interpreter interpreter = new Interpreter(filename);
	}
}
