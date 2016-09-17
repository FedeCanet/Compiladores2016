package examples.stmts;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;
import examples.Stmt;

/** Representación de las sentencias condicionales.
*/
public class IfThen extends Stmt {
	public final Exp condition;
	public final Stmt thenBody;

	public IfThen(Exp condition, Stmt thenBody) {
		this.condition = condition;
		this.thenBody = thenBody;
	}

	@Override public String unparse() {
		return "if "+ condition.unparse() +" then { "+ thenBody.unparse() + " }";
	}

	@Override public String toString() {
		return "IfThen("+ condition +", "+ thenBody +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.thenBody == null ? 0 : this.thenBody.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		IfThen other = (IfThen)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
			&& (this.thenBody == null ? other.thenBody == null : this.thenBody.equals(other.thenBody));
	}

	public static IfThen generate(Random random, int min, int max) {
		Exp condition; Stmt thenBody; 
		condition = Exp.generate(random, min-1, max-1);
		thenBody = Stmt.generate(random, min-1, max-1);
		return new IfThen(condition, thenBody);
	}
	
	@Override public State evaluate(State state) throws Exception{
		Object value = condition.evaluate(state);
		if((Boolean)value){
			return thenBody.evaluate(state);
		}
		return state;
	}

	@Override public CheckState check(CheckState s) throws Exception{
		//Obtenemos el tipo de la condicion.
		Tipo tipoCondicion = condition.check(s);
		
		//Evaluamos la condicion.
		if(tipoCondicion != Tipo.Booleano){
			s.errores.add("La condicion del IfThen no es del tipo esperado, " + tipoCondicion);
		}
		
		//Obtenemos una copia del estado original.
		CheckState copyState = (CheckState)s.clone();
		
		//Retornamos la interseccion entre el estado Actual y el estado 
		//modificado por el cuerpo del IfThen.
		return s.intersectWith(thenBody.check(copyState));
	}	
}
