package examples.exps;
import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

/** RepresentaciÃ³n de sumas.
*/
public class Addition extends Exp {
	public final Exp left;
	public final Exp right;

	public Addition(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" + "+ right.unparse() +")";
	}

	@Override public String toString() {
		return "Addition("+ left +", "+ right +")";
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
		Addition other = (Addition)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static Addition generate(Random random, int min, int max) {
		Exp left; Exp right; 
		left = Exp.generate(random, min-1, max-1);
		right = Exp.generate(random, min-1, max-1);
		return new Addition(left, right);
	}
	
	@Override public Object evaluate(State state) throws Exception{
		Object value1 = left.evaluate(state);
		Object value2 = right.evaluate(state);
		
		if (value1 instanceof Integer && value2 instanceof Integer)  {
			return (Integer)value1 + (Integer)value2;	
		}
		
		if (value1 instanceof Double && value2 instanceof Double)  {
			return (Double)value1 + (Double)value2;	
		}
		//Si la suma no se puede hacer se lanza un msj de error.
		return lanzarError("La evaluación de la Addition no se puede hacer debido a que los operandos no son del tipo requerido, estos son " + value1 + " y " + value2);
	}
	
	@Override public Tipo check(CheckState s){		
		//Obtenemos los tipos de left y right.
		Tipo tipoLeft = left.check(s);
		Tipo tipoRight = right.check(s);
		
		//La suma se realiza solamente si los tipos son númericos.
		if(tipoLeft == Tipo.Numerico && tipoRight == Tipo.Numerico){
			return tipoLeft;
		}else{
			s.errores.add("Los operandos de la suma son de tipos distintos " + tipoLeft + " y " + tipoRight);
			return Tipo.Numerico;//Asumimos el tipo númerico para que pueda continuar con el chequeo de tipos.
		}
	}
}
