import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashSet;
import java.io.File;

public class Main{
	
	private enum Cas{
		None, Aut, Exp, AutAut, AutExp, ExpAut, ExpExp
	};
	
	public static boolean INFO = false;
	public static boolean DG = true;
	
	public static void main(String[] args){
		// ------ Exemple 1
		/*
		Etat zero = new Etat(true, false, 0);
		Etat un = new Etat(false, false, 1);
		Etat deux = new Etat(false, true, 2);
		
		zero.ajouteTransition('a', un);
		un.ajouteTransition('a', un);
		un.ajouteTransition('c', un);
		un.ajouteTransition('b', deux);

		Automate af = new Automate();
		af.ajouteEtatRecursif(zero);

		System.out.println(af.affiche());
		//System.out.println("Deterministe ? "+af.estDeterministe());
		*/
		
		// ------ Exemple 2
		/*
		Etat zero = new Etat(true, false, 0);
		Etat un = new Etat(false, true, 1);
		Etat deux = new Etat(false, true, 2);

		zero.ajouteTransition('a', un);
		zero.ajouteTransition('b', deux);
		un.ajouteTransition('a', deux);
		un.ajouteTransition('b', zero);
		deux.ajouteTransition('a', zero);
		deux.ajouteTransition('b', un);

		Automate af = new Automate();
		af.ajouteEtatRecursif(zero);

		System.out.println(af);
		System.out.println("Deterministe ? "+af.estDeterministe());
		*/

		// ------ Exemple 3
		/*
		Etat zero = new Etat(true, false, 0);
		Etat un = new Etat(false, false, 1);
		Etat deux = new Etat(false, false, 2);
		Etat trois = new Etat(false, true, 3);

		zero.ajouteTransition('a', zero);
		zero.ajouteTransition('a', un);
		zero.ajouteTransition('b', zero);
		un.ajouteTransition('a', deux);
		un.ajouteTransition('b', deux);
		deux.ajouteTransition('a', trois);

		Automate af = new Automate(zero);
		
		//System.out.println(af.affiche());
		//System.out.println("Deterministe ? "+af.estDeterministe()+"\n");
		//System.out.println("---- Determiniser ----\n"+af.determinise().affiche());
		*/

		// ------ Exemple 4
		/*
		Etat zero = new Etat(true, false, 0);
		Etat un = new Etat(true, false, 1);
		Etat deux = new Etat(true, true, 2);

		zero.ajouteTransition('a', un);
		un.ajouteTransition('b', un);
		un.ajouteTransition('b', deux);
		deux.ajouteTransition('b', un);
		deux.ajouteTransition('b', deux);
		deux.ajouteTransition('a', deux);

		Automate af = new Automate();
		af.ajouteEtatRecursif(zero);
		
		//System.out.println(af);
		//System.out.println("Deterministe ? "+af.estDeterministe());
		*/
		
		// ------ TEST ALPHABET
		/*
		String s = "";
		if(args.length > 0){
			s = args[0];
			System.out.println("Accepte "+s+" ? "+af.accepte(s));
		}

		System.out.println("Alphabet : "+af.alphabet().toString());
		*/

		// ------ TEST DETERMINISE 1
		/*
		Etat zero = new Etat(true, false, 0);
		Etat un = new Etat(false, false, 1);
		Etat deux = new Etat(false, false, 2);
		Etat trois  = new Etat(false, true, 3);

		zero.ajouteTransition('a', zero);
		zero.ajouteTransition('b', zero);
		zero.ajouteTransition('a', un);
		un.ajouteTransition('a', deux);
		un.ajouteTransition('b', deux);
		deux.ajouteTransition('a', trois);
		trois.ajouteTransition('a', trois);
		trois.ajouteTransition('b', trois);

		Automate af = new Automate();
		af.ajouteEtatRecursif(zero);

		//System.out.println(af);
		//System.out.println("Deterministe ? "+af.estDeterministe());

		Automate afDet = af.determinise();
		System.out.println(afDet);
		System.out.println("Deterministe ? "+afDet.estDeterministe());
		*/

		// ------ TEST DETERMINISE 2
		/*
		Etat zero = new Etat(true, false, 0);
		Etat un = new Etat(false, false, 1);
		Etat deux = new Etat(false, false, 2);
		Etat trois  = new Etat(false, true, 3);

		zero.ajouteTransition('a', un);
		zero.ajouteTransition('b', un);
		zero.ajouteTransition('a', deux);
		un.ajouteTransition('a', trois);
		un.ajouteTransition('b', trois);
		deux.ajouteTransition('b', zero);
		trois.ajouteTransition('a', deux);
		trois.ajouteTransition('b', zero);

		Automate af = new Automate();
		af.ajouteEtatRecursif(zero);

		System.out.println(af);
		System.out.println("Deterministe ? "+af.estDeterministe());

		Automate afDet = af.determinise();
		System.out.println(afDet);
		System.out.println("Deterministe ? "+afDet.estDeterministe());
		*/

		// ------ Test complete
		//System.out.println("Complete\n"+af.complete());

		// ------ Test complementaire
		/*
		Etat zero = new Etat(true, true, 0);
		Etat un = new Etat(false, true, 1);
		zero.ajouteTransition('a', zero);
		zero.ajouteTransition('b', un);
		un.ajouteTransition('a', zero);
		Automate af = new Automate();
		af.ajouteEtatRecursif(zero);
		System.out.println("Complementaire :\n"+af.complementaire());
		*/

		// ------ Test miroir
		//System.out.println(af);
		//System.out.println("Miroir:\n"+af.miroir());

		// ------ Test union intersection
		/*
		Etat zero = new Etat(true, false, 0);
		Etat un = new Etat(false, true, 1);
		Etat deux = new Etat(true, false, 2);
		Etat trois = new Etat(false, true, 3);

		zero.ajouteTransition('a', un);
		un.ajouteTransition('a', un);
		un.ajouteTransition('b', un);

		deux.ajouteTransition('a', deux);
		deux.ajouteTransition('b', trois);
		trois.ajouteTransition('b', trois);
		trois.ajouteTransition('a', deux);
		
		Automate a = new Automate();
		a.ajouteEtatRecursif(zero);
		Automate b = new Automate();
		b.ajouteEtatRecursif(deux);

		System.out.println("UNION\n"+a.union(b)+"\n\n");
		System.out.println("INTERSECTION\n"+a.intersection(b));
		*/

		// ------ Expression infixe
		/*
		Feuille a = new Feuille('a');
		Feuille b1 = new Feuille('b');
		Feuille b2 = new Feuille('B');
		Feuille c = new Feuille('c');

		Binaire un = new Binaire('+', a, b1);
		Unaire un1 = new Unaire(un);
		Binaire deux = new Binaire('.', un1, b2);
		Binaire trois = new Binaire('.', deux, c);

		System.out.println("Premiers : "+trois.premiers+"\nDerniers : "+trois.derniers+"\nSucc : "+trois.succ().toString());
		Automate af = new Automate(trois);
		System.out.println("--Automate--\n"+af.affiche());
		*/

		// ------ Lecture postfixe
		/*
		Arbre arbre = Arbre.lirePostfixe("ab+*b.c.");
		System.out.println(arbre.succ().toString());
		*/
		/*
		Arbre a = Arbre.lirePostfixe("ab.*a.");
		System.out.println(a+"\n"+a.premiers+"\n"+a.derniers+"\n"+a.succ());
		Arbre b = a.copy();
		System.out.println(b+"\n"+b.premiers+"\n"+b.derniers+"\n"+b.succ());
		*/
		
		// ------ Test lecture fichier
		//Automate aft = new Automate("af.txt");
		//System.out.println(aft.affiche());
		
		
		// ------ TEST MOORE
		/*
		Etat un = new Etat(true, false, 1);
		Etat deux = new Etat(false, false, 2);
		Etat trois = new Etat(false, false, 3);
		Etat quatre = new Etat(false, false, 4);
		Etat cinq = new Etat(false, false, 5);
		Etat six = new Etat(false, true, 6);
		Etat sept = new Etat(false, true, 7);
		
		un.ajouteTransition('a', six);
		un.ajouteTransition('b', deux);
		deux.ajouteTransition('a', sept);
		deux.ajouteTransition('b', un);
		trois.ajouteTransition('a', un);
		trois.ajouteTransition('b', six);
		quatre.ajouteTransition('a', trois);
		quatre.ajouteTransition('b', six);
		cinq.ajouteTransition('a', quatre);
		cinq.ajouteTransition('b', sept);
		six.ajouteTransition('a', sept);
		six.ajouteTransition('b', cinq);
		sept.ajouteTransition('a', six);
		sept.ajouteTransition('b', cinq);
		
		Automate mooretest = new Automate(un);
		System.out.println("Test moore\ndet : "+mooretest.estDeterministe());
		System.out.println(mooretest.minimisation());
		*/
		
		// ------ TEST RESIDUEL
		/*
		Arbre arbre = Arbre.lirePostfixe("bab.*.ab.*a.+");
		System.out.println(arbre);
		Automate a =  new Automate(Arbre.residuels(arbre));
		System.out.println("RESIDUEL\n"+a);
		*/
		
		/*Arbre bb = Arbre.lirePostfixe("bab.*.ab.*a.+");
		System.out.println("Premiers : "+bb.premiers+"\nDerniers : "+bb.derniers);
		System.out.println(bb);*/
		/*Automate b = new Automate(bb);
		System.out.println("GLUSHKOV\n"+b);
		System.out.println("Det ? "+b.estDeterministe());
		Automate det = b.determinise();
		System.out.println("Determiniser\n"+det);
		System.out.println("MOORE\n"+det.minimisation());*/
		//Automate res = new Automate(Arbre.residuels(bb));
		//System.out.println(res);
		/*System.out.println("A: "+bb.residuel('a'));
		Arbre bbb = bb.residuel('b');
		Arbre bbba = bbb.residuel('a');
		Arbre bbbb = bbb.residuel('b');
		System.out.println("B: "+bbb+" T:"+bbb.estResiduelTerm);
		System.out.println("-A: "+bbba+" T:"+bbba.estResiduelTerm);
		System.out.println("-B: "+bbbb+" T:"+bbbb.estResiduelTerm);*/
		
		
		/*Arbre r = arbre.residuel('a');
		System.out.println("Residuel en a : "+r);
		Feuille b = new Feuille('a');
		Feuille c = new Feuille('a');
		System.out.println("test : "+b.equals(c));*/
		
		/*HashSet<Character> al = new HashSet<Character>();
		al.add('a');
		al.add('b');
		Automate a = new Automate(6, al);
		System.out.println(a);
		System.out.println("DETERMINISE\n"+a.determinise());
		System.out.println("MINIMISER\n"+a.minimisation());*/
		param(args);

	}
	
	public static void param(String args[]){
		String fichierLecture = null;
		String fichierLecture2 = null;
		String fichierEcriture = null;
		int nombreAlea = -1;
		String alphabetAlea = null;
		boolean comparer = false;
		boolean moore = false;
		boolean residuel = false;
		boolean glushkov = false;
		
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
			}else if(mot.equals("-m")){
				moore = true;
			}else if(mot.equals("-r")){
				residuel = true;
			}else if(mot.equals("-G")){
				glushkov = true;
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
	}
	
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
