package bibliotheque;

public class Livre implements Document {
	private int numero;
	private boolean dispo;
	@Override
	public int numero() {
		return numero;
	}

	@Override
	public void reserver(Abonne ab) throws PasLibreException {
		
	}

	@Override
	public void emprunter(Abonne ab) throws PasLibreException {
		
	}

	@Override
	public void rendreDispo() {
		
	}

	public boolean isDispo() {
		return dispo;
	}

	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}

}
