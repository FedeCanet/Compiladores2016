package examples.stmts;

import examples.Exp;
import examples.Stmt;
import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;

public class Print extends Stmt{
	
	private Exp expresion;
	
	public Print(Exp expresion){
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
	public State evaluate(State state) throws Exception{
		System.out.println(expresion.evaluate(state));
		return state;
	}

	@Override
	public CheckState check(CheckState s) throws Exception {
		System.out.println(expresion.check(s));
		return s;
	}

	
}
