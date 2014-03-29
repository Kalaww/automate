import java.util.Stack;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.ArrayList;

/**
 * Base de chaque noeud d'un arbre d'une expression rationnelle
 */
public abstract class Arbre {

	/**
	 * Symbole contatenation
	 */
	public static final char SYMBOLE_CONCAT = '.';
	
	/**
	 * Symbole ou
	 */
	public static final char SYMBOLE_OU = '+';
	
	/**
	 * Symbole du mot vide
	 */
	public static final char MOT_VIDE = '1';
	
	/**
	 * Compteur des identifiants de chaque instance d'un noeud
	 */
	public static int ID_COMPT = 0;
	
	/**
	 * Identifiant unique d'un noeud
	 */
	public int id;

	/**
	 * Symbole du noeud
	 */
	public char symbole;

	/**
	 * Vrai si le sous arbre du noeud contient le mot vide
	 */
	public boolean contientMotVide;

	/**
	 * Premiers symboles du sous arbre du noeud
	 */
	public Set<Feuille> premiers;

	/**
	 * Derniers symboles du sous arbre du noeud
	 */
	public Set<Feuille> derniers;
	
	/**
	 * Mappage des résiduels
	 */
	public HashMap<Character, Arbre> residuels;
	
	/**
	 * Vrai si cet arbre est initiale comme résiduel
	 */
	public boolean estResiduelInit = false;
	
	/**
	 * Vrai si cet arbre est acceptant comme résiduel
	 */
	public boolean estResiduelTerm = false;

	/**
	 * Successeurs (transition) du sous arbre du noeud
	 * @return le HashMap des successeurs
	 */
	public abstract Map<Feuille, Set<Feuille>> succ();
	
	/**
	 * Une copie de l'arbre
	 * @return la copie
	 */
	public abstract Arbre copy();
	
	/**
	 * Test si l'arbre est égale a celui passé en argument
	 * @param a arbre à comparer
	 * @return resultat du test
	 */
	public abstract boolean egale(Arbre a);
	
	/**
	 * Récupère toutes les feuilles de l'arbre dans une liste
	 * @return liste contenant les feuilles
	 */
	public abstract ArrayList<Feuille> getFeuilles();
	
	/**
	 * Residuel de l'arbre par la lettre passée en argument
	 * @param c lettre du residuel
	 * @return le langage résiduel
	 */
	public abstract Arbre residuelBis(char c);
	
	/**
	 * Residuel de l'arbre par la lettre passée en argument et simplification des mots vides de l'arbre
	 * @param c lettre du residuel
	 * @return le langage résiduel
	 */
	public Arbre residuel(char c){
		Arbre a = this.residuelBis(c);
		boolean b = false;
		if(a.symbole == '+'){
			Binaire tmp = (Binaire) a;
			b = tmp.droit.symbole == Arbre.MOT_VIDE || tmp.gauche.symbole == Arbre.MOT_VIDE;
		}
		a = a.simplification();
		a.estResiduelTerm = b;
		return a;
	}
	
	/**
	 * Simplification des feuilles mot vide
	 * @return l'arbre sans les mots vides
	 */
	public abstract Arbre simplification();
	
	/**
	 * Recupère un ensemble de l'alphabet de l'arbre
	 * @return ensemble des lettres
	 */
	public abstract Set<Character> alphabet();
	
	/*
	public static ArrayList<Feuille> getSuccesseur(Arbre a, Feuille f){
		ArrayList<Feuille> res = new ArrayList<Feuille>();
		ArrayList<Feuille> feuilles = a.getFeuilles();
		
		for(Feuille courant : feuilles){
			
	}*/

	/**
	 * Conversion d'une expression rationnelle ecrite en lecture postfixe en arbre
	 * @param expr expression rationnelle
	 * @return l'arbre de l'expression rationnelle
	 */
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
	
	/**
	 * Stocke tous les résiduels de l'arbre en argument dans une liste
	 * @param a arbre dont il faut les résisduels
	 * @return liste des résiduels
	 */
	public static ArrayList<Arbre> residuels(Arbre a){
		ArrayList<Arbre> all = new ArrayList<Arbre>();
		Stack<Arbre> pile = new Stack<Arbre>();
		Set<Character> alphabet = a.alphabet();
		
		pile.push(a);
		a.estResiduelInit = true;
		
		while(!pile.empty()){
			Arbre depile = pile.pop();
			depile.residuels = new HashMap<Character, Arbre>();
			for(Character c : alphabet){
				Arbre res = depile.residuel(c.charValue());
				if(res.symbole != Arbre.MOT_VIDE){
					depile.residuels.put(c, res);
					boolean test = false;
					for(Arbre k : all){
						if(k.egale(res)){
							test = true;
							break;
						}
					}
					if(!test) pile.push(res);
				}
			}
			all.add(depile);
		}
		return all;
	}
}
