import java.rmi.Remote;
import java.rmi.RemoteException;

//Creación de los métodos que va a tener el Servidor.
public interface FileOps extends Remote {
    public byte[] readFile(String filename) throws RemoteException;
    public void writeFile(String filename, byte[] data) throws RemoteException;
    public int deleteFile(String filename) throws RemoteException;
    public String[] listFiles() throws RemoteException;
    public int renameFile(String currentFilename, String newFilename) throws RemoteException;
}
