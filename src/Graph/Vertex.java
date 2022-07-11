package Graph;

import Algorithm.Drawable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.Collection;
import javafx.scene.Node;
import java.util.Arrays;

public class Vertex extends Circle implements Drawable {
	private int ID;
	private Label vertexLabel;
	
	public int getID() {
		return ID;
	}

	public String getIDString() {
		return String.valueOf(this.ID);
	}
	
	public Vertex(double xCenter, double yCenter, double radius, int id) {
		this(xCenter, yCenter, radius, Color.BLACK, id);
	}

	public Vertex(double xCenter, double yCenter, double radius, Color color, int id) {
		super(xCenter, yCenter, radius);
		this.ID=id;
		this.setId(String.valueOf(ID));
		this.setOpacity(0.2);
		this.draw(color);
	}


	@Override
	public boolean equals(Object o)
	{
		return o == this || (o instanceof Vertex && ((Vertex) o).getID()==this.getID());
	}
	
	@Override
	public void draw(Color color) {
		this.setFill(color);
		this.setStroke(Color.BLACK);
		if (this.vertexLabel == null)
			this.vertexLabel = new Label();
		
		this.vertexLabel.setLayoutX(this.getCenterX() - 6);
		this.vertexLabel.setLayoutY(this.getCenterY() - 6);
		vertexLabel.setFont(Font.font("Helvetica", FontWeight.BOLD, 14.6));
		vertexLabel.setTextFill(Color.ORANGERED);
		vertexLabel.setText(String.valueOf(this.getID()));
		vertexLabel.setDisable(true);
		vertexLabel.setOpacity(99999.0);
	}
	
	@Override
	public Collection<Node> drawableObjects() {
		return Arrays.asList(this, vertexLabel);
	}
}
