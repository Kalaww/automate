import java.util.Iterator;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Set;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.io.File;

/**
 * Structure d'un Automate fini
 */
public class Automate extends EnsEtat {

	/**
	 * Stocke les etats initiaux
	 */
    private EnsEtat initiaux;

	/**
	 * Automate vide
	 */
    public Automate() {
        super();
        initiaux = new EnsEtat();
    }
    
    /**
	 * Automate auquel on ajoute tous les etats accessibles depuis l'etat en parametre
	 * @param etat l'etat
	 */
    public Automate(Etat etat){
		this();
		this.ajouteEtatRecursif(etat);
	}
	
	/**
	 * Automate a partir d'un arbre d'une expression reguliere
	 * @param arbre l'arbre contenant l'expression reguliere
	 */
	public Automate(Arbre arbre){
		this();
		this.fromArbre(arbre);
	}
    
    /**
	 * Automate a partir de la lecture d'un fichier
	 * @param fichier fichier texte contenant un automate
	 */
    public Automate(String fichier){
    	this();
    	this.readFile(fichier);
    }
    
    /**
     * Automate à partir du calcul des résiduels d'une expressions rationnelle.
     * L'automate est donc minimal
     * @param residuels liste des arbres des résiduels
     */
    public Automate(ArrayList<Arbre> residuels){
		this();
		HashMap<Arbre, Etat> map = new HashMap<Arbre, Etat>();
		Etat courant = null;
		int compteur = 0;
		
		for(Arbre a : residuels){
			boolean test = false;
			for(Arbre k : map.keySet()){
				if(k.egale(a) && k.estResiduelTerm == a.estResiduelTerm){
					test = true;
					break;
				}
			}
			if(!test){
				courant = new Etat(a.estResiduelInit, a.estResiduelTerm, compteur++);
				this.ajouteEtatSeul(courant);
				map.put(a, courant);
			}
		}
		
		for(Arbre a : residuels){
			courant = map.get(a);
			for(Map.Entry<Character, Arbre> entre : a.residuels.entrySet()){
				if(entre.getValue() != null){
					for(Map.Entry<Arbre,Etat> succ: map.entrySet()){
						if(entre.getValue().egale(succ.getKey()) && entre.getValue().estResiduelTerm == succ.getKey().estResiduelTerm){
							courant.ajouteTransition(entre.getKey().charValue(), succ.getValue());
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * Ajoute l'etat a l'automate
	 * @param e etat a ajouter
	 * @return succes de l'ajout
	 */
	public boolean ajouteEtatSeul(Etat e){
		if(!this.add(e)) return false;
		if(e.isInit()) initiaux.add(e);
		return true;
	}

	/**
	 * Ajoute l'etat et ses etats accessibles a l'automate
	 * @param e etat a ajouter et sur lequel appliquer la recursion
	 * @return succes de tous les ajouts
	 */
	public boolean ajouteEtatRecursif(Etat e){
		if(ajouteEtatSeul(e)){
			EnsEtat succ = e.succ();
			if(succ != null){
				for(Etat etat : e.succ()) ajouteEtatRecursif(etat);
			}
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Test si l'automate est deterministe
	 * @return resultat du test
	 */
	public boolean estDeterministe(){
		if(this.isEmpty()) return true;

		if(this.initiaux.size() > 1) return false;
		
		for(Etat e : this){
			for(Character a : e.getTransitions().keySet()){
				if(e.succ(a).size() > 1){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Cree l'union de deux automates
	 * @param a l'automate auquel appliquer l'union avec this
	 * @return l'union des deux automates
	 */
	public Automate union(Automate a){
		return union(a, true);
	}
	
	/**
	 * Cree l'intersection de deux automates
	 * @param a l'automate auquel appliquer l'intersection avec this
	 * @return l'intersection des deux automates
	 */
	public Automate intersection(Automate a){
		return union(a, false);
	}

	/**
	 * Cree l'union ou l'intersection de deux automates
	 * @param a l'automate auquel appliquer l'intersection ou l'union avec this
	 * @param estUnion vrai si on realiser l'union, faux pour l'intersection
	 * @return l'union ou l'intersection des deux automates
	 */
	private Automate union(Automate a, boolean estUnion){
		if(a == null) return null;
		
		Automate union = new Automate();
		Set<Character> alphabet = this.alphabet();
		HashMap<Etat[], Etat> map = new HashMap<Etat[], Etat>();
		Stack<Etat[]> pile = new Stack<Etat[]>();
		ArrayList<Etat[]> listDuoEtat = new ArrayList<Etat[]>();
		int etatCompteur = 0;

		//Unions des etats initiaux
		for(Etat etatB : this.initiaux){
			for(Etat etatA : a.initiaux){
				Etat tmp = new Etat(true, etatB.isTerm() || etatA.isTerm(), etatCompteur++);
				union.ajouteEtatSeul(tmp);
				Etat[] duoTmp = new Etat[2];
				duoTmp[0] = etatB;
				duoTmp[1] = etatA;
				map.put(duoTmp, tmp);
				pile.push(duoTmp);
				listDuoEtat.add(duoTmp);
			}
		}

		while(!pile.empty()){
			Etat[] duoPop = pile.pop();
			Etat etatLie = map.get(duoPop);
			
			for(Character lettre : alphabet){
				ArrayList<Etat[]> duoSuccList = new ArrayList<Etat[]>();
				EnsEtat[] duoPopSucc = new EnsEtat[2];
				duoPopSucc[0] = (duoPop[0] != null)? duoPop[0].succ(lettre.charValue()) : null;
				duoPopSucc[1] = (duoPop[1] != null)? duoPop[1].succ(lettre.charValue()) : null;
				
				if(duoPopSucc[0] == null && duoPopSucc[1] == null) break;
				else if(duoPopSucc[0] == null){
					for(Etat etatTmp : duoPopSucc[1]){
						Etat[] tabTmp = new Etat[2];
						tabTmp[0] = null;
						tabTmp[1] = etatTmp;
						duoSuccList.add(tabTmp);
					}
				}else if(duoPopSucc[1] == null){
					for(Etat etatTmp : duoPopSucc[0]){
						Etat[] tabTmp = new Etat[2];
						tabTmp[0] = etatTmp;
						tabTmp[1] = null;
						duoSuccList.add(tabTmp);
					}
				}else{
					for(Etat etatTmpB : duoPopSucc[0]){
						for(Etat etatTmpA : duoPopSucc[1]){
							Etat[] tabTmp = new Etat[2];
							tabTmp[0] = etatTmpB;
							tabTmp[1] = etatTmpA;
							duoSuccList.add(tabTmp);
						}
					}
				}
				
				//Parcours la liste de tout les duo de succ trouve
				for(Etat[] duoEtat : duoSuccList){
					Etat[] duoRef = null;
					for(Etat[] duoExist : listDuoEtat){
						if(duoEtat[0] == null && duoExist[0] == null && duoEtat[1] == null && duoExist[1] == null
							|| duoEtat[0] == null && duoExist[0] == null && duoEtat[1] != null && duoEtat[1].equals(duoExist[1])
							|| duoEtat[1] == null && duoExist[1] == null && duoEtat[0] != null && duoEtat[0].equals(duoExist[0])
							|| duoEtat[0] != null && duoEtat[1] != null && duoEtat[0].equals(duoExist[0]) && duoEtat[1].equals(duoExist[1])){
							duoRef = duoExist;
							break;
						}
					}

					if(duoRef == null){
						boolean term = (estUnion)? ((duoEtat[0] != null)? duoEtat[0].isTerm() : false) || ((duoEtat[1] != null)? duoEtat[1].isTerm() : false) : ((duoEtat[0] != null)? duoEtat[0].isTerm() : false) && ((duoEtat[1] != null)? duoEtat[1].isTerm() : false);
						Etat nouvelEtat = new Etat(false, term, etatCompteur++);
						union.ajouteEtatSeul(nouvelEtat);
						map.put(duoEtat, nouvelEtat);
						pile.push(duoEtat);
						listDuoEtat.add(duoEtat);
						etatLie.ajouteTransition(lettre.charValue(), nouvelEtat);
					}else{
						etatLie.ajouteTransition(lettre.charValue(), map.get(duoRef));
					}
				}
			}
		}
		union.setMapUnion(map);
		return union;
	}

	/**
	 * Cree l'automate determinise
	 * @return l'automate determinise
	 */
	public Automate determinise(){
		if(this.estDeterministe()) return this;
		Automate det = new Automate();
		HashMap<EnsEtat, Etat> map = new HashMap<EnsEtat, Etat>();
		Stack<EnsEtat> pile = new Stack<EnsEtat>();
		Set<Character> alphabet = this.alphabet();
		ArrayList<EnsEtat> listEnsEtat = new ArrayList<EnsEtat>();
		int etatCompteur = 0;

		Etat initEtat = new Etat(true, false, etatCompteur++);
		det.ajouteEtatSeul(initEtat);
		EnsEtat init = new EnsEtat();
		for(Etat etat : initiaux) init.add(etat);
		map.put(init, initEtat);
		pile.push(init);
		listEnsEtat.add(init);

		while(!pile.empty()){
			EnsEtat ensPop = pile.pop();
			//Recupere etat lie a l'ensemble d'etat pris dans la pile
			Etat etatLie = map.get(ensPop);
			//Parcours de l'alphabet de l'automate
			for(Character a : alphabet){
				EnsEtat etatsLieSuccA = new EnsEtat();
				//Parcours de l'ensemble d'etats pris dans la pile
				for(Etat etatPop : ensPop){
					//Recupere les etats succeceurs a la lettre courante
					EnsEtat etatsSucc = etatPop.succ(a.charValue());
					if(etatsSucc != null){
						//Ajoute les etats successeurs a un ensemble des successeurs de la lettre courante
						for(Etat tmp : etatsSucc) etatsLieSuccA.add(tmp);
					}
				}
				
				//Si on a trouver des successeurs pour la lettre courante
				if(!etatsLieSuccA.isEmpty()){
					//Cherche si on a pas deja creer un ensemble d'etat correspondant
					// aux successeurs de la lettre courante
					boolean existeDeja = false;
					EnsEtat refMemeEnsEtat = null;
					for(EnsEtat listEtat : listEnsEtat){
						if(listEtat.egale(etatsLieSuccA)){
							refMemeEnsEtat = listEtat;
							break;
						}
					}
					
					//Si l'ensemble des successeurs de la lettre courante existe pas deja
					if(refMemeEnsEtat == null){
						Etat nouvelEtat = new Etat(false, etatsLieSuccA.contientTerminal(), etatCompteur++);
						det.ajouteEtatSeul(nouvelEtat);
						map.put(etatsLieSuccA, nouvelEtat);
						pile.push(etatsLieSuccA);
						listEnsEtat.add(etatsLieSuccA);
						etatLie.ajouteTransition(a.charValue(), nouvelEtat);
					}else{
						etatLie.ajouteTransition(a.charValue(), map.get(refMemeEnsEtat));
					}
				}
			}
		}
		det.setMapDeterminise(map);
		return det;
	}
	
	/**
	 * Creer une copie de l'automate
	 * @return copie de l'automate
	 */
	public Automate copy(){
		Automate cp = new Automate();
		for(Etat e : this){
			cp.ajouteEtatSeul(new Etat(e.isInit(), e.isTerm(), e.hashCode()));
		}
		for(Etat e : cp){
			Etat lie = this.getEtat(e.hashCode());
			for(Map.Entry<Character, EnsEtat> entre : lie.transitions.entrySet()){
				for(Etat succ : entre.getValue()){
					e.ajouteTransition(entre.getKey().charValue(), succ);
				}
			}
		}
		return cp;
	}

	/**
	 * Cree l'automate complet
	 * @return l'automate complet
	 */
	public Automate complete(){
		Automate copie = this.copy();
		Etat puit = null;
		boolean reussi = false;
		int i = 0;
		while(!reussi){
			puit = new Etat(false, false, i);
			if(!copie.contains(puit)){
				reussi = true;
			}
			i++;
		}
		
		copie.ajouteEtatSeul(puit);

		Set<Character> alphabet = copie.alphabet();
		for(Etat etat : copie){
			for(Character a : alphabet){
				if(etat.succ(a.charValue()) == null){
					etat.ajouteTransition(a.charValue(), puit);
				}
			}
		}
		return copie;
	}

	/**
	 * Cree le complementaire
	 * @return l'automate complementaire
	 */
	public Automate complementaire(){
		Automate det = this.determinise();
		det.complete();
		for(Etat etat : det){
			etat.setTerm(!etat.isTerm());
		}
		return det;
	}

	/**
	 * Cree l'automate miroir
	 * @return l'automate miroir
	 */
	public Automate miroir(){
		Automate miroir = new Automate();
		for(Etat etat : this){
			miroir.ajouteEtatSeul(new Etat(etat.isTerm(), etat.isInit(), etat.hashCode())); 
		}

		for(Etat etat : this){
			for(Map.Entry<Character, EnsEtat> transition : etat.getTransitions().entrySet()){
				for(Etat etatSucc : transition.getValue()){
					miroir.getEtat(etatSucc.hashCode()).ajouteTransition(transition.getKey(), etat);
				}
			}
		}

		return miroir;
	}
	
	/**
	 * Test si le mot est accepte par l'automate
	 * @param s le mot a tester
	 * @return le resultat du test
	 */
	public boolean accepte(String s){
		return initiaux.accepte(s, 0);
	}
	
	/**
	 * Recupere l'automate minimiser avec l'algorithme de Moore
	 * @return automate minimal
	 */
	public Automate minimisation(){
		if(this.estDeterministe()) return Moore.minimisation(this);
		else return Moore.minimisation(this.determinise());
	}
	
	/**
	 * Cree l'automate de l'arbre representant une expression rationnelle
	 * @param arbre arbre d'une expression rationnelle
	 */
	private void fromArbre(Arbre arbre){
		initiaux.clear();
		this.clear();
		
		int idCompteur = 0;
		HashMap<Feuille, Etat> map = new HashMap<Feuille, Etat>();
		Map<Feuille, Set<Feuille>> succ = arbre.succ();
		Stack<Feuille> pile = new Stack<Feuille>();
		
		Etat etatInit = new Etat(true, arbre.contientMotVide, idCompteur++);
		Feuille feuilleInit = new Feuille(Arbre.MOT_VIDE);
		this.ajouteEtatSeul(etatInit);
		succ.put(feuilleInit, arbre.premiers);
		map.put(feuilleInit, etatInit);
		pile.push(feuilleInit);
		
		while(!pile.isEmpty()){
			Feuille feuilleCourante = pile.pop();
			if(succ.get(feuilleCourante) != null){
				for(Feuille feuilleSucc : succ.get(feuilleCourante)){
					Etat etatCourant = map.get(feuilleSucc);
					if(etatCourant == null){
						etatCourant = new Etat(false, arbre.derniers.contains(feuilleSucc), idCompteur++);
						this.ajouteEtatSeul(etatCourant);
						map.put(feuilleSucc, etatCourant);
						pile.push(feuilleSucc);
					}
					map.get(feuilleCourante).ajouteTransition(feuilleSucc.symbole, etatCourant);
				}
			}
		}
	}
	
	/**
	 * Ecrit dans le fichier la represention de l'automate
	 * @param nomFichier nom du fichier
	 */
	public void toFile(String nomFichier){
		try{
			new PrintStream(nomFichier).println(this.toString());
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Cree l'automate contenu dans le fichier
	 * @param fichier nom du fichier contenant l'automate
	 */
	public void readFile(String fichier){
		try{
    		Scanner sc = new Scanner(new File(fichier));
    		String ligne = sc.nextLine();
    		String[] ligneSplit = ligne.split(" ");
    		int etats = Integer.parseInt(ligneSplit[0]);
    		for(int  i = 0; i < etats; i++){
    			this.add(new Etat(false, false, i));
    		}
    		
    		Etat etatTmp = null;
    		while(sc.hasNextLine()){
    			ligne = sc.nextLine();
    			
    			if(ligne != null && ligne.length() > 0){
    				ligneSplit = ligne.split(" ");
    				etatTmp = getEtat(Integer.parseInt(ligneSplit[0]));
    				etatTmp.setInit(ligneSplit.length > 1 && ligneSplit[1].equals("initial"));
    				etatTmp.setTerm(ligneSplit.length > 1 && ligneSplit[1].equals("terminal") || ligneSplit.length > 2 && ligneSplit[2].equals("terminal"));
    				if(etatTmp.isInit()) initiaux.add(etatTmp);
    				
    				do{
    					ligne = sc.nextLine();
    					if(ligne == null || ligne.length() == 0) break;
    					ligneSplit = ligne.split(" ");
    					char transition = ligneSplit[0].charAt(0);
    					for(int i = 1; i < ligneSplit.length; i++){
    						etatTmp.ajouteTransition(transition, getEtat(Integer.parseInt(ligneSplit[i])));
    					}
    				}while(sc.hasNextLine());
    			}
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    public boolean estEgale(Automate test){
		if(this.size() != test.size()){
			System.out.println("Ne sont pas égaux : tailles differentes");
			return false;
		}
		
		if(!this.alphabet().equals(test.alphabet())){
			System.out.println("Ne sont pas égaux : alphabets differents");
			return false;
		}
		
		HashMap<Etat, Etat> map = new HashMap<Etat, Etat>();
		ArrayList<Etat> liste = new ArrayList<Etat>();
		Stack<Etat> pile = new Stack<Etat>();
		Set<Character> alphabet = this.alphabet();
		
		if(this.initiaux.size() != 1 || test.initiaux.size() != 1){
			System.out.println("Ne sont pas égaux : il y a pas ou plus d'un état initial");
			return false;
		}
		
		pile.push(this.initiaux.iterator().next());
		map.put(this.initiaux.iterator().next(), test.initiaux.iterator().next());
		
		while(!pile.isEmpty()){
			Etat courant = pile.pop();
			Etat lie = map.get(courant);
			for(Character c : alphabet){
				EnsEtat ensSucc = courant.succ(c);
				EnsEtat ensLieSucc = lie.succ(c);
				if(ensSucc.size() > 0 && ensLieSucc.size() > 0){
					if(ensSucc.size() != 1 || ensLieSucc.size() != 1){
						System.out.println("Ne sont pas égaux : non déterminisé");
						return false;
					}
					Etat courantSucc = ensSucc.iterator().next();
					if(!liste.contains(courantSucc)){
						Etat lieSucc = ensLieSucc.iterator().next();
						liste.add(courantSucc);
						map.put(courantSucc, lieSucc);
						pile.push(courantSucc);
					}
				}
			}
		}
		return true;
	}

	@Override
	public String toString(){
		return super.toString();
	}
	
	/**
	 * Representation alternative d'un automate
	 * @return une chaine de caractere de l'automate
	 */
	public String affiche(){
		return super.affiche();
	}

	/**
	 * Getter des initiaux
	 */
    public EnsEtat getInitiaux() {
        return initiaux;
    }
}
