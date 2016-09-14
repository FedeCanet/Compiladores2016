package semantica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import semantica.VarInfo.Tipo;

public class CheckState {

	private Map<String, VarInfo> varInfos = new HashMap<String, VarInfo>();
	public ArrayList<String> errores = new ArrayList<String>();
	
	public CheckState(Map<String, VarInfo> _varInfos, ArrayList<String> _errores){
		this.varInfos = _varInfos;
		this.errores = _errores;
	}
	
	public CheckState(){}
	
	public VarInfo getVarInfos(String id){
	 	if (varInfos.containsKey(id)) {
			return varInfos.get(id);
		} else {
			//La variable no esta definida debemos continuar igual?
			errores.add("La variable " + id + " no esta declarada");
			return create(id, new VarInfo(Tipo.Numerico, true));
		}	
	}
	
	public VarInfo create(String var, VarInfo varInf) {
		varInfos.put(var, varInf);
		return varInf;
	}
	
	public void create(String var, Tipo t, boolean _inicializada) {
		varInfos.put(var, new VarInfo(t, _inicializada));
	}
	
	public boolean exsist(String id){
		return varInfos.get(id) != null;
	}
	
	public static CheckState intersect(final CheckState first, final CheckState second) {
		CheckState result = new CheckState();
		ArrayList<String> erroresResult = new ArrayList<String>();
		for (String id : first.varInfos.keySet()) {
			VarInfo firstEntry = first.varInfos.get(id);
			VarInfo secondEntry = second.varInfos.get(id);
			
			boolean sameType = firstEntry.t.equals(secondEntry.t);
			boolean bothDefined = ((Boolean)firstEntry.inicializado).equals(secondEntry.inicializado);
			
			if (sameType && bothDefined) {
				result.varInfos.put(id, firstEntry);
			}
		}
		
		//Agregamos los errores.
		result.errores = first.errores;
		for (String errorsSecond : second.errores) {
			if(!result.errores.contains(errorsSecond)){
				result.errores.add(errorsSecond);
			}
		}
		
		return result;
	}
	
	public CheckState intersectWith(final CheckState other) {
		return CheckState.intersect(this, other);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		
		CheckState copyState = new CheckState();
		
		ArrayList<String> errorCloned = (ArrayList<String>)this.errores.clone();
		
		for (String id : this.varInfos.keySet()) {
			VarInfo firstEntry = this.varInfos.get(id);
			VarInfo Cloned = new VarInfo(firstEntry.t, firstEntry.inicializado);
			copyState.create(id, Cloned);
		}
		copyState.errores = errorCloned;
		
		return copyState;
		//return super.clone();
	}
	
	@Override
	public String toString() {
		int size = varInfos.size();
		//Obtenemos las llaves.
		String[] keys = varInfos.keySet().toArray(new String[size]);
		//Ordenamos las claves.
		Arrays.sort(keys);
		//Construimos la salida
		StringBuilder buffer = new StringBuilder("{ ");
		if (size > 0) {
			buffer.append(keys[0] + "=" + varInfos.get(keys[0]).t);
			for (int i = 1; i < size; i++) {
				buffer.append(", ").append(keys[i] + "=" + varInfos.get(keys[i]).t);
			}
		}
		
		buffer.append(" }");
		buffer.append("\r\n");
		buffer.append("Errores:");
		buffer.append("\r\n");
		return buffer.append(printErrores()).toString();
	}
		
	public String printTipos() {
		int size = varInfos.size();
		//Obtenemos las llaves.
		String[] keys = varInfos.keySet().toArray(new String[size]);
		//Ordenamos las claves.
		Arrays.sort(keys);
		//Construimos la salida
		StringBuilder buffer = new StringBuilder("{ ");
		if (size > 0) {
			buffer.append(keys[0] + "=" + varInfos.get(keys[0]).t);
			for (int i = 1; i < size; i++) {
				buffer.append(", ").append(keys[i] + "=" + varInfos.get(keys[i]).t);
			}
		}
		return buffer.append(" }").toString();
	}
	
	public String printErrores(){
		StringBuilder sBuilder = new StringBuilder();
		for (String string : errores) {
			sBuilder.append(string + "\r\n");
		}
		return sBuilder.toString();
	}
}
