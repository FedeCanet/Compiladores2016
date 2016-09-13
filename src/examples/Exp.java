package examples;

import java.util.*;

import semantica.CheckState;
import semantica.State;
import semantica.VarInfo.Tipo;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class Exp {
	
	abstract public String unparse();

	@Override public abstract String toString();

	@Override public abstract int hashCode();

	@Override public abstract boolean equals(Object obj);
	
	public static Exp generate(Random random, int min, int max) {
		throw new NotImplementedException();
	}
	
	public Object lanzarError(String msj) throws Exception{
		throw new Exception(msj);
	}
	
	public abstract Object evaluate(State state) throws Exception;
	
	public abstract Tipo check(CheckState s);
}
