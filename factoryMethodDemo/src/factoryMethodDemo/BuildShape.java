package factoryMethodDemo;

import java.io.InputStream;

import javafx.geometry.Point3D;
import javafx.scene.image.Image;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

public class BuildShape {
	public Shape3D shape;
	final String BOX = "Box";
	final String CYL = "Cylinder";
	final String SPH = "Sphere";
	final String TRI = "Triangular Prism";
	
	
	
	public Shape3D buildShap(String type) {
		 return shape = factoryMethod(type);
	}
	
	public Shape3D factoryMethod(String type) {
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
	public void rotateIt(Point3D dir, double val) {
		
		Rotate rotateAbout = new Rotate(val, dir);
		shape.getTransforms().add(rotateAbout);
	}
}
