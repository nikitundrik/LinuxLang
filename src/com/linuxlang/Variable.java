package com.linuxlang;

public class Variable {
	// Create the variables
	String typeOfValue;
	String name;
	String value;
	
	// Initialize the object
	public Variable(String typeOfValue, String name, String value) {
		this.typeOfValue = typeOfValue;
		this.name = name;
		this.value = value;
	}
	
	// Turn the variable into the string
	public String toString() {
		return value;
	}
}
