package Test;

import org.junit.Assert;
import org.junit.Test;

import parser.parser;
import semantica.CheckState;
import semantica.VarInfo;
import semantica.VarInfo.Tipo;
import examples.Stmt;

public class TestMainCheckType {

	Stmt prog;
	
	@Test
	public void test() {
		
		/**
		 * CASOS SIN ERROR PARA CHEQUEAR LOS TIPOS.
		 */
		
		// Caso 1 - Asignación:
		String [] lines = {"x = 1;"};
		CheckState state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		chequearComparacion(lines, state);	
		
		// Caso 2 - Multiplicación:
		lines[0] = "x = 2*2;";
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		chequearComparacion(lines, state);	
		
		// Caso 3 - Resta:
		lines[0] = "x = 2-1;";
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		chequearComparacion(lines, state);	
		
		// CASO 4 - Suma:
		lines[0]= "x = 2 + 3;";		
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		chequearComparacion(lines, state);	
		
		// CASO 5 - Asignación de expresiones y Suma:
		lines[0] = "x = 1 + y = 2;";
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		state.create("y", new VarInfo(Tipo.Numerico, true));
		chequearComparacion(lines, state);
		
		// CASO 6 - Asignación de expresion booleana
		lines[0] = "var = true;";
		state = new CheckState();
		state.create("var", new VarInfo(Tipo.Booleano, true));
		chequearComparacion(lines, state);
		
		// Caso 7 - CompareEqual with ifThenDoubles
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		state.create("y", new VarInfo(Tipo.Numerico, true));
		//el z no va porque se declara en el cuerpo del if y al hacer intersección no se obtiene.
		lines = new String[3];
		lines[0] = "x=1;"; lines[1]="y=1;"; lines[2]="if(x==y)then z=1;";
		chequearComparacion(lines, state);
		
		// Caso 8 - Conjunction with ifThen Boolean
		state = new CheckState();
		state.create("var1", new VarInfo(Tipo.Booleano, true));
		state.create("var2", new VarInfo(Tipo.Booleano, true));
		//el z no va porque se declara en el cuerpo del if y al hacer intersección no se obtiene.
		lines = new String[3];
		lines[0] = "var1=true;"; lines[1]="var2=true;"; lines[2]="if(var1&&var2)then z=4;";
		chequearComparacion(lines, state);
		
		// Caso 9 - CompareLessOrEqual with IfThen Doubles
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		state.create("y", new VarInfo(Tipo.Numerico, true));
		//el z no va porque se declara en el cuerpo del if y al hacer intersección no se obtiene.
		lines = new String[3];
		lines[0] = "x=1;"; lines[1]="y=2;"; lines[2]="if(x<=y)then z=1;";
		chequearComparacion(lines, state);
		
		// Caso 10 - CompareLessOrEqual with ifThenElse Doubles
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		state.create("y", new VarInfo(Tipo.Numerico, true));
		//el z no va porque se declara en el cuerpo del if y al hacer intersección no se obtiene.
		lines = new String[3];
		lines[0] = "x=2;"; lines[1]="y=1;"; lines[2]="if(x<=y)then z=1; else z=2;";
		chequearComparacion(lines, state);
		
		// Caso 11 - Negation with ifThenElse
		state = new CheckState();
		state.create("var", new VarInfo(Tipo.Booleano, true));
		//el x no va porque se declara en el cuerpo del if y al hacer intersección no se obtiene.
		lines = new String[2];
		lines[0] = "var=true;"; lines[1]="if(!var)then x=1; else x=2;";
		chequearComparacion(lines, state);
		
		//Caso 12 - While-Do
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		state.create("y", new VarInfo(Tipo.Numerico, true));
		lines = new String[3];
		lines[0] = "x=1;"; lines[1]="y=10;"; lines[2]="while(x<=y)do x=x+1;";
		chequearComparacion(lines, state);
		
		//Caso 13 - While-Do con declaración dentro del bucle.
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		state.create("y", new VarInfo(Tipo.Numerico, true));
		//el z no va porque se declara dentro del cuerpo del While, al hacer la intersección no se obtiene.
		lines = new String[3];
		lines[0] = "x=1;"; lines[1]="y=10;"; lines[2]="while(x<=y)do z=x+y;";
		chequearComparacion(lines, state);
		
		/**
		 * CASOS CON ERROR
		 */
		
		// Caso 14 - Con Errores:
		state = new CheckState();
		//Asumimos que es númerico, debe pasar el test pero imprimir error.
		state.create("x", new VarInfo(Tipo.Numerico, true));
		lines = new String[2];
		lines[0] = "x=true*2;"; lines[1]="x=1+false;";
		chequearComparacion(lines, state);	
		
		// Caso 15 - Con Errores:
		state = new CheckState();
		//Asumimos que es númerico, debe pasar el test pero imprimir error.
		state.create("x", new VarInfo(Tipo.Numerico, true));
		state.create("y", new VarInfo(Tipo.Numerico, true));
		lines = new String[2];
		lines[0] = "x=x+1;"; lines[1]="x=y*2;";
		chequearComparacion(lines, state);
		
		// Caso 16 - Con Errores:
		state = new CheckState();
		lines = new String[1];
		lines[0] = "while 1 do {}";
		chequearComparacion(lines, state);
		
		// Caso 17 - Con Errores:
		state = new CheckState();
		lines = new String[1];
		lines[0] = "if 2 then {} else {}";
		chequearComparacion(lines, state);
		
		// Caso 18 - Con Errores:
		state = new CheckState();
		lines = new String[1];
		lines[0] = "if " + '"' + "x"+ '"' + " then {} else {}";
		chequearComparacion(lines, state);
		
		// Caso 19 - Con Errores:
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		lines = new String[1];
		lines[0] = "x=" + '"' + "x"+ '"' + "*2;";
		chequearComparacion(lines, state);
		
		// Caso 20 - Con Errores:
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Numerico, true));
		lines = new String[3];
		lines[0] = "x=17;"; lines[1] = "x=true;"; lines[2] = "x=x+1;";
		chequearComparacion(lines, state);
		
		// Caso 21 - Con Errores:
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Booleano, true));
		lines = new String[1];
		lines[0] = "x=true <= "+ '"' + "false" + '"' + ";";
		chequearComparacion(lines, state);
		
		// Caso 22 - Con Errores:
		state = new CheckState();
		state.create("x", new VarInfo(Tipo.Booleano, true));
		lines = new String[2];
		lines[0] = "x=true;"; lines[1] = "while(x)do{x=1; x=x+1;}";
		chequearComparacion(lines, state);
		
		// Caso 23 - Con Errores:
		state = new CheckState();
		lines = new String[1];
		lines[0] = "if true then {x=1;} else {x="+'"' + "1" + '"' + "; " + "x=x*2;" + "}";
		chequearComparacion(lines, state);
		
	}
	
	/**
	 * Método, recibe una entrada y un estado, verifica que la entrada genere el mismo estado
	 * recibido por parámetro.
	 * @param line
	 * @param stateEsperado
	 */	
	private void chequearComparacion(String [] lines, CheckState stateEsperado){
		try {
			CheckState state = new CheckState();
			
			for(int i=0; i<lines.length; i++){
				System.out.println(lines[i]);
				prog = (Stmt)(parser.parse(lines[i]).value);
				state = prog.check(state);
				System.out.print("\t"+ state.toString() +"\n> ");
			}		
			
			Assert.assertEquals(stateEsperado.printTipos(), state.printTipos());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
