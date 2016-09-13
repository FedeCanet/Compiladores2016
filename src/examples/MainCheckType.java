package examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import parser.parser;
import semantica.CheckState;

public class MainCheckType {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("> ");
			CheckState checkState = new CheckState();
			for (String line; (line = in.readLine()) != null ;) {
				line = line.trim();
				try {
					if (line.length() > 0) {
						Stmt prog = (Stmt)(parser.parse(line).value);
						checkState = prog.check(checkState);
						System.out.print("\t"+ checkState +"\n> ");
					}
				} catch (Exception err) {
					System.err.print(err);
					err.printStackTrace();
				}
			}
	}
}