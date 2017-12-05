/* The Connection Handler Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 */

package ee402;

import java.net.*;
import java.io.*;

public class ConnectionHandler
{
    private Socket clientSocket = null;				// Client socket object
    private ObjectInputStream is = null;			// Input stream
    private ObjectOutputStream os = null;			// Output stream
    private DateTimeService theDateService;
    
	// The constructor for the connection handler
    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        //Set up a service object to get the current date and time
        theDateService = new DateTimeService();
    }

    // Will eventually be the thread execution method - can't pass the exception back
    public void init() {
         try {
            this.is = new ObjectInputStream(clientSocket.getInputStream());
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
            while (this.readCommand()) {}
         } 
         catch (IOException e) 
         {
        	System.out.println("XX. There was a problem with the Input/Output Communication:");
            e.printStackTrace();
         }
    }

    // Receive and process incoming string commands from client socket 
    private boolean readCommand() {
        Object o = null;
        try {
            o = (Object) is.readObject();
        } 
        catch (Exception e){    // catch a general exception
        	this.closeSocket();
            return false;
        }
        System.out.println("01. <- Received an object from the client (" + o.toString() + ").");
        System.out.println("01. <- Received an object from the client type is (" + o.getClass().getName() + ").");
        // At this point there is a valid String object
        // invoke the appropriate function based on the command 
        if (o.getClass().getName().equals("ee402.Fish")){ 
            
        	Fish aFish = (Fish) o;
        	System.out.println("\nFish name:" + aFish.getName());
        	System.out.println("\nFish type:" + aFish.getType());
        }       
        else { 
            this.sendError("Invalid object: " + o.toString()); 
        }
        return true;
    }

    // Use our custom DateTimeService Class to get the date and time
    private void getDate() {	// use the date service to get the date
        String currentDateTimeText = theDateService.getDateAndTime();
        this.send(currentDateTimeText);
    }

    // Send a generic object back to the client 
    private void send(Object o) {
        try {
            System.out.println("02. -> Sending (" + o +") to the client.");
            this.os.writeObject(o);
            this.os.flush();
        } 
        catch (Exception e) {
            System.out.println("XX." + e.getStackTrace());
        }
    }
    
    // Send a pre-formatted error message to the client 
    public void sendError(String message) { 
        this.send("Error:" + message);	//remember a String IS-A Object!
    }
    
    // Close the client socket 
    public void closeSocket() { //gracefully close the socket connection
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        } 
        catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }
}