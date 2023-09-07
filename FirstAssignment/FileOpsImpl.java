import java.io.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

//Implemetación completo de todos los métodos del FileOps.
public class FileOpsImpl extends UnicastRemoteObject implements FileOps {
    private static final String SERVER_DIR = "server_files/";

    public FileOpsImpl() throws RemoteException {
        // Acá hacemos llamado a un constructor.
        super(); 
        // Puede inicializar recursos aquí si es necesario
        // Acá creamos un directorio para guardar los archivos si aun no existe.
        File serverDir = new File(SERVER_DIR);
        if (!serverDir.exists()) {
            if (serverDir.mkdirs()) {
                System.out.println("Se ha creado el directorio del servidor en: " + SERVER_DIR);
            } else {
                throw new RemoteException("Error! a la hora de crear el directorio del servidor.");
            }
        }
    }

    @Override
    public byte[] readFile(String filename) throws RemoteException {
        try {
            File file = new File(SERVER_DIR + filename);
            if (!file.exists()) {
                // Por si es que el archivo no existe en el servidor
                return null; 
            }

            byte[] data = new byte[(int) file.length()];
            FileInputStream fis = new FileInputStream(file);
            fis.read(data);
            fis.close();

            return data;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Error! a la hora de leer el archivo del servidor.");
        }
    }

    @Override
    public void writeFile(String filename, byte[] data) throws RemoteException {
        try {
            File file = new File(SERVER_DIR + filename);
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Error! a la hora de escribir el archivo en el servidor.");
        }
    }

    @Override
    public int deleteFile(String filename) throws RemoteException {
        File file = new File(SERVER_DIR + filename);
        if (file.exists() && file.delete()) {
            // Por si se logra borrar exitosamente el archivo.
            return 1; 
        } else {
            // Por si no se logró encontrar el archivo a borrar.
            return 0; 
        }
    }

    @Override
    public String[] listFiles() throws RemoteException {
        File serverDir = new File(SERVER_DIR);
        File[] files = serverDir.listFiles();
        List<String> fileList = new ArrayList<>();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileList.add(file.getName());
                }
            }
        }

        return fileList.toArray(new String[0]);
    }

    @Override
    public int renameFile(String currentFilename, String newFilename) throws RemoteException {
        File currentFile = new File(SERVER_DIR + currentFilename);
        File newFile = new File(SERVER_DIR + newFilename);
    
        if (!currentFile.exists() || newFile.exists()) {
            return 0; // Fallo: el archivo actual no existe o el nuevo archivo ya existe
        }
    
        if (currentFile.renameTo(newFile)) {
            return 1; // Éxito: archivo renombrado
        } else {
            return 0; // Fallo: no se pudo renombrar el archivo
        }
    }
}
