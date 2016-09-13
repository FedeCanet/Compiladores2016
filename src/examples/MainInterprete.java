package examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import parser.parser;
import semantica.State;

public class MainInterprete {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("> ");
			State state = new State();
			for (String line; (line = in.readLine()) != null ;) {
				line = line.trim();
				try {
					if (line.length() > 0) {
						Stmt prog = (Stmt)(parser.parse(line).value);
						state = prog.evaluate(state);
						System.out.print("\t"+ state +"\n> ");
					}
				} catch (Exception err) {
					System.err.print(err);
					err.printStackTrace();
				}
			}
	}
}