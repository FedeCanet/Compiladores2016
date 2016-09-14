package examples.exps;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

/** Representaci√≥n de las comparaciones por menor o igual.
*/
public class CompareLessOrEqual extends Exp {
	public final Exp left;
	public final Exp right;

	public CompareLessOrEqual(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" <= "+ right.unparse() +")";
	}

	@Override public String toString() {
		return "CompareLessOrEqual("+ left +", "+ right +")";
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
		CompareLessOrEqual other = (CompareLessOrEqual)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static CompareLessOrEqual generate(Random random, int min, int max) {
		Exp left; Exp right; 
		left = Exp.generate(random, min-1, max-1);
		right = Exp.generate(random, min-1, max-1);
		return new CompareLessOrEqual(left, right);
	}
	
	@Override public Object evaluate(State state) throws Exception{
		Object value1 = left.evaluate(state);
		Object value2 = right.evaluate(state);
		
		if (value1 instanceof Double && value2 instanceof Double)  {
			return (Double)value1 <= (Double)value2;	
		}
		
		return lanzarError("La evaluaciÛn del CompareLessOrEqual no se puede realizar debido a que los operandos no son del mismo tipo.");
	}
	
	@Override
	public Tipo check(CheckState s) {
		//Obtenemos los tipos.
		Tipo tipo1 = left.check(s);
		Tipo tipo2 = right.check(s);
		
		if(tipo1 == Tipo.Numerico && tipo2 == Tipo.Numerico){
			return Tipo.Booleano;
		}else{
			s.errores.add("Los operandos del CompareLessOrEqual no son del tipo esperado, " + tipo1 + " y " + tipo2);
			return Tipo.Booleano;//Asumimos que la comparaciÛn se puede hacer para continuar con el chequeo de tipos.
		}
	}
}
