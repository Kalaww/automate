public class Moore(){

	public static Automate minimisation(Automate af){
		
		char[] alphabetIt = char[af.alphabet().size()];
		int compteur = 0;
		for(Character c : af.alphabet()) alphabetIt[compteur++] = c.charValue();
		
		
	
	}

}

public class EtapeMoore(){
	
	public int motVide;
	public int bilan;
	public HashMap<Character,Integer> transitions;
	
	public EtapeMoore(boolean estTerm){
		this.motVide = (estTerm)? 1 : 2;
		this.bilan = 0;
		this.transitions = HashMap<Character,Integer>();
	}
	
}
