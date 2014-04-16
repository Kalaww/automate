import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.io.File;

public class Main{
	
	/**
	 * Enum pour les cas d'entrée de fichier
	 */
	private enum Cas{
		None, Aut, Exp, AutAut, AutExp, ExpAut, ExpExp
	};
	
	/**
	 * Variable d'affichage des informations supplémentaires
	 */
	public static boolean INFO = false;
	
	/**
	 * Variable de débug
	 */
	public static boolean DG = false;
	
	public static void main(String[] args){
		String fichierLecture = null;
		String fichierLecture2 = null;
		String fichierEcriture = null;
		int nombreAlea = -1;
		String alphabetAlea = null;
		boolean comparer = false;
		boolean moore = false;
		boolean residuel = false;
		boolean glushkov = false;
		boolean complementaire = false;
		boolean miroir = false;
		boolean determinise = false;
		boolean complete = false;
		
		Cas cas = Cas.None;
		
		Automate automateDepart = null;
		Automate automateDepart2 = null;
		Arbre arbreDepart = null;
		Arbre arbreDepart2 = null;
		
		
		//Recupération des paramètres
		for(int i = 0; i < args.length; i++){
			String mot = args[i];
			if(mot.equals("-f")){
				if(i+1 >= args.length){
					throw new IllegalArgumentException("Pas de fichier après -f");
				}else{
					fichierLecture = args[i+1];
				}
				i++;
			}else if(mot.equals("-f2")){
				if(i+1 >= args.length){
					throw new IllegalArgumentException("Pas de fichier après -f2");
				}else{
					fichierLecture2 = args[i+1];
				}
				i++;
			}else if(mot.equals("-g")){
				if(i+2 >= args.length){
					throw new IllegalArgumentException("Manque deux arguments après -g");
				}else{
					nombreAlea = Integer.parseInt(args[i+1]);
					alphabetAlea = args[i+2];
				}
				i+=2;
			}else if(mot.equals("-w")){
				if(i+1 >= args.length){
					throw new IllegalArgumentException("Pas de fichier après -w");
				}else{
					fichierEcriture = args[i+1];
				}
				i++;
			}else if(mot.equals("-c")){
				comparer = true;
			}else if(mot.equals("-i")){
				Main.INFO = true;
			}else if(mot.equals("-M")){
				moore = true;
			}else if(mot.equals("-r")){
				residuel = true;
			}else if(mot.equals("-G")){
				glushkov = true;
			}else if(mot.equals("-dg")){
				Main.DG = true;
			}else if(mot.equals("-cp")){
				complementaire = true;
			}else if(mot.equals("-m")){
				miroir = true;
			}else if(mot.equals("-d")){
				determinise = true;
			}else if(mot.equals("-cmp")){
				complete = true;
			}else{
				throw new IllegalArgumentException("Argument inconnu : "+mot);
			}
		}
		
		
		//FICHIER 1
		if(fichierLecture != null && (nombreAlea > -1 || alphabetAlea != null)){
			throw new IllegalArgumentException("Lecture de fichier et génération aléatoire d'automate dans la même commande impossible");
		}else if(fichierLecture != null){
			String tmp = fichierContientExpRat(fichierLecture);
			if(tmp != null){
				arbreDepart = Arbre.lirePostfixe(tmp);
			}else{
				automateDepart = new Automate(fichierLecture);
			}
		}else if(nombreAlea > -1 && alphabetAlea != null){
			HashSet<Character> alphabet = new HashSet<Character>();
			for(int i = 0; i < alphabetAlea.length(); i++)
				alphabet.add(alphabetAlea.charAt(i));
			automateDepart = new Automate(nombreAlea, alphabet);
			System.out.println("--- AUTOMATE GENERE ---\n"+automateDepart);
		}else{
			throw new IllegalArgumentException("Aucun argument pour la génération d'un automate");
		}
		
		
		//FICHIER 2
		if(fichierLecture2 != null){
			String tmp = fichierContientExpRat(fichierLecture2);
			if(tmp != null){
				arbreDepart2 = Arbre.lirePostfixe(tmp);
			}else{
				automateDepart2 = new Automate(fichierLecture2);
			}
		}
		
		
		//Gestion du cas des fichiers de lectures
		if(fichierLecture2 == null){
			if(automateDepart != null) cas = Cas.Aut;
			else if(arbreDepart != null) cas = Cas.Exp;
		}else{
			if(automateDepart != null && automateDepart2 != null) cas = Cas.AutAut;
			else if(automateDepart != null && arbreDepart2 != null) cas = Cas.AutExp;
			else if(arbreDepart != null && automateDepart2 != null) cas = Cas.ExpAut;
			else if(arbreDepart != null && arbreDepart2 != null) cas = Cas.ExpExp;
		}
		if(Main.DG) System.out.println("CAS : "+cas);
		if(cas.equals(Cas.None)){
			System.out.println("Aucun automate ou expression rationnelle en entrée");
			return;
		}
			
		
		//Verification d'opérations multiples
		int verif = 0;
		if(comparer) verif++;
		if(moore) verif++;
		if(residuel) verif++;
		if(glushkov) verif++;
		if(complementaire) verif++;
		if(miroir) verif++;
		if(determinise) verif++;
		if(complete) verif++;
		
		if(verif == 0){
			System.out.println("Aucune opération demandée");
			return;
		}
		if(verif > 1){
			System.out.println("Plus d'une opération demandée. Une seul peut être éxécutée par commande");
			return;
		}
		
		
		//COMPARAISON
		if(comparer){
			Automate un = null, deux = null;
			boolean egaux = false;
			if(cas.equals(Cas.Exp)){
				un = new Automate(arbreDepart).minimisation();
				System.out.println("--- MINIMISATION MOORE ---\n"+un);
				deux = new Automate(Arbre.residuels(arbreDepart.copy()));
				System.out.println("--- MINIMISATION RESIDUELS ---\n"+deux);
			}else if(cas.equals(Cas.AutAut)){
				un = automateDepart.minimisation();
				System.out.println("--- MINIMISATION MOORE 1 ---\n"+un);
				deux = automateDepart2.minimisation();
				System.out.println("--- MINIMISATION MOORE 2 ---\n"+deux);
			}else if(cas.equals(Cas.AutExp)){
				un = automateDepart.minimisation();
				System.out.println("--- MINIMISATION MOORE ---\n"+un);
				deux = new Automate(Arbre.residuels(arbreDepart2));
				System.out.println("--- MINIMISATION RESIDUELS ---\n"+deux);
			}else if(cas.equals(Cas.ExpAut)){
				un = new Automate(Arbre.residuels(arbreDepart));
				System.out.println("--- MINIMISATION RESIDUELS ---\n"+un);
				deux = automateDepart2.minimisation();
				System.out.println("--- MINIMISATION MOORE ---\n"+deux);
			}else if(cas.equals(Cas.ExpExp)){
				un = new Automate(Arbre.residuels(arbreDepart));
				System.out.println("--- MINIMISATION RESIDUELS 1 ---\n"+un);
				deux = new Automate(Arbre.residuels(arbreDepart2));
				System.out.println("--- MINIMISATION RESIDUELS 2 ---\n"+deux);
			}else{
				System.out.println("Les paramètres donnés ne permettrent pas de réaliser une comparaison");
				return;
			}
			
			if(un != null && deux != null) egaux = un.estEgale(deux);
			System.out.println("Egaux : "+((egaux)? "Oui" : "Non"));
			
			if(egaux && fichierEcriture != null){
				System.out.println("Sauvegarde de l'automate ...");
				un.toFile(fichierEcriture);
			}else if(!egaux && fichierEcriture != null){
				System.out.println("Les deux automates ne sont pas égaux. Aucune sauvegarde réalisée");
			}
		}
		
		
		//MOORE
		if(moore){
			Automate un = null;
			if(cas.equals(Cas.Aut)){
				un = automateDepart.minimisation();
				System.out.println("--- MINIMISATION MOORE ---\n"+un);
			}else if(cas.equals(Cas.Exp)){
				un = new Automate(arbreDepart);
				un = un.minimisation();
				System.out.println("--- MINIMISATION MOORE ---\n"+un);
			}else{
				System.out.println("Les paramètres donnés ne permettrent pas de réaliser une minimisation par Moore");
				return;
			}
			
			if(un != null && fichierEcriture != null){
				System.out.println("Sauvegarde de l'automate ...");
				un.toFile(fichierEcriture);
			}
		}
		
		
		//RESIDUEL
		if(residuel){
			Automate un = null;
			if(cas.equals(Cas.Exp)){
				un = new Automate(Arbre.residuels(arbreDepart));
				System.out.println("--- MINIMISATION RESIDUELS ---\n"+un);
			}else{
				System.out.println("Les paramètres donnés ne permettent pas de réaliser une minimisation par les résiduels");
				return;
			}
			
			if(un != null && fichierEcriture != null){
				System.out.println("Sauvegarde de l'automate ...");
				un.toFile(fichierEcriture);
			}
		}
		
		
		//GLUSHKOV
		if(glushkov){
			Automate un = null;
			if(cas.equals(Cas.Exp)){
				un = new Automate(arbreDepart);
				System.out.println("--- GLUSHKOV ---\n"+un);
			}else{
				System.out.println("Les paramètres donnés ne permettrent pas de réaliser un algorithme de Glushkov");
				return;
			}
			
			if(un != null && fichierEcriture != null){
				System.out.println("Sauvegarde de l'automate ...");
				un.toFile(fichierEcriture);
			}
		}
		
		
		//COMPLEMENTAIRE
		if(complementaire){
			Automate un = null;
			if(cas.equals(Cas.Exp)){
				un = new Automate(arbreDepart);
				un = un.complementaire();
				System.out.println("--- COMPLEMENTAIRE ---\n"+un);
			}else if(cas.equals(Cas.Aut)){
				un = automateDepart.complementaire();
				System.out.println("--- COMPLEMENTAIRE ---\n"+un);
			}else{
				System.out.println("Les paramètres donnés ne permettrent pas de réaliser le complémentaire");
				return;
			}
			
			if(un != null && fichierEcriture != null){
				System.out.println("Sauvegarde de l'automate ...");
				un.toFile(fichierEcriture);
			}
		}
		
		
		//MIROIR
		if(miroir){
			Automate un = null;
			if(cas.equals(Cas.Exp)){
				un = new Automate(arbreDepart);
				un = un.miroir();
				System.out.println("--- MIROIR ---\n"+un);
			}else if(cas.equals(Cas.Aut)){
				un = automateDepart.miroir();
				System.out.println("--- MIROIR ---\n"+un);
			}else{
				System.out.println("Les paramètres donnés ne permettrent pas de réaliser le miroir");
				return;
			}
			
			if(un != null && fichierEcriture != null){
				System.out.println("Sauvegarde de l'automate ...");
				un.toFile(fichierEcriture);
			}
		}
		
		
		//DETERMINISE
		if(determinise){
			Automate un = null;
			if(cas.equals(Cas.Exp)){
				un = new Automate(arbreDepart);
				un = un.determinise();
				System.out.println("--- DETERMINISE ---\n"+un);
			}else if(cas.equals(Cas.Aut)){
				un = automateDepart.determinise();
				System.out.println("--- DETERMINISE ---\n"+un);
			}else{
				System.out.println("Les paramètres donnés ne permettrent pas de réaliser la determinisation");
				return;
			}
			
			if(un != null && fichierEcriture != null){
				System.out.println("Sauvegarde de l'automate ...");
				un.toFile(fichierEcriture);
			}
		}
		
		
		//COMPLETE
		if(complete){
			Automate un = null;
			if(cas.equals(Cas.Exp)){
				un = new Automate(arbreDepart);
				un = un.complete();
				System.out.println("--- COMPLETE ---\n"+un);
			}else if(cas.equals(Cas.Aut)){
				un = automateDepart.complete();
				System.out.println("--- COMPLETE ---\n"+un);
			}else{
				System.out.println("Les paramètres donnés ne permettrent pas de completer un automate");
				return;
			}
			
			if(un != null && fichierEcriture != null){
				System.out.println("Sauvegarde de l'automate ...");
				un.toFile(fichierEcriture);
			}
		}
	}
	
	/**
	 * Test si le fichier contient une expression rationnelle
	 * @param fichier nom du fichier
	 * @return l'expression rationnelle si oui, null sinon
	 */
	public static String fichierContientExpRat(String fichier){
		try{
			Scanner sc = new Scanner(new File(fichier));
			String ligne = sc.nextLine();
			if(ligne != null && sc.hasNextLine()) return null;
			else return ligne;
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
