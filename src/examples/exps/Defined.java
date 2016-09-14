package examples.exps;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

public class Defined extends Exp{

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
	public Object evaluate(State state) {
		if(expresion instanceof Variable)
			return state.getVariable(((Variable) expresion).id) != null;
		else
			return expresion.toString() + " not available for that Defined(var) function.";
	}
	
	@Override public Tipo check(CheckState s){
		if(expresion instanceof Variable){
			//Si la variable existe, retornamos el tipo de la variable, sino asumimos Númerico y agregamos msj de error.
			if(s.exsist(((Variable)expresion).id)){
				return Tipo.Booleano;
			}else{
				s.errores.add("La variable " + ((Variable)expresion).id + " no esta definida");
				return Tipo.Booleano;
			}
		}else{
			s.errores.add(expresion.toString() + " not available for that Defined(var) function.");
			return Tipo.Booleano;
		}
	}

}
