package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class Pascal implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String[] argumentArray = arguments.split(" ");
		if(argumentArray.length != 2) {
			env.writeln("Cat command should have 2 arguments.");
			return ShellStatus.CONTINUE;
		}
		Integer n = null;
		try {
			n = Integer.parseInt(argumentArray[1]);
			if(n <= 0 || n > 7) {
				throw new IllegalArgumentException();
			}
		} catch(Exception ex) {
			env.writeln("Second argument is not a natural number between 1 and 7.");
			return ShellStatus.CONTINUE;
		}
		
		printPascal(n, env);
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "Pascal";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();

		list.add("function that takes an integer value n as input and prints first n lines of the Pascal’s triangle");
		return Collections.unmodifiableList(list);
	}
	
	     private void printPascal(int n, Environment env) { 
	          
		    for (int line = 0; line < n; line++) { 
				
		        for (int i = 0; i <= line; i++) 
		        env.write(binomialCoeff(line, i)+" "); 
		        env.writeln("");
		                          
		    } 
	    } 
	      
	    private int binomialCoeff(int n, int k) { 
	        int res = 1; 
	          
	        if (k > n - k) 
	        k = n - k; 
	              
	        for (int i = 0; i < k; ++i) 
	        { 
	            res *= (n - i); 
	            res /= (i + 1); 
	        } 
	        return res; 
	    } 
	} 


