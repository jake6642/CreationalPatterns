package factoryMethodDemo;

import java.io.InputStream;

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
	Shape3D shape;

	VBox mainBox;
	VBox shapeBox;
	ComboBox combo;
	final String BOX = "Box";
	final String CYL = "Cylinder";
	final String SPH = "Sphere";
	final String TRI = "Triangular Prism";
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

	public void buildShape(String type) {
		shape = factoryMethod(type);
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
			buildShape(shapeType);
/*************************************************************/			
			resetSlides();
			shape.setTranslateY(100);
			shapeBox.getChildren().add(shape);
		});

		// slider for x rotation
		xSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
					rotateIt(Rotate.X_AXIS, -new_val.doubleValue());
				else
					rotateIt(Rotate.X_AXIS, new_val.doubleValue());
			}
		});
		// slider for y rotation
		ySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
					rotateIt(Rotate.Y_AXIS, -new_val.doubleValue());
				else
					rotateIt(Rotate.Y_AXIS, new_val.doubleValue());
			}
		});
		// slider for z rotation
		zSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
					rotateIt(Rotate.Z_AXIS, -new_val.doubleValue());
				else
					rotateIt(Rotate.Z_AXIS, new_val.doubleValue());
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buildBoxes() {
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
		ObservableList<String> options = FXCollections.observableArrayList(BOX, CYL, SPH, TRI);
		combo = new ComboBox(options);
		combo.autosize();
		// set the default value
		combo.setValue(BOX);

		mainBox.getChildren().addAll(xSlider, ySlider, zSlider, combo, shapeBox);
		shape = factoryMethod(BOX);
		resetSlides();
		shape.setTranslateY(100);
		shapeBox.getChildren().add(shape);

	}

	/*
	 * The whole point of this exercise. The factory method that instantiates the
	 * class appropriately
	 */
	Shape3D factoryMethod(String type) {
		switch (type) {
		case BOX:
			return new Box(200, 200, 200);
		case CYL:
			return new Cylinder(150, 300, 300);
		case SPH:
			return new Earth(150);
		case TRI:
			return new Prism(150, 300);
		default:
			return new Box();
		}
	}

	/* Class that extends the cylinder to show a triangular prism */
	public class Prism extends Cylinder {

		public Prism(double rad, double height) {
			// create a cylinder with 3 sides
			super(rad, height, 3);
		}
	}

	/* Class that extends the Sphere to prove a point with the factory method */
	public class Earth extends Sphere {
		public Earth(double rad) {
			super(rad);
			setEarth(this);
		}
	}

	/* Method to reset the sliders to 0 */
	private void resetSlides() {
		xSlider.setValue(0);
		ySlider.setValue(0);
		zSlider.setValue(0);
	}

	/* method that rotates based on slider input */
	private void rotateIt(Point3D dir, double val) {
		Rotate rotateAbout = new Rotate(val, dir);
		shape.getTransforms().add(rotateAbout);
	}

	/* This method applies the earth skin to the sphere */
	private void setEarth(Shape3D earth) {

		InputStream diffuse = getClass().getClassLoader().getResourceAsStream("texture.jpg");
		InputStream bump = getClass().getClassLoader().getResourceAsStream("normal.jpg");
		InputStream specular = getClass().getClassLoader().getResourceAsStream("specular.jpg");

		PhongMaterial earthMaterial = new PhongMaterial();
		earthMaterial.setDiffuseMap(new Image(diffuse));
		earthMaterial.setBumpMap(new Image(bump));
		earthMaterial.setSpecularMap(new Image(specular));

		earth.setMaterial(earthMaterial);
	}
}
