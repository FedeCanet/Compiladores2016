package examples.exps;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

/** Representación de constantes numéricas o numerales.
*/
public class Numeral extends Exp {
	public final Double number;

	public Numeral(Double number) {
		this.number = number;
	}

	@Override public String unparse() {
		return number.toString();
	}

	@Override public String toString() {
		return "Numeral("+ number +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.number == null ? 0 : this.number.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Numeral other = (Numeral)obj;
		return (this.number == null ? other.number == null : this.number.equals(other.number));
	}

	public static Numeral generate(Random random, int min, int max) {
		Double number; 
		number = Math.round(random.nextDouble() * 1000) / 100.0;
		return new Numeral(number);
	}
	
	@Override public Object evaluate(State state) throws Exception{
		return number;
	}
	
	@Override public Tipo check(CheckState s){			
		return Tipo.Numerico;
	}
}
