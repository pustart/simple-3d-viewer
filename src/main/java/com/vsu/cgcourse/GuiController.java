package com.vsu.cgcourse;

import com.vsu.cgcourse.model.Triangulation;
import com.vsu.cgcourse.objreader.ObjReader;
import com.vsu.cgcourse.objwriter.ObjWriter;
import com.vsu.cgcourse.renderengine.Transformations;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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

public class GuiController {
    final private float TRANSLATION = 0.5F;
    double alpha = 0.01;
    private boolean showPanelFlag = false;

    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;
    @FXML
    private VBox optionsPanel;
    @FXML
    private Slider rotateX;
    @FXML
    private Label rotateXLabel;
    @FXML
    private Slider rotateY;
    @FXML
    private Label rotateYLabel;
    @FXML
    private Slider rotateZ;
    @FXML
    private Label rotateZLabel;
    @FXML
    private Slider scaleX;
    @FXML
    private Label scaleXLabel;
    @FXML
    private Slider scaleY;
    @FXML
    private Label scaleYLabel;
    @FXML
    private Slider scaleZ;
    @FXML
    private Label scaleZLabel;
    @FXML
    private Slider offsetX;
    @FXML
    private Label offsetXLabel;
    @FXML
    private Slider offsetY;
    @FXML
    private Label offsetYLabel;
    @FXML
    private Slider offsetZ;
    @FXML
    private Label offsetZLabel;
    private Mesh meshToTransform = null;
    private Mesh originalMesh = null;
    private Transformations transformations;
    private final Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));
        changeArrowPolicy();
        optionsPanel.setVisible(false);
        Timeline timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        initializeSlider(scaleX, -3, 3, 0, TRANSLATION);
        initializeSlider(scaleY, -3, 3, 0, TRANSLATION);
        initializeSlider(scaleZ, -3, 3, 0, TRANSLATION);
        initializeSlider(rotateX, -10, 10, 0, alpha);
        initializeSlider(rotateY, -10, 10, 0, alpha);
        initializeSlider(rotateZ, -10, 10, 0, alpha);
        initializeSlider(offsetX, -10, 10, 0, TRANSLATION);
        initializeSlider(offsetY, -10, 10, 0, TRANSLATION);
        initializeSlider(offsetZ, -10, 10, 0, TRANSLATION);

        enableScaleX();
        enableScaleY();
        enableScaleZ();
        enableRotateX();
        enableRotateY();
        enableRotateZ();
        enableOffsetX();
        enableOffsetY();
        enableOffsetZ();

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));
            enableMouseScroll();

            if (meshToTransform != null) {
                transformations = new Transformations(originalMesh, meshToTransform);
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, transformations.getTransformed(), (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    private void initializeSlider(Slider slider, double min, double max, double currValue, double increment) {
        slider.setMin(min);
        slider.setMax(max);
        slider.setValue(currValue);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setBlockIncrement(increment);
    }

    private void enableScaleX() {
        scaleX.valueProperty().addListener((observable, oldValue, newValue) -> {
            scaleXLabel.setText("X: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                float nowValue = (float) (Math.abs((double) newValue - (double) oldValue) + 1);
                if (((double) newValue - (double) oldValue) > 0) {
                    transformations.scaleX(nowValue);
                } else {
                    transformations.scaleX(1 / nowValue);
                }
            }
        });
    }

    private void enableScaleY() {
        scaleY.valueProperty().addListener((observable, oldValue, newValue) -> {
            scaleYLabel.setText("Y: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                float nowValue = (float) (Math.abs((double) newValue - (double) oldValue) + 1);
                if (((double) newValue - (double) oldValue) > 0) {
                    transformations.scaleY(nowValue);
                } else {
                    transformations.scaleY(1 / nowValue);
                }
            }
        });
    }

    private void enableScaleZ() {
        scaleZ.valueProperty().addListener((observable, oldValue, newValue) -> {
            scaleZLabel.setText("Z: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                float nowValue = (float) (Math.abs((double) newValue - (double) oldValue) + 1);
                if (((double) newValue - (double) oldValue) > 0) {
                    transformations.scaleZ(nowValue);
                } else {
                    transformations.scaleZ(1 / nowValue);
                }
            }
        });
    }

    private void enableRotateX() {
        rotateX.valueProperty().addListener((observable, oldValue, newValue) -> {
            rotateXLabel.setText("X: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                transformations.rotateAboutX((Double) newValue - (Double) oldValue);
            }
        });
    }

    private void enableRotateY() {
        rotateY.valueProperty().addListener((observable, oldValue, newValue) -> {
            rotateYLabel.setText("Y: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                transformations.rotateAboutY((Double) newValue - (Double) oldValue);
            }
        });
    }

    private void enableRotateZ() {
        rotateZ.valueProperty().addListener((observable, oldValue, newValue) -> {
            rotateZLabel.setText("Z: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                transformations.rotateAboutZ((Double) newValue - (Double) oldValue);
            }
        });
    }

    private void enableOffsetX() {
        offsetX.valueProperty().addListener((observable, oldValue, newValue) -> {
            offsetXLabel.setText("X: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                transformations.offsetX((float) ((Double) newValue - (Double) oldValue));
            }
        });
    }

    private void enableOffsetY() {
        offsetY.valueProperty().addListener((observable, oldValue, newValue) -> {
            offsetYLabel.setText("Y: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                transformations.offsetY((float) ((Double) newValue - (Double) oldValue));
            }
        });
    }

    private void enableOffsetZ() {
        offsetZ.valueProperty().addListener((observable, oldValue, newValue) -> {
            offsetZLabel.setText("Z: " + String.format("%.3f", (Double) newValue));
            if (meshToTransform != null) {
                transformations.offsetZ((float) ((Double) newValue - (Double) oldValue));
            }
        });
    }

    private void enableMouseScroll() {
        anchorPane.setOnScroll(scrollEvent -> {
            double deltaY = -scrollEvent.getDeltaY();
            camera.movePosition(new Vector3f(0, 0, (float) deltaY / 5));
        });
    }

    private void changeArrowPolicy() {
        anchorPane.addEventFilter(
                KeyEvent.ANY,
                event -> {
                    switch (event.getCode()) {
                        case LEFT -> {
                            event.consume();
                            camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(TRANSLATION, 0, 0)).getCoordinates()));
                            camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
                        }
                        case RIGHT -> {
                            event.consume();
                            camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(-TRANSLATION, 0, 0)).getCoordinates()));
                            camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
                        }
                        case UP -> {
                            event.consume();
                            camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(0, TRANSLATION, 0)).getCoordinates()));
                            camera.movePosition(new Vector3f(0, TRANSLATION, 0));
                        }
                        case DOWN -> {
                            event.consume();
                            camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(0, -TRANSLATION, 0)).getCoordinates()));
                            camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
                        }
                    }
                });
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
            meshToTransform = ObjReader.read(fileContent);
            originalMesh = ObjReader.read(fileContent);
            optionsPanel.setVisible(true);
            resetSliders();
        } catch (IOException exception) {
            // todo: обработка ошибок
        }
    }

    private void resetSliders() {
        scaleX.setValue(0);
        scaleY.setValue(0);
        scaleZ.setValue(0);
        rotateX.setValue(0);
        rotateY.setValue(0);
        rotateZ.setValue(0);
        offsetX.setValue(0);
        offsetY.setValue(0);
        offsetZ.setValue(0);
    }

    @FXML
    public void onSaveOriginalModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".obj", "*.obj"));
        fileChooser.setTitle("Save model");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file != null && meshToTransform != null) {
            ObjWriter.write(transformations.getOriginal(), file);
            transformations.back();
            ObjWriter.write(transformations.getTransformed(), file);
        }
    }

    @FXML
    public void onSaveModifiedModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".obj", "*.obj"));
        fileChooser.setTitle("Save model");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file != null && meshToTransform != null) {
            ObjWriter.write(transformations.getTransformed(), file);
        }
    }

    @FXML
    public void handleCameraForward() {
        camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(0, 0, -TRANSLATION)).getCoordinates()));
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward() {
        camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(0, 0, TRANSLATION)).getCoordinates()));
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft() {
        camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(TRANSLATION, 0, 0)).getCoordinates()));
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight() {
        camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(-TRANSLATION, 0, 0)).getCoordinates()));
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp() {
        camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(0, TRANSLATION, 0)).getCoordinates()));
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown() {
        camera.setTarget(new Vector3f(camera.getTarget().addition(new Vector3f(0, -TRANSLATION, 0)).getCoordinates()));
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }

    @FXML
    public void handleModelXForward() {
        if (meshToTransform != null) {
            transformations.rotateAboutX(alpha);
        }
    }

    @FXML
    public void handleModelXBackward() {
        if (meshToTransform != null) {
            transformations.rotateAboutX(-alpha);
        }
    }

    @FXML
    public void handleModelYLeft() {
        if (meshToTransform != null) {
            transformations.rotateAboutY(alpha);
        }
    }

    @FXML
    public void handleModelYRight() {
        if (meshToTransform != null) {
            transformations.rotateAboutY(-alpha);
        }
    }

    @FXML
    public void handleModelZRight() {
        if (meshToTransform != null) {
            transformations.rotateAboutZ(alpha);
        }
    }

    @FXML
    public void handleModelZLeft() {
        if (meshToTransform != null) {
            transformations.rotateAboutZ(-alpha);
        }
    }

    @FXML
    public void handleStretchModelX() {
        if (meshToTransform != null) {
            transformations.scaleX(1 / TRANSLATION);
        }
    }

    @FXML
    public void handleCompressModelX() {
        if (meshToTransform != null) {
            transformations.scaleX(TRANSLATION);
        }
    }

    @FXML
    public void handleStretchModelY() {
        if (meshToTransform != null) {
            transformations.scaleY(1 / TRANSLATION);
        }
    }

    @FXML
    public void handleCompressModelY() {
        if (meshToTransform != null) {
            transformations.scaleY(TRANSLATION);
        }
    }

    @FXML
    public void handleStretchModelZ() {
        if (meshToTransform != null) {
            transformations.scaleZ(1 / TRANSLATION);
        }
    }

    @FXML
    public void handleCompressModelZ() {
        if (meshToTransform != null) {
            transformations.scaleZ(TRANSLATION);
        }
    }

    @FXML
    public void handleOffsetPlusModelX() {
        if (meshToTransform != null) {
            transformations.offsetX(TRANSLATION);
        }
    }

    @FXML
    public void handleOffsetMinusModelX() {
        if (meshToTransform != null) {
            transformations.offsetX(-TRANSLATION);
        }
    }

    @FXML
    public void handleOffsetPlusModelY() {
        if (meshToTransform != null) {
            transformations.offsetY(TRANSLATION);
        }
    }

    @FXML
    public void handleOffsetMinusModelY() {
        if (meshToTransform != null) {
            transformations.offsetY(-TRANSLATION);
        }
    }

    @FXML
    public void handleOffsetPlusModelZ() {
        if (meshToTransform != null) {
            transformations.offsetZ(TRANSLATION);
        }
    }

    @FXML
    public void handleOffsetMinusModelZ() {
        if (meshToTransform != null) {
            transformations.offsetZ(-TRANSLATION);
        }
    }

    @FXML
    public void handleCameraTurnDown() {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }

    @FXML
    public void handleCameraTurnUp() {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraTurnLeft() {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraTurnRight() {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void resetModel() {
        resetSliders();
        if (meshToTransform != null) {
            transformations.back();
        }
    }

    @FXML
    public void options() {
        if (!showPanelFlag) {
            optionsPanel.setVisible(true);
            showPanelFlag = true;
        } else {
            optionsPanel.setVisible(false);
            showPanelFlag = false;
        }
    }

    @FXML
    public void triangulateModel() {
        if (meshToTransform != null && meshToTransform.getPolygons().size() != 0) {
            meshToTransform.setPolygons(Triangulation.polygonsTriangulation(meshToTransform.getPolygons()));
        }
    }
}
