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
	
	/**
	 * Une copie du noeud unaire
	 * @return la copie
	 */
	public Unaire copy(){
		return new Unaire(this.fils.copy());
	}
	
	/**
	 * Residuel du noeud Unaire par la lettre passée en argument
	 * @param c lettre du residuel
	 * @return le langage résiduel
	 */
	public Arbre residuelBis(char c){
		if(premiers.contains(new Feuille(c))) return new Binaire(Arbre.SYMBOLE_CONCAT, fils.residuelBis(c), this.copy());
		return this.copy();
	}
	
	/**
	 * Simplification des feuilles mot vide
	 * @return l'arbre sans les mots vides
	 */
	public Arbre simplification(){
		fils = fils.simplification();
		if(fils.symbole == Arbre.MOT_VIDE) return new Feuille(Arbre.MOT_VIDE);
		return this;
	}
	
	/**
	 * Recupère un ensemble de l'alphabet de l'arbre
	 * @return ensemble des lettres
	 */
	public Set<Character> alphabet(){
		return fils.alphabet();
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
		return "{"+fils.toString()+"}"+symbole;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			final Unaire other = (Unaire) obj;
			return (symbole == other.symbole) && fils.equals(other.fils);
		}
	}
	
	@Override
	public int hashCode() {
		return (int) fils.hashCode();
	}
}
