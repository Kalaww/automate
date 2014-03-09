import java.util.Stack;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public abstract class Arbre {

	public static final char SYMBOLE_CONCAT = '.';
	public static final char SYMBOLE_OU = '+';
	public static final char MOT_VIDE = '1';

	public char symbole;

	public boolean contientMotVide;

	public Set<Feuille> premiers;

	public Set<Feuille> derniers;

	public abstract Map<Feuille, Set<Feuille>> succ();

	public static Arbre lirePostfixe(String expr){
		Stack<Arbre> pile = new Stack<Arbre>();
		
		for(int i = 0; i < expr.length(); i++){
			char valeur = expr.charAt(i);
			if(valeur == '*'){
				Arbre element = pile.pop();
				Unaire unaire = new Unaire(element);
				pile.push(unaire);
			}else if(valeur == SYMBOLE_CONCAT || valeur == SYMBOLE_OU){
				Arbre element1 = pile.pop();
				Arbre element2 = pile.pop();
				Binaire binaire = new Binaire(valeur, element2, element1);
				pile.push(binaire);
			}else{
				Feuille f = new Feuille(valeur);
				pile.push(f);
			}
		}

		return pile.pop();
	}
}
