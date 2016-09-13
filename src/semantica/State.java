package semantica;

import java.util.*;

/** Representación de los estados de las variables.
*/
public class State {
	
	private final Map<String, Object> variables = new HashMap<String, Object>();
		
	public Object getVariable(String id) {
		return variables.get(id);		
	}
	
	public void create(String var, Object value) {
		variables.put(var, value);
	}
	
	public String toString() {
		int size = variables.size();
		//Obtenemos las llaves.
		String[] keys = variables.keySet().toArray(new String[size]);
		//Ordenamos las claves.
		Arrays.sort(keys);
		//Construimos la salida
		StringBuilder buffer = new StringBuilder("{ ");
		if (size > 0) {
			buffer.append(keys[0] + "=" + variables.get(keys[0]));
			for (int i = 1; i < size; i++) {
				buffer.append(", ").append(keys[i] + "=" + variables.get(keys[i]));
			}
		}
		return buffer.append(" }").toString();
	}
	
	public boolean exsist(String id){
		return variables.get(id) != null;
	}
}
