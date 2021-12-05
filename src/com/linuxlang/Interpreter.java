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
	// Boolean that is true if if started
	boolean isIf;
	// List of if operations
	List<String> ifOperations;
	// Values of if command
	int ifValue;
	int ifValue1;
	// String of what to do in if
	String ifToDo;
	// List of loop operations
	List<String> loopOperations;
	// Boolean that is true if loop started
	boolean isLoop;
	// Values of loop command
	int loopValue;
	int loopValue1;
	// String of what to do in loop
	String loopToDo;
	
	// Constructor that interprets the source code
	public Interpreter(String filename) {
		// Initialization of variables
		variables = new ArrayList<Variable>();
		ifOperations = new ArrayList<String>();
		loopOperations = new ArrayList<String>();
		isIf = false;
		isLoop = false;
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
					// Split the data variable
					String[] splittedData = data.split(" ");
					// If data has the command linux (print)
					if (!isIf && !isLoop) {
						doOperation(data);
					} else if (isIf && !data.equals("x")) { // Add the line to the ifOperations
						ifOperations.add(data);
					} else if (isLoop && !data.equals("x")) {
						loopOperations.add(data);
					}
					// If substring is equal to nux, start the operations for if
					if (!isIf && splittedData[0].equals("nux")) {
						isIf = true;
						for (Variable variable: variables) {
							if (variable.name.equals(splittedData[1])) {
								ifValue = Integer.parseInt(variable.value);
								break;
							}
						}
						ifValue1 = Integer.parseInt(splittedData[3]);
						ifToDo = splittedData[2];
					} else if (splittedData[0].equals("x") && isIf) { // If substring is equal to x, do everything that is in if
						if (ifValue > ifValue1 && ifToDo.equals(">")) {
							for (String action: ifOperations) {
								doOperation(action);
							}
						} else if (ifValue < ifValue1 && ifToDo.equals("<")) {
							for (String action: ifOperations) {
								doOperation(action);
							}
						}
						isIf = false;
						ifOperations = new ArrayList<String>();
					} else if (!isLoop && splittedData[0].equals("lili")) {  // If substring is equal to nux, start the operations for if
						isLoop = true;
						loopValue = Integer.parseInt(splittedData[1]);
						loopValue1 = Integer.parseInt(splittedData[3]);
						loopToDo = splittedData[2];
					} else if (splittedData[0].equals("x") && isLoop) {
						if (loopValue > loopValue1 && loopToDo.equals(">")) {
							while (loopValue > loopValue1) {
								for (String action: loopOperations) {
									doOperation(action);
								}
								loopValue--;
							}
						} else if (loopValue < loopValue1 && loopToDo.equals("<")) {
							while (loopValue < loopValue1) {
								for (String action: loopOperations) {
									doOperation(action);
								}
								loopValue++;
							}
						}
						loopOperations = new ArrayList<String>();
						isLoop = false;
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
	
	// Function that does a certain operation
	public void doOperation (String data) {
		String[] splittedData = data.split(" ");
		if (splittedData[0].equals("linux")) {
			// List through the values of a splittedData array
			for (int i = 1; i < splittedData.length; i++) {
				// Do this if splittedData[i] contains { ({} is a template for printing variables)
				if (splittedData[i].contains("{")) {
					// Creation of the variable names
					String variableName = "";
					String variableName1 = "";
					// Creation of boolean that is true when we access the first variableName
					boolean isFirstName = true;
					// Creation of calculation type variable
					char calcType = ' ';
					// Turn the value of splittedData of the index i into array of chars
					char[] stringArr = splittedData[i].toCharArray();
					// Change it, so it doesn't include the {
					stringArr = Arrays.copyOfRange(stringArr, 1, stringArr.length);
					// Get the variable name
					for (int j = 0; j < stringArr.length; j++) {
						// If the template ends, do this
						if (stringArr[j] == '}') {
							// If isFirstName is equal to true, do this
							if (isFirstName) {
								// List through the variables list
								for (Variable variable: variables) {
									// If some value has the name that is equal variableName, print the value of it and break the loop
									if (variable.name.equals(variableName)) {
										System.out.print(variable.toString() + " ");
										break;
									}
								}
							} else { // If it's equal to true, do this
								// Creation of variable objects
								Variable var = null;
								Variable var1 = null;
								// List through the variables list
								for (Variable variable: variables) {
									if (variable.name.equals(variableName)) {
										var = variable;
									}
								}
								for (Variable variable: variables) {
									if (variable.name.equals(variableName1)) {
										var1 = variable;
									}
								}
								// Print the result of calculation
								if (calcType == '+') {
									System.out.print(Calculations.add(var, var1));
								} else if (calcType == '-') {
									System.out.print(Calculations.subtract(var, var1));
								}
							}
						} else if (stringArr[j] == '+') { // If the char is equal to +
							isFirstName = false;
							calcType = '+';
						} else if (stringArr[j] == '-') { // If the char is equal to -
							isFirstName = false;
							calcType = '-';
						} else {
							// Add the char to the variable name 1 if isFirstName = true
							if (isFirstName) {
								variableName += stringArr[j];
							} else { // Add the char to the variable name 1 if isFirstName = true
								variableName1 += stringArr[j];
							}
						}
					}
				}
				// If not, print the value of splittedData with the index i
				else {
					System.out.print(splittedData[i] + " ");
				}
			}
			System.out.println();
		// If data has the command li (creation of a variable)
		} else if (splittedData[0].equals("li")) {
			// The data turned into the array
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
}
