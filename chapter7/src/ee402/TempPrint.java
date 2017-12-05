package ee402;
import java.io.*;
import java.net.InetAddress;
import java.net.*;

public class TempPrint {
    public static void main(String [] args) {

        String fileName = "/sys/class/thermal/thermal_zone0/temp";
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                System.out.println("Temp °C: " + Integer.parseInt(line) / 1000);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
        }
        catch(IOException ex) {
            System.out.println("Error reading file '" + fileName + "'");
        }
        try {
        	InetAddress localAddress = InetAddress.getLocalHost();
        	System.out.println("Local hostname is: " + localAddress.getHostName());
        	System.out.println("Local address is: " + localAddress.getHostAddress());
        }
        catch (UnknownHostException e) {
        	System.out.println("Cannot detect localhost : " + e.toString());
        }
    }
}