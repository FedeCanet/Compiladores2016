package semantica;

public class VarInfo {

	public enum Tipo{
		Cadena,
		Numerico,
		Booleano,
		Entero
	}
	
	//Variables
	public boolean inicializado = false;
	public Tipo t = null;
	
	public VarInfo(Tipo _t, boolean _inicializado){
		t = _t;
		inicializado = _inicializado;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "VarInfo [inicializado=" + inicializado + ", t=" + t + "]";
	}
	
	
}
