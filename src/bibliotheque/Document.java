package bibliotheque;

public interface Document {
	int numero(); // renvoie le numero du document
	// enregistre que le document est r�serv� par l�abonn� ab
	void reserver(Abonne ab) throws PasLibreException ;
	// enregistre que le document est emprunt� par l�abonn� ab
	void emprunter(Abonne ab) throws PasLibreException;
	void rendreDispo();// enregistre que le document est disponible
}
