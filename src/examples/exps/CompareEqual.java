package examples.exps;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

/** RepresentaciÃ³n de las comparaciones por igual.
*/
public class CompareEqual extends Exp {
	public final Exp left;
	public final Exp right;

	public CompareEqual(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" == "+ right.unparse() +")";
	}

	@Override public String toString() {
		return "CompareEqual("+ left +", "+ right +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.left == null ? 0 : this.left.hashCode());
		result = result * 31 + (this.right == null ? 0 : this.right.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		CompareEqual other = (CompareEqual)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static CompareEqual generate(Random random, int min, int max) {
		Exp left; Exp right; 
		left = Exp.generate(random, min-1, max-1);
		right = Exp.generate(random, min-1, max-1);
		return new CompareEqual(left, right);
	}
	
	@Override public Object evaluate(State state) throws Exception{
		Object value1 = left.evaluate(state);
		Object value2 = right.evaluate(state);
		
		if (value1 instanceof Boolean && value2 instanceof Boolean)  {
			return (Boolean)value1 & (Boolean)value2;	
		}
		if (value1 instanceof Double && value2 instanceof Double)  {
			return ((Double)value1).compareTo(((Double)value2)) == 0;
		}
		if (value1 instanceof String && value2 instanceof String)  {
			return ((String)value1).equals((String)value2);	
		}
		return lanzarError("La evaluación del CompareEqual no se puede realizar debido a que los operandos no son del mismo tipoe.");
	}
	
	@Override public Tipo check(CheckState s){		
		//Obtenemos los tipos de left y right
		Tipo tipo1 = left.check(s);
		Tipo tipo2 = right.check(s);
		
		//Comparamos que sean del mismo tipo.
		if(tipo1 == tipo2){
			return Tipo.Booleano;
		}else{
			s.errores.add("Los operandos del CompareEqual son de tipos distintos " + tipo1 + " y " + tipo2);
			return Tipo.Booleano;
		}
	}
}
