import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            // Crear el registro RMI en el puerto 3399, ya que este es el asignado por la universidad para las maquinas virtuales.
            LocateRegistry.createRegistry(1099);
            // LocateRegistry.createRegistry(3389);
            
            // Acá creamos una instancia de la implementación del servidor RMI.
            FileOps fileOps = new FileOpsImpl();
            
            // Registrar la implementación del servidor RMI.
            Naming.rebind("FileOpsServer", fileOps);
            
            System.out.println("Servidor RMI ejecutandose correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
