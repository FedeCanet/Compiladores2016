package examples.stmts;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;
import examples.Stmt;

/** RepresentaciÃ³n de las sentencias condicionales.
*/
public class IfThenElse extends Stmt {
	public final Exp condition;
	public final Stmt thenBody;
	public final Stmt elseBody;

	public IfThenElse(Exp condition, Stmt thenBody, Stmt elseBody) {
		this.condition = condition;
		this.thenBody = thenBody;
		this.elseBody = elseBody;
	}

	@Override public String unparse() {
		return "if "+ condition.unparse() +" then { "+ thenBody.unparse() +" } else { "+ elseBody.unparse() +" }";
	}

	@Override public String toString() {
		return "IfThenElse("+ condition +", "+ thenBody +", "+ elseBody +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.condition == null ? 0 : this.condition.hashCode());
		result = result * 31 + (this.thenBody == null ? 0 : this.thenBody.hashCode());
		result = result * 31 + (this.elseBody == null ? 0 : this.elseBody.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		IfThenElse other = (IfThenElse)obj;
		return (this.condition == null ? other.condition == null : this.condition.equals(other.condition))
			&& (this.thenBody == null ? other.thenBody == null : this.thenBody.equals(other.thenBody))
			&& (this.elseBody == null ? other.elseBody == null : this.elseBody.equals(other.elseBody));
	}

	public static IfThenElse generate(Random random, int min, int max) {
		Exp condition; Stmt thenBody; Stmt elseBody; 
		condition = Exp.generate(random, min-1, max-1);
		thenBody = Stmt.generate(random, min-1, max-1);
		elseBody = Stmt.generate(random, min-1, max-1);
		return new IfThenElse(condition, thenBody, elseBody);
	}
	
	@Override public State evaluate(State state) throws Exception{
		Object value = condition.evaluate(state);
		if((Boolean)value){
			return thenBody.evaluate(state);
		}else{
			return elseBody.evaluate(state);
		}
	}
	
	@Override
	public CheckState check(CheckState s) throws Exception{
		//Obtenemos el tipo de la condición.
		Tipo tipoCondcion = condition.check(s);
		
		if(tipoCondcion != Tipo.Booleano){
			s.errores.add("La condición del IfThenElse no es del tipo esperado, " + tipoCondcion);
		}
		
		CheckState thenBodyState = (CheckState)s.clone();
		CheckState elseBodyState = (CheckState)s.clone();
				
		//Obtenemos el estado resultante de le intersección entre el cuerpo del ThenBody y ElseBody.
		CheckState resultState = CheckState.intersect(thenBody.check(thenBodyState), 
													elseBody.check(elseBodyState));
		
		//Retornamos la intersección entre el estado Actual y el estado resultante de la
		//intersección entre el cuerpo ThenBody y ElseBody.
		return s.intersectWith(resultState);
	}
}
