package co.edu.uniquindio.uniEventos.modelo;

import co.edu.uniquindio.uniEventos.modelo.Evento;
import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ImageTableCell extends TableCell<Evento, File> {
    private final ImageView imageView = new ImageView();

    @Override
    protected void updateItem(File item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setGraphic(null);
        } else {
            Image image = new Image(item.toURI().toString());
            imageView.setImage(image);
            imageView.setFitWidth(120); // Ajusta el ancho de la imagen según sea necesario
            imageView.setFitHeight(100); // Ajusta la altura de la imagen según sea necesario
            setGraphic(imageView);
        }
    }
}
