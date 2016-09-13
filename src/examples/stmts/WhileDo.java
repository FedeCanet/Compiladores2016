package examples.stmts;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;
import examples.Stmt;

/** RepresentaciÃ³n de las iteraciones while-do.
*/
public class WhileDo extends Stmt {
	public final Exp condition;
	public final Stmt body;

	public WhileDo(Exp condition, Stmt body) {
		this.condition = condition;
		this.body = body;
	}

	@Override public String unparse() {
		return "while "+ condition.unparse() +" do { "+ body.unparse() +" }";
	}

	@Override public String toString() {
		return "WhileDo("+ condition +", "+ body +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.body == null ? 0 : this.body.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		WhileDo other = (WhileDo)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
			&& (this.body == null ? other.body == null : this.body.equals(other.body));
	}

	public static WhileDo generate(Random random, int min, int max) {
		Exp condition; Stmt body; 
		condition = Exp.generate(random, min-1, max-1);
		body = Stmt.generate(random, min-1, max-1);
		return new WhileDo(condition, body);
	}
	
	@Override public State evaluate(State state) throws Exception{
		while((Boolean)condition.evaluate(state)){
			state = body.evaluate(state);
		}
		return state;
	}
	
	@Override
	public CheckState check(CheckState s) throws Exception{
		//Obtenemos el tipo de la condición.
		Tipo tipoCondition = condition.check(s);
		
		if(tipoCondition != Tipo.Booleano){
			s.errores.add("La condición del WhileDo no es del tipo esperado, " + tipoCondition);
		}
		
		//Obtenemos una copia del estado actual.
		CheckState bodyState = (CheckState)s.clone();
		
		//Retornamos la intersección entre el estado actual y el estado resultante
		//de evaluar el cuerpo del WhileDo.
		return s.intersectWith(body.check(bodyState));
	}
}
