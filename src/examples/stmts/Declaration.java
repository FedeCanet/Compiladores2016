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
		if(state.exsist(id)){
			throw new Exception("La variable " + id + " ya esta declarada.");
		}else{
			if(exp != null){
				Object value = exp.evaluate(state);
				state.create(id, value);
				return state;
			}else{
				state.create(id, null);
				return state;
			}			
		}
	}
	@Override
	public CheckState check(CheckState s) throws Exception {
		if(exp != null){
			Tipo tipoDeLaExp = exp.check(s);
			//Verificamos que ya no exista la variable.
			if(s.exsist(id)){
				//Estamos en Declaration, entonce hay ejemplo int algo.. num algo... si la variable existe hay que mandar msj de error
				s.errores.add("La variable " + id + " ya esta definida");
				
				//Verificamos que lo que el valor que se le asigna sea del mismo tipo.
				if(!t.isComatible(tipoDeLaExp)){
					s.errores.add("El tipo de la asignación " + tipoDeLaExp + " no se puede asignar a la variable " + id + " del tipo " + t);
				}
				
				return s;
			}else{
				//No existe, la creamos.
				s.create(id, t, false);
				//Verificamos que lo que el valor que se le asigna sea del mismo tipo.
				if(!t.isComatible(tipoDeLaExp)){
					s.errores.add("El tipo de la asignación " + tipoDeLaExp + " no se puede asignar a la variable " + id + " del tipo " + t);
				}
			}
		}else{
			if(s.exsist(id)){
				s.errores.add("El tipo de la Asignación no es del mismo tipo que la variable.");
			}else{
				s.create(id, t, false);
			}
		}	
		return s;
	}

}
