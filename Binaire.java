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
		if(symbole == Arbre.SYMBOLE_CONCAT) return gauche.toString()+symbole+droit.toString();
		else return "("+gauche.toString()+symbole+droit.toString()+")";
	}
}
