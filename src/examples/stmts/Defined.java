package examples.stmts;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo;
import semantica.VarInfo.Tipo;
import examples.Exp;
import examples.Stmt;
import examples.exps.Variable;

public class Defined extends Stmt{

	private Exp expresion;
	
	public Defined(Exp expresion){
		this.expresion = expresion;
	}
	
	@Override
	public String unparse() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public State evaluate(State state) {
		if(expresion instanceof Variable)
			System.out.println(state.getVariable(((Variable) expresion).id) != null);
		else
			System.out.println(expresion.toString() + " not defined.");
		
		return state;
	}
	
	@Override public CheckState check(CheckState s){		
		Tipo tipo1 = expresion.check(s);//Esto me devuelve el tipo de la expresion.
		return s; 
	}

}
