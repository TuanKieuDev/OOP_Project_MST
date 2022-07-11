package Algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import Graph.*;
import javafx.animation.FillTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public abstract class Algorithm {
	protected List<Edge> currentEdges = new ArrayList<Edge>();
	protected int connectedPart[];
	protected Graph graph;
	protected PseudoCode pCode = new PseudoCode();
	protected Text descriptionText = new Text();
	protected static long speed=1;
	protected static long speeddown=1;
	
	public void speedup() {
		speed++;
	}
	
	public void slowdown() {
		speeddown++;
	}
	
	public Text getDescriptionText() {
		return descriptionText;
	}

	public void setDescriptionText(Text descriptionText) {
		this.descriptionText = descriptionText;
	}

	public PseudoCode getpCode() {
		return pCode;
	}

	public Algorithm() {
		super();
	}

	public Algorithm(Graph graph) {
		super();
		this.graph = graph;
	}

	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public List<Edge> getCurrentEdges() {
		return currentEdges;
	}

	public void fillnext(FillTransition fill) {
		fill.setDuration(Duration.millis(1000));  
	    fill.setFromValue(Color.BLACK);  
	    fill.setToValue(Color.RED);
	}
	
	public void fillprev(FillTransition fill) {
		fill.setDuration(Duration.millis(1000));  
	    fill.setFromValue(Color.RED);  
	    fill.setToValue(Color.BLACK);
	}
	
	public abstract boolean finished();
	
	public abstract void setup();
	
	public abstract void execute();
	
	public void executeAll()
	{
		int a=0;
		this.execute();
		if(this instanceof Kruskal) { 
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	execute();
			            	System.out.println(speed);
			            }
			        }, 
			        (11000/speed)*speeddown
			);
		for(int i=1; i<=graph.getListEdges().size()-2; i++)
		{
				System.out.println("notdone");
				a++;
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	execute();
				            }
				        }, 
				        ((11000+6000*i)/speed)*speeddown
				    );
			}
		}
		
		if(this instanceof Prim) {
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	execute();
			            }
			        }, 
			        (8000/speed)*speeddown
			);
		for(int i=1; i<=graph.getListEdges().size()-1; i++)
		{
			if(!finished()) {
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	execute();
				            }
				        }, 
				        ((8000+4500*i)/speed)*speeddown
				    );
			}
			}
		}
		System.out.println("sadsad" + a);
	}
	
	public int totalWeight()
	{
		int total = 0;
		for (Edge i: this.currentEdges)
			total += i.getWeight();
		return total;
	}
	
	public boolean checkConnected()
	{
		if (this.graph.getListEdges().size() < (this.graph.getListVertice().size() - 1))
		{
			return false;
		}
		connectedPart = new int[this.graph.getListVertice().size()];
		for (int i = 0; i < this.graph.getListVertice().size(); i++)
		{
			connectedPart[i] = i;
		}
		dfs(this.graph.getListVertice().get(0));
		for (int i = 1; i < this.graph.getListVertice().size(); i++)
		{
			if (connectedPart[i]!=connectedPart[i - 1])
			{
				return false;
			}
		}
		return true;
	}

	public void dfs(Vertex v)
	{
		for (Vertex i: this.graph.getVertexFrom(v))
		{
			if (connectedPart[i.getID() - 1]
					!= connectedPart[v.getID() - 1])
			{
				connectedPart[i.getID() - 1]
						= connectedPart[v.getID() - 1];
				dfs(i);
			}
		}
	}
}
