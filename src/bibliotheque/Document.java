package bibliotheque;

public interface Document {
    
        public final int DELAI_RESERVATION = 5;
        
	int numero();

	void reserver(Abonne ab) throws PasLibreException ;

	void emprunter(Abonne ab) throws PasLibreException;
	void rendreDispo();
        
        Abonne getEmprunteur();
        
        
}
