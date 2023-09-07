import java.io.*;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Debes por favor proporcionar algun argumento para seleccionar una opcion del Servidor.");
            return;
        }

        String action = args[0].toLowerCase(); // Obtener el primer argumento y convertirlo a minúsculas

        try {
            // Obtener una referencia al objeto remoto del servidor
                // Prueba #1
                FileOps fileOps = (FileOps) Naming.lookup("rmi://localhost/FileOpsServer");
                // Prueba #2: 
                // FileOps fileOps = (FileOps) Naming.lookup("rmi://ipEjemplo/FileOpsServer");

            // Realizar la acción seleccionada
            switch (action) {
                case "download":
                    if (args.length < 2) {
                        System.out.println("Por favor debes proporcionar el nombre del archivo a descargar desde el servidor al cliente.");
                        return;
                    }
                    String filenameToDownload = args[1];
                    byte[] downloadedData = fileOps.readFile(filenameToDownload);
                    if (downloadedData != null) {
                        // Acá se procesa el archivo descargado.
                        saveToFile(filenameToDownload, downloadedData);
                        System.out.println("Felicidades! Archivo descargado exitosamente.");
                    } else {
                        System.out.println("Oh! El archivo no existe en nuestro servidor, intenta con otro nombre.");
                    }
                    break;
                case "upload":
                    if (args.length < 2) {
                        System.out.println("Por favor recuerda que debes proporcionar el nombre del archivo para subir a nuestro servidor.");
                        return;
                    }
                    String filenameToUpload = args[1];
                    byte[] dataToUpload = readFromFile(filenameToUpload);
                    fileOps.writeFile(filenameToUpload, dataToUpload);
                    System.out.println("Felicidades! Archivo subido exitosamente.");
                    break;
                case "delete":
                    if (args.length < 2) {
                        System.out.println("Oh! Recuerda que debes proporcionar el nombre del archivo para borrar dentro de nuestro servidor.");
                        return;
                    }
                    String filenameToDelete = args[1];
                    int deleteResult = fileOps.deleteFile(filenameToDelete);
                    if (deleteResult == 1) {
                        System.out.println("Felicidades! El archivo se ha borrado exitosamente.");
                    } else {
                        System.out.println("Oh! lamentablemente el archivo no existe en el servidor o no se pudo borrar, intenta de nuevo.");
                    }
                    break;
                case "list":
                    // Acá se listan todos los archivos.
                    String[] fileList = fileOps.listFiles();
                    System.out.println("Por supuesto! Estos son nuestros archivos disponibles en el servidor:");
                    for (String file : fileList) {

                        System.out.println("   -" + file);
                    }
                    break;
                case "rename":
                    if (args.length < 3) {
                        System.out.println("Recuerda que debes proporcionar el nombre del archivo actual que ya descargaste y el nuevo nombre que le quieres dar.");
                        return;
                    }
                    String currentFilename = args[1];
                    String newFilename = args[2];
                    int renameResult = fileOps.renameFile(currentFilename, newFilename);
                    if (renameResult == 1) {
                        System.out.println("Felicidades! Tu archivo ha sido renombrado exitosamente.");
                    } else {
                        System.out.println("Oh! No se pudo renombrar el archivo o el archivo no existe en el servidor.");
                    }
                    break;
                default:
                    System.out.println("Oh! Acción no válida. Recuerda que las acciones válidas son: download, upload, delete, list, rename.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para guardar los datos de un archivo seleccionado.
    private static void saveToFile(String filename, byte[] data) {
        try (FileOutputStream fos = new FileOutputStream(filename)) {
            fos.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para leer los datos de un archivo seleccionado.
    private static byte[] readFromFile(String filename) {
        try (FileInputStream fis = new FileInputStream(filename)) {
            byte[] data = new byte[fis.available()];
            fis.read(data);
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
