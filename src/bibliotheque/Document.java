package bibliotheque;

public interface Document {
	int numero(); // renvoie le numero du document
	// enregistre que le document est réservé par l’abonné ab
	void reserver(Abonne ab) throws PasLibreException ;
	// enregistre que le document est emprunté par l’abonné ab
	void emprunter(Abonne ab) throws PasLibreException;
	void rendreDispo();// enregistre que le document est disponible
}
