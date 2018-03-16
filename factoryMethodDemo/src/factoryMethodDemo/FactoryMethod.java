package factoryMethodDemo;

import java.io.File;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class FactoryMethod extends Application {

	//globally declared shape 
	Shape3D shape;
	
	VBox mainBox;
	VBox shapeBox;
	final String BOX = "Box";
	final String CYL = "Cylinder";
	final String SPH = "Sphere";
	Slider xSlider;
	Slider ySlider;
	Slider zSlider;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		
		buildBoxes();
		Scene scene = new Scene(mainBox, 600, 600);
		//setupCamera(scene);
		/* Set the Stage */
		stage.setScene(scene);
		stage.setMinHeight(scene.getHeight() + 10);
		stage.setMinWidth(scene.getWidth() + 10);
		stage.setTitle("Factory Demo");
		stage.show();
	}
	
	private void setupCamera(Scene scene) {
	      //Setting camera 
	      PerspectiveCamera camera = new PerspectiveCamera(false); 
	      camera.setTranslateX(0); 
	      camera.setTranslateY(0); 
	      camera.setTranslateZ(0); 
	      scene.setCamera(camera); 
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void buildBoxes() {
		mainBox = new VBox();
		shapeBox = new VBox();
		mainBox.setAlignment(Pos.TOP_CENTER);
		shapeBox.setPadding(new Insets(50,50,50,50));
		shapeBox.setAlignment(Pos.BOTTOM_CENTER);
		/* Create the slides */
		xSlider = new Slider(0, 3.14, 0);
		ySlider = new Slider(0, 3.14, 0);
		zSlider = new Slider(0, 3.14, 0);
		
		ObservableList<String> options = 
			    FXCollections.observableArrayList(BOX, CYL, SPH);
		ComboBox combo = new ComboBox (options);
		combo.autosize();
		combo.setValue(BOX);

		mainBox.getChildren().addAll(xSlider, ySlider, zSlider, combo, shapeBox);
		factoryMethod(BOX);
		
		
		//listener for combobox
		combo.setOnAction((Event ev) -> {
			if(shapeBox.getChildren().size() > 0) {
				shapeBox.getChildren().remove(shapeBox.getChildren().size()-1);
			}
		    factoryMethod(combo.getSelectionModel().getSelectedItem().toString());    
		});
		
		// slider for rotation
		xSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
					rotateIt(Rotate.X_AXIS, -new_val.doubleValue());
				else
					rotateIt(Rotate.X_AXIS, new_val.doubleValue());
			}
		});
		// slider for rotation
		ySlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
					rotateIt(Rotate.Y_AXIS, -new_val.doubleValue());
				else
					rotateIt(Rotate.Y_AXIS, new_val.doubleValue());
			}
		});
		// slider for rotation
		zSlider.valueProperty().addListener(new ChangeListener<Number>() {
			public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
				if (new_val.doubleValue() < old_val.doubleValue())
					rotateIt(Rotate.Z_AXIS, -new_val.doubleValue());
				else
					rotateIt(Rotate.Z_AXIS, new_val.doubleValue());
			}
		});
		
	}	
	
	private void factoryMethod(String type) {

		switch (type){
		case BOX:
			shape = new Box(200,200,200);
			break;
		case CYL:
			shape = new Cylinder(200, 200, 100);
			break;
		case SPH:
			shape = new Sphere(150);
			setEarth();
			break;
		default:
			shape = new Box(100,100,100);
			break;
		}
		
		resetSlides();
		shape.setTranslateY(100);
		shapeBox.getChildren().add(shape);		
	}
	
	private void resetSlides() {
		xSlider.setValue(0);
		ySlider.setValue(0);;
		zSlider.setValue(0);
	}
	private void rotateIt(Point3D dir, double val) {
		Rotate rotateAbout = new Rotate(val, dir);
		shape.getTransforms().add(rotateAbout);
	}
	
	
	

	private void setEarth() {

	  InputStream diffuse = getClass().getClassLoader().getResourceAsStream("texture.jpg");
	  InputStream bump = getClass().getClassLoader().getResourceAsStream("normal.jpg");
	  InputStream specular = getClass().getClassLoader().getResourceAsStream("specular.jpg");

	    PhongMaterial earthMaterial = new PhongMaterial();
	    earthMaterial.setDiffuseMap(
	    	new Image(diffuse)
	    );
	    earthMaterial.setBumpMap(
	    	new Image(bump)
	    );
	    earthMaterial.setSpecularMap(
	    	new Image(specular)
	    );

	    shape.setMaterial(
	        earthMaterial
	    );
	}
}
