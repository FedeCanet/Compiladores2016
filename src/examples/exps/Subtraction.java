package examples.exps;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

/** Representación de restas.
*/
public class Subtraction extends Exp {
	public final Exp left;
	public final Exp right;

	public Subtraction(Exp left, Exp right) {
		this.left = left;
		this.right = right;
	}

	@Override public String unparse() {
		return "("+ left.unparse() +" - "+ right.unparse() +")";
	}

	@Override public String toString() {
		return "Subtraction("+ left +", "+ right +")";
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
		Subtraction other = (Subtraction)obj;
		return (this.left == null ? other.left == null : this.left.equals(other.left))
			&& (this.right == null ? other.right == null : this.right.equals(other.right));
	}

	public static Subtraction generate(Random random, int min, int max) {
		Exp left; Exp right; 
		left = Exp.generate(random, min-1, max-1);
		right = Exp.generate(random, min-1, max-1);
		return new Subtraction(left, right);
	}
	
	@Override public Object evaluate(State state) throws Exception{
		Object value1 = left.evaluate(state);
		Object value2 = right.evaluate(state);
		
		if (value1 instanceof Double && value2 instanceof Double)  {
			return (Double)value1 - (Double)value2;	
		}
		return lanzarError("La evaluaci�n de la Substraction no puede realizarse debido a que los operandos no son del tipo requerido, estos son " + value1 + " y " + value2);
	}
	
	@Override public Tipo check(CheckState s){		
		//Obtenemos los tipos.
		Tipo tipo1 = left.check(s);
		Tipo tipo2 = right.check(s);
		
		if(tipo1 == Tipo.Entero && tipo2 == Tipo.Entero){
			return Tipo.Entero;
		}else if(Tipo.Numerico.isComatible(tipo1) && Tipo.Numerico.isComatible(tipo2)){
			return Tipo.Numerico;
		}else{
			s.errores.add("Los operandos de la substracci�n son de tipos distintos " + tipo1 + " y " + tipo2);
			return Tipo.Numerico;//Asumimos para poder continuar con el chequeo de tipos.
		}
	}
}
