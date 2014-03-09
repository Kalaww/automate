
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;

public class Etat {

	HashMap<Character, EnsEtat> transitions;
	boolean init;
	boolean term;
	int id;

	public Etat() {
		this.transitions = new HashMap<Character, EnsEtat>();
	}

	public Etat(int id) {
		this.transitions = new HashMap<Character, EnsEtat>();
		this.id = id;
	}

	public Etat(boolean init, boolean term, int id) {
		this.transitions = new HashMap<Character, EnsEtat>();
		this.init = init;
		this.term = term;
		this.id = id;
	}

	public Etat(boolean estInit, boolean estTerm) {
		this.init = estInit;
		this.term = estTerm;
		this.transitions = new HashMap<Character, EnsEtat>();
	}

	public EnsEtat succ(char c){
		if(transitions.containsKey(c)){
			return transitions.get(c);
		}
		return new EnsEtat();
	}

	public EnsEtat succ(){
		if(!transitions.isEmpty()){
			EnsEtat tmp = new EnsEtat();
			for(EnsEtat etats: transitions.values()){
				tmp.addAll(etats);
			}
			return tmp;
		}
		return new EnsEtat();
	}

	public void ajouteTransition(char c, Etat e){
		if(transitions.containsKey(c)){
			EnsEtat tmp = transitions.get(c);
			tmp.add(e);
		}else{
			EnsEtat tmp = new EnsEtat();
			tmp.add(e);
			transitions.put(c, tmp);
		}
	}

	public boolean accepte(String s, int i){
		if(i == s.length() && this.term) return true;
		else if(i == s.length()) return false;

		EnsEtat succ = this.succ(s.charAt(i));
		if(succ == null) return false;

		for(Etat etat : succ){
			if(etat.accepte(s, i+1)) return true;
		}
		return false;
	}

	public Set<Character> alphabet(){
		return transitions.keySet();
	}

	public boolean isInit() {
		return init;
	}

	public boolean isTerm() {
		return term;
	}

	public void setInit(boolean init) {
		this.init = init;
	}

	public void setTerm(boolean term) {
		this.term = term;
	}

	public HashMap<Character, EnsEtat> getTransitions(){
		return transitions;
	}

	@Override
	public int hashCode() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			final Etat other = (Etat) obj;
			return (id == other.id);
		}
	}

	@Override
	public String toString(){
		String res = "";
		res += id+"";
		res += (init)? " initial": "";
		res += (term)? " terminal" : "";
		res += "\n";
		
		for(Map.Entry<Character, EnsEtat> entry : transitions.entrySet()){
			res += entry.getKey().charValue();
			for(Etat etat : entry.getValue()){
				res += " "+etat.id;
			}
			res += "\n";
		}
		return res;
	}
	
	public String affiche(){
		int i = (init)? 1 : 0;
		int t = (term)? 1 : 0;
		String res = "{"+id+" ("+i+","+t+")";

		for(Character a : transitions.keySet()){
			String key = " ["+a+"=>(";
			String etatS = "";
			for(Etat etat : transitions.get(a)){
				if(etatS.length() != 0) etatS += ",";
				etatS += etat.hashCode()+"";
			}
			key += etatS+")]";
			res += key;
		}

		res += " }";
		return res;
	}
}
