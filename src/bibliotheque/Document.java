package bibliotheque;

public interface Document {
	int numero();

	void reserver(Abonne ab) throws PasLibreException ;

	void emprunter(Abonne ab) throws PasLibreException;
	void rendreDispo();
        
        Abonne getEmprunteur();
        
        
}
