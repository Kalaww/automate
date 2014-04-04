import java.util.ArrayList;

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
		
		Arbre bb = Arbre.lirePostfixe("bab.*.ab.*a.+");
		System.out.println("Premiers : "+bb.premiers+"\nDerniers : "+bb.derniers);
		System.out.println(bb);
		/*Automate b = new Automate(bb);
		System.out.println("GLUSHKOV\n"+b);
		System.out.println("Det ? "+b.estDeterministe());
		Automate det = b.determinise();
		System.out.println("Determiniser\n"+det);
		System.out.println("MOORE\n"+det.minimisation());*/
		Automate res = new Automate(Arbre.residuels(bb));
		System.out.println(res);
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
		//commande(args);
	}
	
	
	/*public static void commande(String[] args){
		boolean glushkov = false;
		boolean moore = false;
		boolean residuel = false;
		boolean comparer = false
		
		
		for(int i = 0; i < args.length; i++){
			switch(args[i].charAt(0)){
				case '-':
					if(args[i].length() < 2)
						throw new IllegalArgumentException("Argument non valide: "+args[i]);
					else{
						if(args.length-1 == i || args[i+1])
							throw new IllegalArgumentException("Argument non valide: "+args[i]);
						optList.add(new Option(args[i], args[i+1]));
						i++;
					}
					break;
				default:
					argList.add(args[i]);
					break;
				}
		}
		
		System.out.println("ARG\n"+argList);
		System.out.println("OPT\n"+optList);
	}*/
}

/*private class Option{
	String flag;
	String opt;
	
	public Option(String flag, String opt){
		this.flag = flag;
		this.opt = opt;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		} else {
			final Option other = (Option) obj;
			return (flag.equals(other.flag));
		}
	}
	
	@Override
	public String toString(){
		return "["+flag+"] = "+opt;
	}
}*/
