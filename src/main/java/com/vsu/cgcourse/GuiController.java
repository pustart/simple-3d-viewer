package com.vsu.cgcourse;

import com.vsu.cgcourse.objreader.ObjReader;
import com.vsu.cgcourse.objwriter.ObjWriter;
import com.vsu.cgcourse.renderengine.Transformations;
import com.vsu.cgcourse.vectormath.Matrix3x3;
import com.vsu.cgcourse.vectormath.Matrix4x4;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import com.vsu.cgcourse.vectormath.Vector3f;

import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.renderengine.Camera;
import com.vsu.cgcourse.renderengine.RenderEngine;

import static com.vsu.cgcourse.renderengine.GraphicConveyor.*;

public class GuiController {
    final private float TRANSLATION = 0.5F;

    double alpha = 0.01;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Mesh mesh = null;

    private Transformations transformations = new Transformations(mesh);

    private Camera camera = new Camera(
            new Vector3f(0, 00, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                transformations.setOriginal(mesh);
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
        } catch (IOException exception) {
            // todo: обработка ошибок
        }
    }

    public void onSaveModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".obj", "*.obj"));
        fileChooser.setTitle("Save model");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file != null) {
            ObjWriter.write(mesh, file);
        }
    }

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }


    @FXML
    public void handleModelXForward(ActionEvent actionEvent) {
        transformations.rotateAboutX(alpha);
    }

    @FXML
    public void handleModelXBackward(ActionEvent actionEvent) {
        transformations.rotateAboutX(-alpha);
    }

    @FXML
    public void handleModelYLeft(ActionEvent actionEvent) {
        transformations.rotateAboutY(alpha);
    }

    @FXML
    public void handleModelYRight(ActionEvent actionEvent) {
        transformations.rotateAboutY(-alpha);
    }

    @FXML
    public void handleModelZRight(ActionEvent actionEvent) {
        transformations.rotateAboutZ(alpha);
    }

    @FXML
    public void handleModelZLeft(ActionEvent actionEvent) {
        transformations.rotateAboutZ(-alpha);
    }

    @FXML
    public void handleStretchModelX(ActionEvent actionEvent) {
        transformations.scaleX(1/TRANSLATION);
    }

    @FXML
    public void handleCompressModelX(ActionEvent actionEvent) {
        transformations.scaleX(TRANSLATION);
    }

    @FXML
    public void handleStretchModelY(ActionEvent actionEvent) {
        transformations.scaleY(1/TRANSLATION);
    }

    @FXML
    public void handleCompressModelY(ActionEvent actionEvent) {
        transformations.scaleY(TRANSLATION);
    }

    @FXML
    public void handleStretchModelZ(ActionEvent actionEvent) {
        transformations.scaleZ(1/TRANSLATION);
    }

    @FXML
    public void handleCompressModelZ(ActionEvent actionEvent) {
        transformations.scaleZ(TRANSLATION);
    }

    @FXML
    public void handleOffsetPlusModelX(ActionEvent actionEvent) {
        transformations.offsetX(TRANSLATION);
    }

    @FXML
    public void handleOffsetMinusModelX(ActionEvent actionEvent) {
        transformations.offsetX(-TRANSLATION);
    }

    @FXML
    public void handleOffsetPlusModelY(ActionEvent actionEvent) {
        transformations.offsetY(TRANSLATION);
    }

    @FXML
    public void handleOffsetMinusModelY(ActionEvent actionEvent) {
        transformations.offsetY(-TRANSLATION);
    }

    @FXML
    public void handleOffsetPlusModelZ(ActionEvent actionEvent) {
        transformations.offsetZ(TRANSLATION);
    }

    @FXML
    public void handleOffsetMinusModelZ(ActionEvent actionEvent) {
        transformations.offsetZ(-TRANSLATION);
    }
}