package semantica;

import java.util.ArrayList;
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
	
	public static CheckState intersect(final CheckState first, final CheckState second) {
		CheckState result = new CheckState();
		for (String id : first.varInfos.keySet()) {
			VarInfo firstEntry = first.varInfos.get(id);
			VarInfo secondEntry = second.varInfos.get(id);
			
			boolean sameType = firstEntry.t.equals(secondEntry.t);
			boolean bothDefined = ((Boolean)firstEntry.inicializado).equals(secondEntry.inicializado);
			
			if (sameType && bothDefined) {
				result.varInfos.put(id, firstEntry);
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
		return "CheckState [varInfos=" + varInfos + "]\r\n" + 
				"Errores=" + printErrores();
	}
	
	public String printErrores(){
		StringBuilder sBuilder = new StringBuilder();
		for (String string : errores) {
			sBuilder.append(string + "\r\n");
		}
		return sBuilder.toString();
	}
}
