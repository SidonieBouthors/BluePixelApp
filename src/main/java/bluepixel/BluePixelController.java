package bluepixel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BluePixelController {
    @FXML
    public Label selectedImageToExtract;
    public Label selectedImageToInsertIn;
    public TextField newImageName;
    public TextArea messageToInsert;
    public Text extractWarning;
    public Text insertWarning;
    public Text extractedMessage;


    private Stage stage;
    private File fileToExtract;
    private File fileToInsert;

    @FXML
    public void selectExtractImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        fileToExtract = fileChooser.showOpenDialog(stage);
        if (fileToExtract!=null){
            selectedImageToExtract.setText(fileToExtract.getName());
            System.out.println("Selected File : " + fileToExtract);
        }
    }

    @FXML
    public void handleExtractButtonAction(ActionEvent actionEvent) {
        try {
            if (fileToExtract != null) {
                BufferedImage image = ImageIO.read(fileToExtract);
                String text = Steganographer.extract(image);
                extractedMessage.setText(text);
                System.out.println("Extracted Text :\n");
                System.out.println(text);
            }
        }
        catch (IOException e){
            extractWarning.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void selectInsertImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        fileToInsert = fileChooser.showOpenDialog(stage);
        if (fileToInsert!=null){
            selectedImageToInsertIn.setText(fileToInsert.getName());
            System.out.println("Selected File : " + fileToInsert);
        }
    }
    @FXML
    public void handleInsertButtonAction(ActionEvent actionEvent) {
        try {
            if (fileToInsert!=null) {
                DirectoryChooser dirChooser = new DirectoryChooser();
                dirChooser.setInitialDirectory(fileToInsert.getParentFile());
                File directory = dirChooser.showDialog(stage);
                System.out.println(directory.getPath());
                String message = messageToInsert.getText();
                String imageName = newImageName.getText();
                String newImagePath;
                if (imageName.length() > 4 && imageName.endsWith(".png")) {
                    newImagePath = directory.getPath() + imageName;
                } else {
                    newImagePath = directory.getPath() + imageName + ".png";
                }
                BufferedImage newImage = Steganographer.insert(ImageIO.read(fileToInsert), message);
                ImageIO.write(newImage, "png", new File(newImagePath));
            }
        }
        catch (IOException e){
            insertWarning.setText(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void setStage(Stage stage){ this.stage = stage;}


}