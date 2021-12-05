package com.linuxlang;

public class Calculations {
	public static int add(Variable var, Variable var1) {
		int value = Integer.parseInt(var.value) + Integer.parseInt(var1.value);
		return value;
	}
	
	public static int subtract(Variable var, Variable var1) {
		int value = Integer.parseInt(var.value) - Integer.parseInt(var1.value);
		return value;
	}
}
