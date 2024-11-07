import model.FileManager;
import view.FileManagerView;
import controller.FileManagerController;

public class Main {
    public static void main(String[] args) {
        // Crear instancias del modelo y la vista
        FileManager model = new FileManager();
        FileManagerView view = new FileManagerView();

        // Crear el controlador pasando el modelo y la vista
        new FileManagerController(model, view);

        // Hacer visible la vista
        view.setVisible(true);
    }
}
