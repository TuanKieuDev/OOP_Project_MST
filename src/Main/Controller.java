package Main;

import java.util.Collection;

import Graph.*;
import Algorithm.AlgorithmFactory;
import Algorithm.Algorithm;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.animation.FillTransition;
import javafx.util.Duration;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import javafx.animation.StrokeTransition;
import javafx.collections.FXCollections;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;


public class Controller implements Initializable {
	
	@FXML
	private Button initNewGraph;
	@FXML
	private Button execute;
	@FXML
	private Button executeAll;
	@FXML
	private Button reset; 
	@FXML
	private Label chooseAl;
	@FXML
	private ComboBox<String> alBox;
	@FXML
	private Group group;
	@FXML
	private AnchorPane childPane;
	@FXML
	private Label result;
	@FXML
	private AnchorPane mainPane;
	@FXML
	private Pane drawGraph;
	@FXML
	private VBox vBox;
	@FXML
	private ImageView imageView;
	@FXML
	private Button speedup;
	@FXML
	private Button slowdown;
	
    private Graph graph;
    private List<Vertex> selectedVertice = new ArrayList<Vertex>();
    private boolean graphLocked = false;
    private Algorithm algorithm;
    private String alnameString;
    private static int ver=0;
    
    @FXML
    public void handle(MouseEvent event) 
	{ 
        addVertexEvent.handle(event);
    };
    
	public EventHandler<MouseEvent> addVertexEvent = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			if(event.getButton() == MouseButton.PRIMARY && !event.getSource().equals(group)) {
				ver++;
				Vertex vertex = new Vertex(event.getX(), event.getY(), 18, ver);
				addVerToGraph(vertex);
			}
			 else if (event.getButton() == MouseButton.SECONDARY) 
         	{
                 unhighlightAllVertices(selectedVertice);
                 selectedVertice.clear();
             }
		}
	};
	
	public EventHandler<MouseEvent> addEdgeEvent = new EventHandler<MouseEvent>() {
		public void handle(MouseEvent event) {
			System.out.println("Pointed!");
			Vertex circle = (Vertex) event.getSource();
			
			selectedVertice.remove(circle);	
			selectedVertice.add(circle);
		
			for (Vertex v: selectedVertice)
				System.out.println(v.getID());
		
			if (selectedVertice.size () == 1)	
				highlightAllVertices(selectedVertice);
						
			else if (!graph.isExistsEdge(selectedVertice.get(0), selectedVertice.get(1))) {
				  TextInputDialog dialog = new TextInputDialog();

			      dialog.setTitle("Hello world!");
			      dialog.setHeaderText("Enter the weight:");
			      Optional<String> result = dialog.showAndWait();
			      int w=0;
			      if (result.isPresent()) {
                  	w = Integer.parseInt(result.get());
                  } 
			      else {}
			      if(w!=0) {
			      Edge newEdge = new Edge(selectedVertice.get(0), selectedVertice.get(1), w);
			      addEdgeToGraph(newEdge);
			      }
			unhighlightAllVertices(selectedVertice);
            selectedVertice.clear();
			}
			else
			{
				unhighlightAllVertices(selectedVertice);
				selectedVertice.clear();
			}
		}
		
	}; 
	
    @FXML
	public void speedup(ActionEvent event) {
		algorithm.speedup();
	}
	
    @FXML
	public void slowdown(ActionEvent event) {
		algorithm.slowdown();
	}
	
	public void boxchoose(ActionEvent event) {
			alnameString = alBox.getValue();
			System.out.println(alnameString);
	}
	
	protected void highlightAllVertices(List<Vertex> v)	{	
		for (Vertex i : v) 
			highlight(i);	
	}
	
	protected void highlight(Vertex v) {	
		v.draw(Color.RED);	
	}
	
	protected void unhighlightAllVertices(List<Vertex> v)	{	
		for (Vertex i: v) 
			unhighlight(i);	
	}
	
	protected void unhighlight(Vertex v) {	
		FillTransition ft1 = new FillTransition(Duration.millis(300), v);
        ft1.setToValue(Color.BLACK);
        ft1.play();
	}
	
	protected void unhighlightAllEdges(List<Edge> e)	{	
		for (Edge i: e)
			unhighlight(i);	
		}
	protected void unhighlight(Edge e) {	
		 StrokeTransition ftEdge = new StrokeTransition(Duration.millis(500), e);
         ftEdge.setToValue(Color.BLACK);
         ftEdge.play();
	}
	
	public void addVerToGraph(Vertex vertex) {
		group.getChildren().addAll(vertex.drawableObjects());
        graph.addVertex(vertex);
        System.out.println("ver " + vertex.getID());
        System.out.println(graph.getListVertice().size());
        for(int i=0; i<graph.getListVertice().size(); i++) {
			System.out.println("run away1 " + graph.getListVertice().get(i).getID());
		}
        vertex.setOnMousePressed(addEdgeEvent);
	}
	
	protected void addEdgeToGraph(Edge edge)
    {
        group.getChildren().addAll(edge.drawableObjects());
        graph.addEdge(edge);
    }
	
	@FXML
    private void resetHandle(ActionEvent event) {
        if (graphLocked)
        	unlockGraph();
        unhighlightAllVertices(graph.getListVertice());
        unhighlightAllEdges(graph.getListEdges());
        algorithm.setup();
        vBox.getChildren().removeAll(vBox.getChildren());
    }
	
	 @FXML
	 private void initHandle(ActionEvent event) {
	    if (graphLocked)
	        unlockGraph();
	    graph.initNewGraph();
	    ver=0;
	    group.getChildren().clear();
	    group.getChildren().addAll(drawGraph);
	    vBox.getChildren().removeAll(vBox.getChildren());
	}

    protected void lockGraph() {
	   	for (Vertex v: graph.getListVertice())
	    	v.setOnMousePressed(e -> {});
	    drawGraph.setOnMouseClicked(e -> {});
	    graphLocked = true;
    	algorithm = AlgorithmFactory.algorithmCreate(alnameString, this.graph);
		vBox.getChildren().add(algorithm.getpCode().getLine1());
		vBox.getChildren().add(algorithm.getpCode().getLine2());
		vBox.getChildren().add(algorithm.getpCode().getLine3());
		vBox.getChildren().add(algorithm.getpCode().getLine4());
		vBox.getChildren().add(algorithm.getpCode().getLine5());
		vBox.getChildren().add(algorithm.getpCode().getLine6());
		vBox.getChildren().add(algorithm.getpCode().getLine7());
		Text text = new Text("\nDescription: ");
		text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 18));
		text.setFill(Color.BROWN);
		algorithm.getDescriptionText().setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.ITALIC, 15));
		algorithm.getDescriptionText().setFill(Color.BROWN);
		vBox.getChildren().add(text);	
		vBox.getChildren().add(algorithm.getDescriptionText());
	}
	    
	    protected void unlockGraph()
	    {
	    	for (Vertex v: graph.getListVertice())
	    		v.setOnMousePressed(addEdgeEvent);
	    	drawGraph.setOnMouseClicked(addVertexEvent);
	    	graphLocked = false;
	    }
	    
	    public void execute(ActionEvent event) {
	    	if (!graphLocked)
	    	{
	    		lockGraph();
	    	}
			algorithm.execute();
		}
	    
	    public void executeAll(ActionEvent event) {
	    	if (!graphLocked)
	    		lockGraph();
	    	algorithm.executeAll();	
		}
	    
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			System.out.println("Initialize");
			String[] al = {"Kruskal", "Prim"};
			alBox.setItems(FXCollections.observableArrayList(al));
			initNewGraph.setStyle("-fx-background-color: violet; -fx-border-color: grey; -fx-border-radius: 5;");
			reset.setStyle("-fx-background-color: coral; -fx-border-color: grey; -fx-border-radius: 5;");
			execute.setStyle("-fx-background-color: aqua; -fx-border-color: grey; -fx-border-radius: 5;");
			executeAll.setStyle("-fx-background-color: beige; -fx-border-color: grey; -fx-border-radius: 5;");
			speedup.setStyle("-fx-background-color: beige; -fx-border-color: grey; -fx-border-radius: 5;");
			slowdown.setStyle("-fx-background-color: beige; -fx-border-color: grey; -fx-border-radius: 5;");
			if (this.graph != null) {
				group.getChildren().addAll(this.graph.drawableObjects());
				for(Vertex v : this.graph.getListVertice()) {
					v.setOnMousePressed(addEdgeEvent);
				}
			}
			else
				graph = new Graph();
		}
}
