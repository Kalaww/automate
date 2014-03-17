import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Noeud d'une expression rationnelle pour mettre une expression rationnelle en etoile
 */
public class Unaire extends Arbre {

	/**
	 * Sous arbre du noeud
	 */
	public Arbre fils;
	
	/**
	 * Initialise le noeud unaire
	 */
	public Unaire(){
		this.symbole = '*';
		this.fils = null;
	}

	/**
	 * Initialise l'arbre unaire en lui ajoutant un fils
	 * @param fils fils à ajouter
	 */
	public Unaire(Arbre fils){
		this();
		this.fils = fils;
		this.contientMotVide = true;
		this.premiers = new HashSet<Feuille>();
		this.premiers.addAll(fils.premiers);
		this.derniers = new HashSet<Feuille>();
		this.derniers.addAll(fils.derniers);
	}
	
	public Map<Feuille, Set<Feuille>> succ(){
		HashMap<Feuille, Set<Feuille>> map = new HashMap<Feuille, Set<Feuille>>();
		map.putAll(fils.succ());
		for(Feuille feuilleD : derniers){
			for(Feuille feuilleP : premiers){
				if(map.containsKey(feuilleD)){
					map.get(feuilleD).add(feuilleP);
				}else{
					Set<Feuille> tmp = new HashSet<Feuille>();
					tmp.add(feuilleP);
					map.put(feuilleD, tmp);
				}
			}
		}
		return map;	
	}

	@Override
	public String toString(){
		return fils.toString()+symbole;
	}
}
