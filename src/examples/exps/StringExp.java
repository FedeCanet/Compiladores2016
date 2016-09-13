package examples.exps;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import examples.Exp;

/** Representación de constantes Strings.
*/
public class StringExp extends Exp {
	public final String literal;

	public StringExp(String id) {
		this.literal = id;
	}

	@Override public String unparse() {
		return literal;
	}

	@Override public String toString() {
		return "String("+ literal +")";
	}

	@Override public int hashCode() {
		int result = 1;
		result = result * 31 + (this.literal == null ? 0 : this.literal.hashCode());
		return result;
	}

	@Override public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		StringExp other = (StringExp)obj;
		return (this.literal == null ? other.literal == null : this.literal.equals(other.literal));
	}

	public static StringExp generate(Random random, int min, int max) {
		String id; 
		id = ""+"abcdefghijklmnopqrstuvwxyz".charAt(random.nextInt(26));
		return new StringExp(id);
	}
	
	@Override public Object evaluate(State state) throws Exception{
		return literal;
	}
	
	@Override public Tipo check(CheckState s) {
		return Tipo.Cadena;
	}
}
