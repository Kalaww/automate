import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Noeud d'une expression rationnelle pour la concatenation ou "ou"
 */
public class Binaire extends Arbre {

	/**
	 * Sous arbre gauche
	 */
	public Arbre gauche;
	
	/**
	 * Sous arbre droit
	 */
	public Arbre droit;

	/**
	 * Initialise le noeud binaire avec ces sous arbres et son symbole
	 * @param symbole symbole + ou .
	 * @param gauche sous arbre gauche
	 * @param droit sous arbre droit
	 */
	public Binaire(char symbole, Arbre gauche, Arbre droit){
		this.symbole = symbole;
		this.gauche = gauche;
		this.droit = droit;
		this.premiers = new HashSet<Feuille>();
		this.derniers = new HashSet<Feuille>();
		
		if(symbole == Arbre.SYMBOLE_CONCAT){
			this.contientMotVide = gauche.contientMotVide && droit.contientMotVide;
			
			this.premiers.addAll(gauche.premiers);
			if(gauche.contientMotVide) this.premiers.addAll(droit.premiers);
			
			this.derniers.addAll(droit.derniers);
			if(droit.contientMotVide) this.derniers.addAll(gauche.derniers);
		}else{
			this.contientMotVide = gauche.contientMotVide || droit.contientMotVide;
			
			this.premiers.addAll(gauche.premiers);
			this.premiers.addAll(droit.premiers);

			this.derniers.addAll(gauche.derniers);
			this.derniers.addAll(droit.derniers);
		}
	}
	
	/**
	 * Une copie du noeud binaire
	 * @return la copie
	 */
	public Binaire copy(){
		return new Binaire(this.symbole, this.gauche.copy(), this.droit.copy());
	}
	
	/**
	 * Residuel du noeud Binaire par la lettre passée en argument
	 * @param c lettre du residuel
	 * @return le langage résiduel
	 */
	public Arbre residuelBis(char c){
		if(!premiers.contains(new Feuille(c))) return new Feuille(Arbre.MOT_VIDE);
		if(this.symbole == Arbre.SYMBOLE_CONCAT){
			if(gauche.contientMotVide) return new Binaire(Arbre.SYMBOLE_OU, new Binaire(Arbre.SYMBOLE_CONCAT, gauche.residuelBis(c), droit.copy()), droit.residuelBis(c));
			return new Binaire(Arbre.SYMBOLE_CONCAT, gauche.residuelBis(c), droit.copy());
		}else{
			return new Binaire(Arbre.SYMBOLE_OU, gauche.residuelBis(c), droit.residuelBis(c));
		}
	}
	
	/**
	 * Simplification des feuilles mot vide
	 * @return l'arbre sans les mots vides
	 */
	public Arbre simplification(){
		gauche = gauche.simplification();
		droit = droit.simplification();
		if(gauche.symbole == Arbre.MOT_VIDE && droit.symbole == Arbre.MOT_VIDE) return new Feuille(Arbre.MOT_VIDE);
		if(gauche.symbole == Arbre.MOT_VIDE) return droit;
		if(droit.symbole == Arbre.MOT_VIDE) return gauche;
		return this;
	}
	
	/**
	 * Recupère un ensemble de l'alphabet de l'arbre
	 * @return ensemble des lettres
	 */
	public Set<Character> alphabet(){
		Set<Character> a = new HashSet<Character>();
		a.addAll(gauche.alphabet());
		a.addAll(droit.alphabet());
		return a;
	}
	
	public Map<Feuille, Set<Feuille>> succ(){
		HashMap<Feuille, Set<Feuille>> map = new HashMap<Feuille, Set<Feuille>>();
		map.putAll(gauche.succ());
		map.putAll(droit.succ());
		if(symbole == Arbre.SYMBOLE_CONCAT){
			for(Feuille g : gauche.derniers){
				for(Feuille d : droit.premiers){
					if(map.containsKey(g)){
						Set<Feuille> tmp = map.get(g);
						tmp.add(d);
					}else{
						Set<Feuille> tmp = new HashSet<Feuille>();
						tmp.add(d);
						map.put(g, tmp);
					}
				}
			}
		}
		return map;
	}

	@Override
	public String toString(){
		if(symbole == Arbre.SYMBOLE_CONCAT) return "["+gauche.toString()+symbole+droit.toString()+"]";
		else return "("+gauche.toString()+symbole+droit.toString()+")";
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			final Binaire other = (Binaire) obj;
			return (symbole == other.symbole) && gauche.equals(other.gauche) && droit.equals(other.droit);
		}
	}
	
	@Override
	public int hashCode() {
		if(symbole == Arbre.SYMBOLE_CONCAT){
			return (int) (gauche.hashCode() * droit.hashCode());
		}else{
			return (int) (gauche.hashCode() + droit.hashCode());
		}
	}
}
