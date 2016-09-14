package examples.exps;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

/** Representación de las asignaciones de valores a variables.
*/
public class Assignment extends Exp {
	public final String id;
	public final Exp expression;

	public Assignment(String id, Exp expression) {
		this.id = id;
		this.expression = expression;
	}

	@Override public String unparse() {
		return id +" = "+ expression.unparse() +"; ";
	}

	@Override public String toString() {
		return "Assignment("+ id +", "+ expression +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.id == null ? 0 : this.id.hashCode());
		result = result * 31 + (this.expression == null ? 0 : this.expression.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Assignment other = (Assignment)obj;
		return (this.id == null ? other.id == null : this.id.equals(other.id))
			&& (this.expression == null ? other.expression == null : this.expression.equals(other.expression));
	}

	public static Assignment generate(Random random, int min, int max) {
		String id; Exp expression; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		expression = Exp.generate(random, min-1, max-1);
		return new Assignment(id, expression);
	}
	
	@Override public Object evaluate(State state) throws Exception{
		Object value = expression.evaluate(state);
		state.create(id, value);
		return value;
	}
	
	@Override public Tipo check(CheckState s){
		
		//Obtenemos el tipo de la asignaci�n
		Tipo assignType = expression.check(s);
		
		//Verificamos si la variable ya existe, de lo contrario la declaramos.
		if(s.exsist(id)){
			//Existe validamos que lo que vamos a asignar sea del mismo tipo que la variable.
			if(s.getVarInfos(id).t == assignType){
				//Es del mismo tipo entonces no hacemos nada.
			}else{
				//No es del mismo tipo, debemos continuar pero lanzando error.
				s.errores.add("El tipo de la asignaci�n no es del mismo tipo que la variable. Tipo Variable= " + s.getVarInfos(id).t + ", Tipo Asignaci�n= " + assignType);
			}
		}else{
			//No existe, la creamos.
			s.create(id, assignType, true);
		}		
				
		return assignType;
	}
}
