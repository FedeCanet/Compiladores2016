package examples.exps;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo;
import semantica.VarInfo.Tipo;
import examples.Exp;

/** RepresentaciÃ³n de las negaciones de expresiones booleanas.
*/
public class Negation extends Exp {
	public final Exp condition;

	public Negation(Exp condition) {
		this.condition = condition;
	}

	@Override public String unparse() {
		return "(!"+ condition.unparse() +")";
	}

	@Override public String toString() {
		return "Negation("+ condition +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Negation other = (Negation)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition));
	}

	public static Negation generate(Random random, int min, int max) {
		Exp condition; 
		condition = Exp.generate(random, min-1, max-1);
		return new Negation(condition);
	}
	
	@Override public Object evaluate(State state) throws Exception{
		Object value1 = condition.evaluate(state);
		
		if (value1 instanceof Boolean)  {
			return !((Boolean)value1);
		}
		
		return lanzarError("La evaluación de la Negation no puede realizarse debido a que el operando no es del tipo requerido, este es " + value1);
	}
	
	@Override public Tipo check(CheckState s){	
		//Obtenemos el tipo
		Tipo tipoCondicion = condition.check(s);
		
		if(tipoCondicion == Tipo.Booleano){
			return tipoCondicion;
		}else{
			s.errores.add("El operando de la Negation no es del tipo requerido, este es " + tipoCondicion);
			return Tipo.Booleano;//Asumimos para poder continuar con el chequeo de tipos.
		}
	}
}
