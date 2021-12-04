package com.linuxlang;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
	// List of variables
	List<Variable> variables;
	
	// Constructor that interprets the source code
	public Interpreter(String filename) {
		// Initialization of variables list
		variables = new ArrayList<Variable>();
		try {
			// Gets the file with source code
			File codeFile = new File(filename);
			// Do this if file with code exists
			if (codeFile.exists()) {
				// Scanner that scans the file
				Scanner fileScanner = new Scanner(codeFile);
				// Do this while file still has next lines
				while (fileScanner.hasNextLine()) {
					// String with the line of the file
					String data = fileScanner.nextLine();
					// If data has the command linux (print)
					if (data.substring(0, 5).equals("linux")) {
						// The data turned into the array
						String[] splittedData = data.split(" ");
						// List through the values of a splittedData array
						for (int i = 1; i < splittedData.length; i++) {
							// Do this if splittedData[i] contains { ({} is a template for printing variables)
							if (splittedData[i].contains("{")) {
								// Creation of the variable name
								String variableName = "";
								// Turn the value of splittedData of the index i into array of chars
								char[] stringArr = splittedData[i].toCharArray();
								// Change it, so it doesn't include the {
								stringArr = Arrays.copyOfRange(stringArr, 1, stringArr.length);
								// Get the variable name
								for (int j = 0; j < stringArr.length; j++) {
									// If the template ends, do this
									if (stringArr[j] == '}') {
										// List through the variables list
										for (Variable variable: variables) {
											// If some value has the name that is equal variableName, print the value of it and break the loop
											if (variable.name.equals(variableName)) {
												System.out.print(variable.toString() + " ");
												break;
											}
										}
									}
									// Add the char to the variable name
									variableName += stringArr[j];
								}
							}
							// If not, print the value of splittedData with the index i
							else {
								System.out.print(splittedData[i] + " ");
							}
						}
						System.out.println();
					// If data has the command li (creation of a variable)
					} else if (data.substring(0, 2).equals("li")) {
						// The data turned into the array
						String[] splittedData = data.split(" ");
						// The type of variable
						String type;
						// If value is number, set the type to int
						if (isNumber(splittedData[2])) {
							type = "int";
						// If not, set the type to string
						} else {
							type = "str";
						}
						// Creating of the variable object with the type, name of the variable and value of the variable
						Variable variable = new Variable(type, splittedData[1], splittedData[2]);
						// Add the variable to the list
						variables.add(variable);
					}
				}
				// Close the scanner when the work with the file finishes
				fileScanner.close();
			} else { // Do this if there is no file
				System.out.println("There is no file " + filename + "!");
			}
		} catch (FileNotFoundException e) { // Do this if there is an exception
			System.out.println("There is no file " + filename + "!");
		}
	}
	
	// Check if a string is a number
	public boolean isNumber(String s) {
		try {
			int sVal = Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
