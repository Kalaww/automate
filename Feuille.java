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

	public Map<Feuille, Set<Feuille>> succ(){
		return new HashMap<Feuille, Set<Feuille>>();
	}
	
	@Override
	public String toString(){
		return ""+symbole;
	}
}
