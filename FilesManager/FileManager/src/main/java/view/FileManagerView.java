package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

public class FileManagerView extends JFrame {
    private JTextField pathField;
    private JTextArea outputArea;
    private JButton listButton;
    private JButton createDirButton;
    private JButton deleteButton;
    private JButton infoButton;
    private JButton logicButton;
    private JList<String> fileList;
    private JButton backButton;
    private JButton copyButton;
    private JButton pasteButton;
    

    public FileManagerView() {
        // Configurar la ventana principal
        setTitle("Gestor de Archivos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Campo de entrada para el camino
        pathField = new JTextField("C:\\");  // Cambia a "C:\\" para evitar problemas de escape
        add(pathField, BorderLayout.NORTH);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        listButton = new JButton("Listar");
        createDirButton = new JButton("Crear Directorio");
        deleteButton = new JButton("Eliminar");
        infoButton = new JButton("Propiedades");
        logicButton = new JButton("Propiedades U.L.");
        backButton = new JButton("Atras");
        copyButton = new JButton ("Copiar");
        pasteButton = new JButton ("Pegar");
        buttonPanel.add(listButton);
        buttonPanel.add(createDirButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(copyButton);
        buttonPanel.add(pasteButton);
        buttonPanel.add(infoButton);
        buttonPanel.add(logicButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Lista de archivos y área de salida
        fileList = new JList<>();
        outputArea = new JTextArea();
        outputArea.setEditable(false);

        // Panel de archivos y área de salida
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(fileList), new JScrollPane(outputArea));
        add(splitPane, BorderLayout.CENTER);
    }

    // Métodos para configurar los listeners
    public void addListButtonListener(ActionListener listener) {
        listButton.addActionListener(listener);
    }

    public void addCreateDirButtonListener(ActionListener listener) {
        createDirButton.addActionListener(listener);
    }

    public void addDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void addInfoButtonListener(ActionListener listener) {
        infoButton.addActionListener(listener);
    }

    public void addFileListClickListener(MouseListener listener) {
        fileList.addMouseListener(listener);
    }
    
    public void addLogicButtonListener(ActionListener listener) {
        logicButton.addActionListener(listener);
    }
    
    public void addBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    
    public void addCopyButtonListener(ActionListener listener) {
        copyButton.addActionListener(listener);
    }
    
    public void addPasteButtonListener(ActionListener listener) {
        pasteButton.addActionListener(listener);
    }
    
    // Métodos para interactuar con los elementos de la interfaz
    public String getPathInput() {
        return pathField.getText();
    }

    public void setPathFieldText(String path) {
        pathField.setText(path);
    }

    public void setFileList(String[] files) {
        fileList.setListData(files);
    }

    public String getSelectedFile() {
        return fileList.getSelectedValue();
    }

    public void setOutputText(String text) {
        outputArea.setText(text);
    }
}
