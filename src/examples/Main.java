package examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import parser.parser;

public class Main {
	public static void main(String[] args) throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("> ");
			for (String line; (line = in.readLine()) != null ;) {
				line = line.trim();
				try {
					if (line.length() > 0) {
						Stmt prog = (Stmt)(parser.parse(line).value);
						System.out.print("\t"+ prog +"\n> ");
					}
				} catch (Exception err) {
					System.err.print(err);
					err.printStackTrace();
				}
			}
	}
}