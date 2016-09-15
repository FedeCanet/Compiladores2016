package examples.exps;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

public class NumEntero extends Exp{
	public final Integer entero;

	public NumEntero(Integer _entero) {
		this.entero = _entero;
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
	public Object evaluate(State state) throws Exception {
		return entero;
	}

	@Override
	public Tipo check(CheckState s) {
		// TODO Auto-generated method stub
		return null;
	}

}
