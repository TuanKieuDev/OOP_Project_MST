package Graph;

import Algorithm.Drawable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.text.Font;
import javafx.animation.FadeTransition;
import java.util.Collection;
import javafx.scene.Node;
import javafx.util.Duration;
import java.util.Arrays;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;


public class Edge extends Path implements Comparable<Edge>, Drawable {
	
	protected Label weightLabel;
	private Vertex vertex1;
	private Vertex vertex2;
	private int weight;

	public Vertex getVertex1() {
		return vertex1;
	}

	public Vertex getVertex2() {
		return vertex2;
	}

	public int getWeight() {
		return weight;
	}
	
	public String getWeightString() {
		return String.valueOf(weight);
	}
	
	public Edge(Vertex vertex1, Vertex vertex2, int weight) {
		this(vertex1, vertex2, weight, Color.BLACK);
		this.setStrokeWidth(1.6);
	}

	public Edge(Vertex vertex1, Vertex vertex2, int weight, Color color) {
		super();
		this.vertex1 = vertex1;
		this.vertex2 = vertex2;
		this.weight = weight;
		this.weightLabel = new Label();
		this.weightLabel.setText(String.valueOf(this.weight));
		this.draw(color);
		this.setStrokeWidth(1.8);
	}

	public int compareTo(Edge e) {
		// TODO Auto-generated method stub
		if (this.getWeight() < e.getWeight())
			return -1;
		
		else if (this.getWeight() == e.getWeight()) return 0;
		else return 1;
	}	
	
	public boolean equals(Object o) {
		if(o==this) {
			return true;
		}
		else if(!(o instanceof Edge)) {
			return false;
		}
		else {
			return ((((Edge) o).getVertex1()==this.getVertex1())&&(((Edge) o).getVertex2()==this.getVertex2())) ||
				   ((((Edge) o).getVertex1()==this.getVertex2())&&(((Edge) o).getVertex2()==this.getVertex1()));
		}
	}
	
	public Vertex getVerthat(Vertex v) { //pass v parameter 
		if(this.vertex1==v) return this.vertex2;
		if(this.vertex2 == v) return this.vertex1; 
		return null;
	}
		
	@Override
	public void draw(Color color)
	{
		//this.setDisable(true);
		weightLabel.setFont(new Font(10.6));
        weightLabel.setLayoutX((this.getVertex1().getCenterX() + this.getVertex2().getCenterX()) / 2);
        weightLabel.setLayoutY((this.getVertex1().getCenterY() + this.getVertex2().getCenterY()) / 2);
		
        this.getElements().add(new MoveTo(this.getVertex1().getCenterX(), this.getVertex1().getCenterY()));
        
		this.getElements().add(new LineTo(this.getVertex2().getCenterX(), this.getVertex2().getCenterY()));
		this.setStroke(color);
	}
	
	public void highlightEdge(Color color) {
		this.draw(color);
		FadeTransition ft = new FadeTransition(Duration.millis(500), this);
	    ft.setFromValue(1.0);
	    ft.setToValue(0.3);
	    ft.setCycleCount(4);
	    ft.setAutoReverse(true);
	    ft.play();
	}

	@Override
	public Collection<Node> drawableObjects()
	{
		return Arrays.asList(this, weightLabel);
	}
	
}
