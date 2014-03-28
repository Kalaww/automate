public class Main{
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
		Automate a = new Automate(Arbre.lirePostfixe("ab.*a."));
		System.out.println(a);
		
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
	}
		
}
