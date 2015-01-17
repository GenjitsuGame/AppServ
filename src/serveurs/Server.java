package serveurs;

import java.io.*;
import java.net.*;

// cette classe Server est très générale et peut servir de modèle
// pour tous vos futurs serveurs

public abstract class Server implements Runnable {
	protected ServerSocket listen_socket; // le serveur d'écoute
	protected Thread thread;
	// Cree un serveur TCP : c'est un objet de la
	// classe ServerSocket
	
	public Server(int port) throws IOException, InstantiationException, IllegalAccessException {  
			this.listen_socket = new ServerSocket(port);
			this.thread = new Thread(this);			
	}
	// lance le thread du serveur.
	public void start(){
		this.thread.start();
	}

	// à surcharger si le serveur veut passer d'autres infos au dialogue
	protected void init(Service service) {
			}
        
        @Override
        public abstract void run();
        
	public void close() {
		/* permet d'arreter le serveur
		 * listen_socket.accept() génère une IOException, 
		 * et le run se termine
		 * on pourrait aussi faire this.thread.interrupt et 
		 * accept génère une InterruptedIOException sous-classe de IOException
		 * 
		 */
		try {this.listen_socket.close();} catch (IOException e) {}
	}
}
