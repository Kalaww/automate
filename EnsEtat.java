
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

/**
 * Ensemble d'Etat unique
 * @see Etat
 */
public class EnsEtat extends HashSet<Etat> {
	
	/**
	 * Map memoire lors d'un determinisation d'un automate
	 */
	protected HashMap<EnsEtat, Etat> mapDeterminise;
	
	/**
	 * Map memoire lors d'une union/intersectiond d'un automate
	 */
	protected HashMap<Etat[], Etat> mapUnion;

	/**
	 * Ensemble d'etat vide
	 */
	public EnsEtat() {
		super();
		mapDeterminise = null;
		mapUnion = null;
	}

	/**
	 * Ensemble d'etat successeur de par la lettre en parametre
	 * @param c lettre de la transition
	 * @return l'ensemble d'etat atteignable par la lettre
	 */
	public EnsEtat succ(char c){
		EnsEtat a = new EnsEtat();
		for(Etat etat : this){
			EnsEtat tmp = etat.succ(c);
			a.addAll(tmp);
		}
		return a;
	}

	/**
	 * Ensemble d'etat successeur sur tout l'alphabet
	 * @return l'ensemble d'etat atteignable sur tout l'alphabet
	 */
	public EnsEtat succ(){
		EnsEtat a = new EnsEtat();
		for(Etat etat : this){
			EnsEtat sorties = etat.succ();
			a.addAll(sorties);
		}
		return a;
	}

	/**
	 * Test si l'ensemble d'etat contient un etat terminal
	 * @return resultat du test
	 */
	public boolean contientTerminal(){
		for(Etat etat : this){
			if(etat.isTerm()) return true;
		}
		return false;
	}
	
	/**
	 * Test si l'ensemble d'etat accepte le sous mot demarrant a la position i
	 * @param s mot a tester
	 * @param i position de depart
	 * @return resultat du test
	 */
	public boolean accepte(String s, int i){
		if(i == s.length()) return this.contientTerminal();
		if(this.succ(s.charAt(i)) != null){
			return this.succ(s.charAt(i)).accepte(s, ++i);
		}
		return false;
	}

	/**
	 * Recupere un ensemble des lettre du langage de l'ensemble d'etat
	 * @return ensemble de lettre
	 */
	public Set<Character> alphabet(){
		Set<Character> a = new HashSet<Character>();
		for(Etat etat : this){
			for(Character c : etat.alphabet()){
				a.add(c);
			}
		}
		return a;
	}

	/**
	 * Test si deux ensembles d'etats ont les memes etats
	 * @param e l'ensemble d'etat auquel comparer this
	 * @return resultat du test
	 */
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
	public boolean equals(Object obj){
		if (obj == null || getClass() != obj.getClass()){
			return false;
		}else{
			final EnsEtat other = (EnsEtat) obj;
			if(this.isEmpty() && other.isEmpty()) return null;
			for(Etat etat : this){
				if(e.getEtat(etat.hashCode()) == null) return false;
			}
	
			for(Etat etat : e){
				if(this.getEtat(etat.hashCode()) == null) return false;
			}
			return true;
		}
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
	
	/**
	 * Representation alternative d'un ensemble d'etats
	 * @return une chaine de caractere d'un ensemble d'etats
	 */
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

	/**
	 * Representation du contenu de l'ensemble d'etats
	 * @return une chaine de caracteres
	 */
	public String listEtats(){
		String res = "(";
		for(Etat etat : this){
			res += etat.hashCode()+",";
		}
		res = res.substring(0, res.length()-1);
		return res+")";
	}

	/**
	 * Recupere l'etat avec l'id en parametre, null s'il n'y est pas
	 * @param id l'id de l'etat a chercher
	 * @return l'etat
	 */
	public Etat getEtat(int id){
		for(Etat etat : this){
			if(etat.hashCode() == id){
				return etat;
			}
		}
		return null;
	}

	/**
	 * Memorisation du mappage de la determinisation
	 * @param e mappage utilisé
	 */
	public void setMapDeterminise(HashMap<EnsEtat, Etat> e){
		this.mapDeterminise = e;
	}

	/**
	 * Memorisation du mappage de l'union/intersection
	 * @param e mappage utilisé
	 */
	public void setMapUnion(HashMap<Etat[], Etat> e){
		this.mapUnion = e;
	}
}
