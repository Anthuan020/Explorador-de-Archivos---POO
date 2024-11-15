package model;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Desktop;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;



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

    if (!file.exists()) {
        return false; // No se puede eliminar si el archivo o directorio no existe
    }

    // Si es un directorio, eliminar todo su contenido de forma recursiva
    if (file.isDirectory()) {
        for (File subFile : file.listFiles()) {
            deleteFileOrDirectory(subFile.getAbsolutePath());
        }
    }
    
    // Una vez vacío, eliminar el directorio o el archivo
    return file.delete();
}
    
    public String getLogicProperties(String path){
        File unidad = new File(path);
        
        long totalSpace = unidad.getTotalSpace();
        long freeSpace = unidad.getFreeSpace();
        long usableSpace = unidad.getUsableSpace();
        
        StringBuilder info = new StringBuilder();

        info.append("Unidad lógica: ").append(unidad.getAbsolutePath()).append("\n");
        info.append("Tamaño total: ").append(totalSpace / (1024 * 1024 * 1024)).append(" GB").append("\n");
        info.append("Espacio libre: ").append( freeSpace / (1024 * 1024 * 1024)).append(" GB").append("\n");
        info.append("Espacio disponible para el usuario: ").append( usableSpace / (1024 * 1024 * 1024)).append(" GB").append("\n");
        return info.toString();
    }
    
    public void pasteElement(String origen, String destin)throws IOException {
        Path origin = Paths.get(origen);
        Path desti = Paths.get(destin);
        
         Path targetPath = Files.isDirectory(origin) ? desti.resolve(origin.getFileName()) : desti.resolve(origin.getFileName());
        
        if (Files.exists(targetPath)) {
            int response = JOptionPane.showConfirmDialog(null, 
                    "El archivo o directorio ya existe en el destino. ¿Desea sobrescribirlo?", 
                    "Confirmación de Sobrescritura", 
                    JOptionPane.YES_NO_OPTION, 
                    JOptionPane.WARNING_MESSAGE);

            if (response == JOptionPane.NO_OPTION) {
                return; // Salir sin sobrescribir
            }
        }
        
        if (Files.isDirectory(origin)) {
            copiarDirectorio(origen, destin);
        } else {
            if (Files.exists(desti) && Files.isDirectory(desti)) {
                Files.copy(origin, desti.resolve(origin.getFileName()), StandardCopyOption.REPLACE_EXISTING);
            } else {
                Files.copy(origin, desti, StandardCopyOption.REPLACE_EXISTING);
            }
        }
        
        
        
    }
    
    private static void copiarDirectorio(String origin, String destine) throws IOException {
        Path origen = Paths.get(origin);
        Path destino = Paths.get(destine);
        
        Path targetDir = destino.resolve(origen.getFileName());
        Files.createDirectories(targetDir);       

        Files.walkFileTree(origen, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path subDirectorioDestino = targetDir.resolve(origen.relativize(dir));
                if (Files.notExists(subDirectorioDestino)) {
                    Files.createDirectories(subDirectorioDestino);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path destinoArchivo = targetDir.resolve(origen.relativize(file));
                Files.copy(file, destinoArchivo, StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        });
    }
    
    public void openFile(String pfile) throws IOException {
        File file = new File(pfile);

        if (file.exists()) {
            Desktop.getDesktop().open(file);
        } else{
            throw new FileNotFoundException("El archivo no fue encontrado: " + pfile);
        }
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
