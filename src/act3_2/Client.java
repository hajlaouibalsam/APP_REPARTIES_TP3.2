package act3_2;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import act3_2.Operation;


public class Client {
	public static void main(String[] args) {
		try {
			// Connexion au serveur
			System.out.println("je suis un nouveau client ");
			
			Socket socket = new Socket("localhost", 1234);
			System.out.println("je suis connecté");

			Scanner scanner = new Scanner(System.in);
			System.out.println("Donner le premier operateur : ");
			int op1 = scanner.nextInt();
			
			
			System.out.println("Donner l'operation : ");

			String operation;
			do {
				operation = scanner.nextLine();
			}while(!(operation.equals("*")) && !(operation.equals("+")) && !(operation.equals("-")) && !(operation.equals("/")));
	
			
			System.out.println("Donner le deuxieme operateur : ");
			int op2 = scanner.nextInt();
			
			scanner.close();
			
			
			// Création d'une instance de Operation à partir de la saisie de l'utilisateur
			Operation op = new Operation(op1, op2, operation.charAt(0));
			
			// Utilisation de ObjectOutputStream pour pouvoir convertir un objet (Serializable) à un fichier binaire
			// Puis l'envoyer au serveur
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(op);
			
			// Utlisation du ObjectInputStream pour pourvoir lire l'objet 
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			op = (Operation) ois.readObject();
			
			System.out.println("\n" + op.op1 + " " + op.operation + " " + op.op2 + " = " + op.resultat);
			
			// Deconnexion et libération des ressources
			socket.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}