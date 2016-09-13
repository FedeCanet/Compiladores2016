package Test;

import org.junit.Assert;
import org.junit.Test;

import parser.parser;
import semantica.State;
import examples.Stmt;

public class TestMain {

	Stmt prog;
	
	@Test
	public void test() {
		
		// Caso 1 - Asignación:
		String [] lines = {"x = 1;"};
		State state = new State();
		state.create("x", 1.0);
		chequearComparacion(lines, state);	
		
		// Caso 2 - Multiplicación:
		lines[0] = "x = 2*2;";
		state = new State();
		state.create("x", 4.0);
		chequearComparacion(lines, state);	
		
		// Caso 3 - Resta:
		lines[0] = "x = 2-1;";
		state = new State();
		state.create("x", 1.0);
		chequearComparacion(lines, state);	
		
		// CASO 4 - Suma:
		lines[0]= "x = 2 + 3;";		
		state = new State();
		state.create("x", 5.0);
		chequearComparacion(lines, state);	
		
		// CASO 5 - Asignación de expresiones y Suma:
		lines[0] = "x = 1 + y = 2;";
		state = new State();
		state.create("x", 3.0);
		state.create("y", 2.0);
		chequearComparacion(lines, state);
		
		// CASO 6 - Asignación de expresion booleana
		lines[0] = "var = true;";
		state = new State();
		state.create("var", true);
		chequearComparacion(lines, state);
		
		// Caso 7 - CompareEqual with ifThenDoubles
		state = new State();
		state.create("x", 1.0);
		state.create("y", 1.0);
		state.create("z", 1.0);
		lines = new String[3];
		lines[0] = "x=1;"; lines[1]="y=1;"; lines[2]="if(x==y)then z=1;";
		chequearComparacion(lines, state);
		
		// Caso 8 - Conjunction with ifThen Boolean
		state = new State();
		state.create("var1", true);
		state.create("var2", true);
		state.create("z", 4.0);
		lines = new String[3];
		lines[0] = "var1=true;"; lines[1]="var2=true;"; lines[2]="if(var1&&var2)then z=4;";
		chequearComparacion(lines, state);
		
		// Caso 9 - CompareLessOrEqual with IfThen Doubles
		state = new State();
		state.create("x", 1.0);
		state.create("y", 2.0);
		state.create("z", 1.0);
		lines = new String[3];
		lines[0] = "x=1;"; lines[1]="y=2;"; lines[2]="if(x<=y)then z=1;";
		chequearComparacion(lines, state);
		
		// Caso 10 - CompareLessOrEqual with ifThenElse Doubles
		state = new State();
		state.create("x", 2.0);
		state.create("y", 1.0);
		state.create("z", 2.0);
		lines = new String[3];
		lines[0] = "x=2;"; lines[1]="y=1;"; lines[2]="if(x<=y)then z=1; else z=2;";
		chequearComparacion(lines, state);
		
		// Caso 11 - Negation with ifThenElse
		state = new State();
		state.create("var", true);
		state.create("x", 2.0);
		lines = new String[2];
		lines[0] = "var=true;"; lines[1]="if(!var)then x=1; else x=2;";
		chequearComparacion(lines, state);
		
		//Caso 12 - While-Do
		state = new State();
		state.create("x", 11.0);
		state.create("y", 10.0);
		lines = new String[3];
		lines[0] = "x=1;"; lines[1]="y=10;"; lines[2]="while(x<=y)do x=x+1;";
		chequearComparacion(lines, state);
	}
	
	/**
	 * Método, recibe una entrada y un estado, verifica que la entrada genere el mismo estado
	 * recibido por parámetro.
	 * @param line
	 * @param stateEsperado
	 */	
	private void chequearComparacion(String [] lines, State stateEsperado){
		try {
			State state = new State();
			
			for(int i=0; i<lines.length; i++){
				System.out.println(lines[i]);
				prog = (Stmt)(parser.parse(lines[i]).value);
				state = prog.evaluate(state);
				System.out.print("\t"+ state.toString() +"\n> ");
			}		
			
			Assert.assertEquals(stateEsperado.toString(), state.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
