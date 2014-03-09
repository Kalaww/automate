
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class EnsEtat extends HashSet<Etat> {

	protected HashMap<EnsEtat, Etat> mapDeterminise;

	protected HashMap<Etat[], Etat> mapUnion;

	public EnsEtat() {
		super();
		mapDeterminise = null;
		mapUnion = null;
	}

	public EnsEtat succ(char c){
		EnsEtat a = new EnsEtat();
		for(Etat etat : this){
			EnsEtat tmp = etat.succ(c);
			a.addAll(tmp);
		}
		return a;
	}

	public EnsEtat succ(){
		EnsEtat a = new EnsEtat();
		for(Etat etat : this){
			EnsEtat sorties = etat.succ();
			a.addAll(sorties);
		}
		return a;
	}

	public boolean contientTerminal(){
		for(Etat etat : this){
			if(etat.isTerm()) return true;
		}
		return false;
	}

	public boolean accepte(String s){
		for(Etat etat : this){
			if(etat.accepte(s, 0)) return true;
		}
		return false;
	}
	
	public boolean accepte(String s, int i){
		if(i == s.length()) return this.contientTerminal();
		if(this.succ(s.charAt(i)) != null){
			return this.succ(s.charAt(i)).accepte(s, ++i);
		}
		return false;
	}

	public Set<Character> alphabet(){
		Set<Character> a = new HashSet<Character>();
		for(Etat etat : this){
			for(Character c : etat.alphabet()){
				a.add(c);
			}
		}
		return a;
	}

	public boolean egale(EnsEtat e){
		for(Etat etat : this){
			if(e.getEtat(etat.hashCode()) == null) return false;
		}
		
		for(Etat etat : e){
			if(this.getEtat(etat.hashCode()) == null) return false;
		}
		return true;
	}

	@Override
	public String toString(){
		String res = "";
		res += this.size() + " Etats\n";
		for(Etat etat : this){
			res += "\n"+etat.toString();
		}
		return res;
	}
	
	public String affiche(){
		String res = "";
		
		if(this.mapDeterminise != null){
			res += "Map determinisation :\n";
			Set<EnsEtat> listEns = mapDeterminise.keySet();
			for(EnsEtat ens : listEns){
				res += "["+ens.listEtats()+"="+mapDeterminise.get(ens).hashCode()+"]";
			}
			res += "\n";	
		}

		if(this.mapUnion != null){
			res += "Map union :\n";
			for(Map.Entry<Etat[], Etat> entry : mapUnion.entrySet()){
				String b = (entry.getKey()[0] != null)? entry.getKey()[0].hashCode()+"" : "n";
				String a = (entry.getKey()[1] != null)? entry.getKey()[1].hashCode()+"" : "n";
				res += "[("+b+","+a+")="+entry.getValue().hashCode()+"]";
			}
			res += "\n";
		}
		
		for(Etat etat : this) res += etat.affiche()+"\n";
		return res;
	}

	public String listEtats(){
		String res = "(";
		for(Etat etat : this){
			res += etat.hashCode()+",";
		}
		res = res.substring(0, res.length()-1);
		return res+")";
	}

	public Etat getEtat(int id){
		for(Etat etat : this){
			if(etat.hashCode() == id){
				return etat;
			}
		}
		return null;
	}

	public void setMapDeterminise(HashMap<EnsEtat, Etat> e){
		this.mapDeterminise = e;
	}

	public void setMapUnion(HashMap<Etat[], Etat> e){
		this.mapUnion = e;
	}
}
