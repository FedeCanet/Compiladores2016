package examples.stmts;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;
import examples.Stmt;

public class Declaration extends Stmt{
	Tipo t;
	String id;
	Exp exp;
	
	public Declaration(Tipo _t, String _id, Exp _exp) {
		this.t = _t;
		this.id = _id;
		this.exp = _exp;
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
	public State evaluate(State state) throws Exception {
		String a="lego";
		if(a.equals("a"))
			return null;
		return null;
	}
	@Override
	public CheckState check(CheckState s) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
