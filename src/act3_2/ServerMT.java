package act3_2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import act3_2.Operation;


public class ServerMT extends Thread {
    private int nbrClient = 1;

    public static void main(String[] args) {
        new ServerMT().start();
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(1234);
            System.out.println("Un serveur attend un client ");

            while (true) {
                Socket s = ss.accept();
                new ClientProcess(s,nbrClient).start();
                nbrClient++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class ClientProcess extends Thread 
    {
        private Socket s;
        private int nbrClient;

        ClientProcess(Socket s, int nbrClient) {
            this.s = s;
            this.nbrClient = nbrClient;
        }

        public void run() {
            try {
       
                String IP = s.getRemoteSocketAddress().toString();
                System.out.println("Connexion du client num " + nbrClient + IP);
                InputStream is = s.getInputStream();
    			ObjectInputStream ois = new ObjectInputStream(is);
    			
    			// Conversion du type Object vers Operation
    			Operation op = (Operation) ois.readObject();
    			
    			// Traitement / Service
    			int resultat = op.op1;
    			switch(op.operation) {
    			case '+':
    				resultat += op.op2;
    				break;
    			case '-':
    				resultat -= op.op2;
    				break;
    			case '*':
    				resultat *= op.op2;
    				break;
    			case '/':
    				resultat /= op.op2;
    				break;
    			}
    		
    			op.setResultat(resultat);
    			
    			// Le renvoi du même objet vers le client après modification de la propriétés 'Resultat'
    			OutputStream os = s.getOutputStream();
    			ObjectOutputStream oos = new ObjectOutputStream(os);
    			oos.writeObject(op);
            } catch (IOException e) 
            {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
            finally 
            {
                try 
                {
                    s.close();
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            
        }
    }
    }
    }

