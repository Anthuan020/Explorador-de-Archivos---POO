package model;

import java.io.File;

public class FileManager {

    public File[] listDirectoryContents(String path) {
        File directory = new File(path);
        return (directory.isDirectory()) ? directory.listFiles() : null;
    }

    public boolean createDirectory(String path, String dirName) {
        File newDir = new File(path, dirName);
        return newDir.mkdir();
    }

    public boolean deleteFileOrDirectory(String path) {
        File file = new File(path);
        return file.exists() && file.delete();
    }

    public String getFileInfo(String path) {
        File file = new File(path);
        if (file.exists()) {
            StringBuilder info = new StringBuilder();
            info.append("Nombre: ").append(file.getName()).append("\n");
            info.append("Ruta: ").append(file.getPath()).append("\n");
            info.append("Tamaño: ").append(file.length()).append(" bytes\n");
            info.append("Directorio: ").append(file.isDirectory() ? "Sí" : "No").append("\n");
            info.append("Lectura: ").append(file.canRead() ? "Sí" : "No").append("\n");
            info.append("Escritura: ").append(file.canWrite() ? "Sí" : "No").append("\n");
            return info.toString();
        }
        return "Archivo o directorio no encontrado.";
    }
}
