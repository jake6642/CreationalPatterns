package factoryMethodDemo;

import java.io.InputStream;
import factoryMethodDemo.BuildShape;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class FactoryMethod extends Application {

	// globally declared shape


	VBox mainBox;
	VBox shapeBox;
	ComboBox combo;
	BuildShape buildShape;
	Slider xSlider;
	Slider ySlider;
	Slider zSlider;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {

		buildBoxes();
		setListeners();
		Scene scene = new Scene(mainBox, 600, 600);
		/* Set the Stage */
		stage.setScene(scene);
		stage.setMinHeight(scene.getHeight() + 20);
		stage.setMinWidth(scene.getWidth() + 10);
		stage.setTitle("Factory Demo");
		stage.show();
	}

	
	
	@SuppressWarnings("unchecked")
	public void setListeners() {
		
		// listener for combobox
		combo.setOnAction((Event ev) -> {
			if (shapeBox.getChildren().size() > 0) {
				shapeBox.getChildren().remove(shapeBox.getChildren().size() - 1);
			}
			String shapeType = combo.getSelectionModel().getSelectedItem().toString();
/************* The whole point of this exercise***************/			
			buildShape.buildShap(shapeType);
/*************************************************************/			
			resetSlides();
			buildShape.shape.setTranslateY(100);
			shapeBox.getChildren().add(buildShape.shape);
		});

		// slider for x rotation
		xSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
				buildShape.rotateIt(Rotate.X_AXIS, -new_val.doubleValue());
				else
					buildShape.rotateIt(Rotate.X_AXIS, new_val.doubleValue());
			}
		});
		// slider for y rotation
		ySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
					buildShape.rotateIt(Rotate.Y_AXIS, -new_val.doubleValue());
				else
					buildShape.rotateIt(Rotate.Y_AXIS, new_val.doubleValue());
			}
		});
		// slider for z rotation
		zSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
					buildShape.rotateIt(Rotate.Z_AXIS, -new_val.doubleValue());
				else
					buildShape.rotateIt(Rotate.Z_AXIS, new_val.doubleValue());
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buildBoxes() {
		BuildShape buildShape = new BuildShape();
		mainBox = new VBox();
		shapeBox = new VBox();
		Insets inset = new Insets(5, 10, 5, 10);
		mainBox.setAlignment(Pos.TOP_CENTER);
		mainBox.setBackground(
				new Background(new BackgroundFill(Color.color(.1f, .1f, .15f), CornerRadii.EMPTY, Insets.EMPTY)));
		shapeBox.setPadding(new Insets(50, 50, 50, 50));
		shapeBox.setAlignment(Pos.BOTTOM_CENTER);
		/* Create the slides */
		xSlider = new Slider(0, 3.6, 0);
		ySlider = new Slider(0, 3.6, 0);
		zSlider = new Slider(0, 3.6, 0);
		xSlider.setPadding(inset);
		ySlider.setPadding(inset);
		zSlider.setPadding(inset);
		/* Add the items to the combobox */
		ObservableList<String> options = FXCollections.observableArrayList(buildShape.BOX, buildShape.CYL, buildShape.SPH, buildShape.TRI);
		combo = new ComboBox(options);
		combo.autosize();
		// set the default value
		combo.setValue(buildShape.BOX);

		mainBox.getChildren().addAll(xSlider, ySlider, zSlider, combo, shapeBox);
		buildShape.shape = buildShape.factoryMethod(buildShape.BOX);
		resetSlides();
		buildShape.shape.setTranslateY(100);
		shapeBox.getChildren().add(buildShape.shape);

	}

	/*
	 * The whole point of this exercise. The factory method that instantiates the
	 * class appropriately
	 */
	

	/* Class that extends the cylinder to show a triangular prism */
	

	/* Method to reset the sliders to 0 */
	private void resetSlides() {
		xSlider.setValue(0);
		ySlider.setValue(0);
		zSlider.setValue(0);
	}

	/* method that rotates based on slider input */
	

	/* This method applies the earth skin to the sphere */
	
}
