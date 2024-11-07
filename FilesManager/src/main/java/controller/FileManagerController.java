package controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import model.FileManager;
import view.FileManagerView;

public class FileManagerController {
    private FileManager model;
    private FileManagerView view;

    public FileManagerController(FileManager model, FileManagerView view) {
        this.model = model;
        this.view = view;

        // Configurar listeners para los botones y la lista
        this.view.addListButtonListener(new ButtonActionListener("Listar"));
        this.view.addCreateDirButtonListener(new ButtonActionListener("Crear Directorio"));
        this.view.addDeleteButtonListener(new ButtonActionListener("Eliminar"));
        this.view.addInfoButtonListener(new ButtonActionListener("Propiedades"));
        this.view.addBackButtonListener(new ButtonActionListener("Atras"));
        this.view.addFileListClickListener(new FileListClickListener());
    
        updateFileList("C:\\");
    }

    // ActionListener genérico para manejar diferentes botones con switch-case
    private class ButtonActionListener implements ActionListener {
        private String command;

        public ButtonActionListener(String command) {
            this.command = command;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (command) {
                case "Listar":
                    updateFileList(view.getPathInput());
                    break;
                case "Atras":
                    String currentDir = view.getPathInput(); // Obtiene la dirección actual
                    File currentFile = new File(currentDir);
                    if (currentFile.getParent() != null) { // Verifica que no sea el directorio raíz
                        String parentDir = currentFile.getParent(); // Obtiene el directorio padre
                        view.setPathFieldText(parentDir); // Actualiza el campo de texto con el directorio padre
                        updateFileList(parentDir); // Lista el contenido del directorio padre
                    } else {
                        view.setOutputText("Ya estás en el directorio raíz.");
                    }
                    break;  
                case "Crear Directorio":
                    String path = view.getPathInput();
                    String dirName = JOptionPane.showInputDialog(view, "Ingrese el nombre del nuevo directorio:");
                    if (dirName != null && !dirName.trim().isEmpty()) {
                        boolean success = model.createDirectory(path, dirName.trim());
                        view.setOutputText(success ? "Directorio creado exitosamente." : "No se pudo crear el directorio.");
                        updateFileList(path);
                    } else {
                        view.setOutputText("El nombre del directorio no puede estar vacío.");
                    }
                    break;
                case "Eliminar":
                    String selectedFile = view.getSelectedFile();
                    if (selectedFile != null) {
                        String deletePath = view.getPathInput() + "\\" + selectedFile;
                        int confirmation = JOptionPane.showConfirmDialog(view, "¿Está seguro de que desea eliminar " + selectedFile + "?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                        if (confirmation == JOptionPane.YES_OPTION) {
                            boolean success = model.deleteFileOrDirectory(deletePath);
                            view.setOutputText(success ? selectedFile + " eliminado exitosamente." : "No se pudo eliminar " + selectedFile + ".");
                            updateFileList(view.getPathInput());
                        }
                    } else {
                        view.setOutputText("Seleccione un archivo o directorio para eliminar.");
                    }
                    break;
                case "Propiedades":
                    String fileInfo = view.getSelectedFile();
                    if (fileInfo != null) {
                        String infoPath = view.getPathInput() + "\\" + fileInfo;
                        view.setOutputText(model.getFileInfo(infoPath));
                    } else {
                        view.setOutputText("Seleccione un archivo o directorio para ver sus propiedades.");
                    }
                    break;
                default:
                    view.setOutputText("Acción desconocida.");
                    break;
            }
        }
    }

    // Listener para manejar clics en la lista de archivos y carpetas
    private class FileListClickListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) { // Doble clic para abrir carpeta
                String selectedFile = view.getSelectedFile();
                if (selectedFile != null) {
                    String newPath = view.getPathInput() + "\\" + selectedFile;
                    File file = new File(newPath);
                    if (file.isDirectory()) {
                        view.setOutputText("Abriendo carpeta: " + newPath);
                        view.setPathFieldText(newPath); // Actualizar ruta en la vista
                        updateFileList(newPath);
                    }
                }
            }
        }
    }

    // Método para actualizar la lista de archivos y carpetas en la vista
    private void updateFileList(String path) {
        File[] contents = model.listDirectoryContents(path);
        if (contents != null) {
            String[] fileNames = new String[contents.length];
            for (int i = 0; i < contents.length; i++) {
                fileNames[i] = contents[i].getName();
            }
            view.setFileList(fileNames);
        } else {
            view.setOutputText("No se pudo acceder al directorio o no es válido.");
        }
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        FileManager model = new FileManager();
        FileManagerView view = new FileManagerView();
        new FileManagerController(model, view);

        view.setVisible(true);
    }
}
