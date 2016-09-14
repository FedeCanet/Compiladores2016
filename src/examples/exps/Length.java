package examples.exps;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

public class Length extends Exp{
	
	private Exp expresion;
	
	public Length(Exp expresion) {
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
	public Object evaluate(State state) throws Exception{
		Object value = expresion.evaluate(state);
		
		if(value instanceof String)
			return ((String) value).length();
		else
			return "Wrong type.";
	}

	@Override public Tipo check(CheckState s){
		//Obtenemos el tipo de la expresión.
		Tipo typeExpresion = expresion.check(s);
		
		if(typeExpresion == Tipo.Cadena)
			return typeExpresion;
		else{
			s.errores.add(expresion.toString() + " not available for that Length() function.");
			return Tipo.Cadena;
		}
	}
}
