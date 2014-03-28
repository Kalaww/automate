import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

/**
 * Feuille contenant un symbole de l'expression rationelle
 */
public class Feuille extends Arbre{

	/**
	 * Initialise la feuille sur le symbole
	 * @param symbole symbole de la feuille
	 */
	public Feuille(char symbole){
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
	 * Residuel de la feuille par la lettre passée en argument
	 * @param c lettre du residuel
	 * @return le langage résiduel
	 */
	public Arbre residuelBis(char c){
		if(symbole == c) return new Feuille(Arbre.MOT_VIDE);
		else return this.copy();
	}
	
	/**
	 * Simplification des feuilles mot vide
	 * @return l'arbre sans les mots vides
	 */
	public Arbre simplification(){
		return this;
	}

	public Map<Feuille, Set<Feuille>> succ(){
		return new HashMap<Feuille, Set<Feuille>>();
	}
	
	@Override
	public String toString(){
		return ""+symbole;
	}
}
