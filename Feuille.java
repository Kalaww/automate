import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * Feuille contenant un symbole de l'expression rationelle
 */
public class Feuille extends Arbre{

	/**
	 * Initialise la feuille sur le symbole
	 * @param symbole symbole de la feuille
	 */
	public Feuille(char symbole){
		this.id = Arbre.ID_COMPT++;
		this.symbole = symbole;
		this.contientMotVide = symbole == Arbre.MOT_VIDE;
		this.premiers = new HashSet<Feuille>();
		if(!this.contientMotVide) this.premiers.add(this);
		this.derniers = new HashSet<Feuille>();
		if(!this.contientMotVide) this.derniers.add(this);
	}
	
	/**
	 * Une copie de la feuille
	 * @return la copie
	 */
	public Feuille copy(){
		return new Feuille(this.symbole);
	}
	
	/**
	 * Test si l'arbre est égale a celui passé en argument
	 * @param a arbre à comparer
	 * @return resultat du test
	 */
	public boolean egale(Arbre a){
		return symbole == a.symbole;
	}
	
	/**
	 * Residuel de la feuille par la lettre passée en argument
	 * @param c lettre du residuel
	 * @return le langage résiduel
	 */
	public Arbre residuelBis(char c){
		if(symbole == c) return new Feuille(Arbre.MOT_VIDE);
		else return null;
	}
	
	/**
	 * Simplification des feuilles mot vide
	 * @return l'arbre sans les mots vides
	 */
	public Arbre simplification(){
		return this;
	}
	
	/**
	 * Recupère un ensemble de l'alphabet de l'arbre
	 * @return ensemble des lettres
	 */
	public Set<Character> alphabet(){
		Set<Character> a = new HashSet<Character>();
		if(symbole != Arbre.MOT_VIDE) a.add(symbole);
		return a;
	}

	public Map<Feuille, Set<Feuille>> succ(){
		return new HashMap<Feuille, Set<Feuille>>();
	}
	
	@Override
	public String toString(){
		return ""+symbole;
	}
	
	@Override
	public boolean equals(Object obj){
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			final Feuille other = (Feuille) obj;
			return (id == other.id);
		}
	}
	
	@Override
	public int hashCode() {
		return (int) id;
	}
}
