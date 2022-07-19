package Algorithm;

import Graph.*;
import javafx.animation.FillTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.TextInputDialog;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Optional;

public class Prim extends Algorithm  {
	private Vertex root;
	private PriorityQueue<Edge> EdgeCanAdd = new PriorityQueue<Edge>();
	private ArrayList<Vertex> CurrentVertices = new ArrayList<Vertex>();
	private ArrayList<Edge> EliminatedEdges = new ArrayList<Edge>();
	
	public Vertex getRoot() {
		return root;
	}

	public ArrayList<Vertex> getCurrentVertices() {
		return CurrentVertices;
	}

	public Prim(Graph graph)
	{
		super(graph);
		int a;
		do {
		TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Hello world!");
	    dialog.setHeaderText("Enter the root:");
	    Optional<String> result = dialog.showAndWait();
	    a = Integer.parseInt(result.get());
		}while(a<=0 || a>graph.getListVertice().size());
		this.root = graph.getListVertice().get(a-1);
		this.root.draw(Color.BLUE);
		this.checkConnected();
		this.change(this.root);;
	}

	public boolean finished()
	{
		return this.graph.getListVertice().size() == this.CurrentVertices.size();
	}

	@Override
	public void execute()	{
		Long a = new Long(speed);
		Long b = new Long(speeddown);
		float sp = a.floatValue()/b.floatValue();
		FillTransition fill1 = new FillTransition();
		FillTransition fill2 = new FillTransition();
		FillTransition fill11 = new FillTransition();
		FillTransition fill21 = new FillTransition();
		fillnext(fill1);
		fillnext(fill2);
		fillprev(fill11);
		fillprev(fill21);
		fill1.setShape(pCode.getLine1());
		fill11.setShape(pCode.getLine1());
		fill2.setShape(pCode.getLine2());
		fill21.setShape(pCode.getLine2());
		FillTransition fill3 = new FillTransition();
		FillTransition fill4 = new FillTransition();
		FillTransition fill31 = new FillTransition();
		FillTransition fill41 = new FillTransition();
		FillTransition fill5 = new FillTransition();
		FillTransition fill6 = new FillTransition();
		FillTransition fill51 = new FillTransition();
		FillTransition fill61 = new FillTransition();
		fillnext(fill3);
		fillnext(fill4);
		fill3.setShape(pCode.getLine3());
		fill4.setShape(pCode.getLine4());
		fillprev(fill31);
		fillprev(fill41);
		fill31.setShape(pCode.getLine3());
		fill41.setShape(pCode.getLine4());
		fillnext(fill5);
		fill5.setShape(pCode.getLine5());
		fillprev(fill51);
		fill51.setShape(pCode.getLine5());
		fillnext(fill6);
		fill6.setShape(pCode.getLine6());
		fillprev(fill61);
		fill61.setShape(pCode.getLine6());
		SequentialTransition seq1, seq2, seq3, seq4, seq0;
		FillTransition fill7 = new FillTransition();
		FillTransition fill71 = new FillTransition();
		fillnext(fill7);
		fillprev(fill71);
		fill7.setShape(this.pCode.getLine7());
		fill71.setShape(this.pCode.getLine7());
		if(this.finished()||(!this.checkConnected()))
		{
			if(this.EdgeCanAdd.size()==0) {
				return;
			}
			this.EdgeCanAdd.removeAll(this.currentEdges);
			this.descriptionText.setText("The algorithm finish, total weight is  " + String.valueOf(this.totalWeight()) + ". \nDelete all remaining edges");
			Edge shortestEdge = this.EdgeCanAdd.poll();
			this.EdgeCanAdd.remove(shortestEdge);
			shortestEdge.highlightEdge(Color.RED);
			seq0 = new SequentialTransition(fill7, new PauseTransition(Duration.millis(2000)), fill71);
			seq0.setRate(sp);
			seq0.play();
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			    			descriptionText.setText("The highlighted green vertices and edges\n form an MST with weight = " + String.valueOf(totalWeight()));
			            }
			        }, 
			        (3000/speed)*speeddown 
			);
			return;
		}
		
		if(this.CurrentVertices.size()==1) {
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	for(Edge e: graph.getEdgesFrom(root)) {
			    				e.highlightEdge(Color.LIGHTGREEN);
			    			}
			            }
			        }, 
			        2000/speed*speeddown
			);
			seq1 = new SequentialTransition(fill1, fill2, fill11, fill21);
			seq1.setRate(sp);
			seq1.play();
			this.descriptionText.setText("T={"+this.root.getIDString()+"}");
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	line2();
			            }
			        }, 
			        2000/speed*speeddown 
			);
		}
		
		ArrayList<Vertex> VertexCanAddNext = new ArrayList<Vertex>();
		while (true)
		{
			Edge shortestEdge = this.EdgeCanAdd.poll();
			
			VertexCanAddNext.add(shortestEdge.getVertex1());		
			VertexCanAddNext.add(shortestEdge.getVertex2());
			VertexCanAddNext.removeAll(this.CurrentVertices);
			
			if(this.EliminatedEdges.contains(shortestEdge)) {
				this.execute();
			}
			
			if(VertexCanAddNext.size()==0 && !this.currentEdges.contains(shortestEdge)) {
				this.EliminatedEdges.add(shortestEdge);				
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	shortestEdge.highlightEdge(Color.RED);
								descriptionText.setText("that edge will form a cycle, so we ignore it");
				            }
				        }, 
				        2000/speed*speeddown
				);
				seq2 = new SequentialTransition(new PauseTransition(Duration.millis(2000)),fill6, fill61);
				seq2.setRate(sp);
				seq2.play();
				break;
			}
			
			if(this.CurrentVertices.size()==1) {
				seq3 = new SequentialTransition(new PauseTransition(Duration.millis(4000)), fill4, fill41);
				seq3.setRate(sp);
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
								line4(shortestEdge);
				            }
				        }, 
				        4000/speed*speeddown
				);			
			}
			else {
				seq3 = new SequentialTransition(fill4, fill41);
				seq3.setRate(sp);
				line4(shortestEdge);
			}				
			seq3.play();
			
			if (VertexCanAddNext.size() == 1)
			{
				if (!this.currentEdges.contains(shortestEdge))
				{
					this.currentEdges.add(shortestEdge);
				}

				if(this.CurrentVertices.size()==1) {
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									VertexCanAddNext.get(0).setFill(Color.GREEN);
					            }
					        }, 
					        4000/speed*speeddown
					);
				}
				
				else {
					VertexCanAddNext.get(0).setFill(Color.GREEN);
				}
				
				for (Edge e: this.graph.getEdgesFrom(shortestEdge.getVertex1()))
				{
					if (!this.EdgeCanAdd.contains(e))
					{
						this.EdgeCanAdd.add(e);
					}
				}
				for (Edge e: this.graph.getEdgesFrom(shortestEdge.getVertex2()))
				{
					if (!this.EdgeCanAdd.contains(e))
					{
						this.EdgeCanAdd.add(e);
					}
				}
				if(this.CurrentVertices.size()==1) {
					seq4 = new SequentialTransition(new PauseTransition(Duration.millis(6000)), fill5, fill51);
					seq4.setRate(sp);
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									line5(shortestEdge, VertexCanAddNext.get(0));
					            }
					        }, 
					        6000/speed*speeddown
					);	
				}
				else {
					seq4 = new SequentialTransition(new PauseTransition(Duration.millis(2000)), fill5, fill51);
					seq4.setRate(sp);
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									line5(shortestEdge, VertexCanAddNext.get(0));
					            }
					        }, 
					        2000/speed*speeddown
					);
				}
				seq4.play();
				
				if(this.CurrentVertices.size()==1) {			
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
					            	shortestEdge.highlightEdge(Color.GREEN);
					            	for(Edge e: graph.getEdgesFrom(VertexCanAddNext.get(0))) {
										if(!currentEdges.contains(e) && !EliminatedEdges.contains(e)) {
											e.highlightEdge(Color.LIGHTGREEN);
										}
							 		}
					            }
					        }, 
					        6000/speed*speeddown
					);
				}
				
				else {
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									shortestEdge.highlightEdge(Color.GREEN);
					            	for(Edge e: graph.getEdgesFrom(VertexCanAddNext.get(0))) {
										if(!currentEdges.contains(e) && !EliminatedEdges.contains(e)) {
											e.highlightEdge(Color.LIGHTGREEN);
										}
							 		}
					            }
					        }, 
					        2000/speed*speeddown
					);
				}
				CurrentVertices.addAll(VertexCanAddNext);
				break;
			}
		}
	}
	
	public void change(Vertex newRoot)
	{
		CurrentVertices.clear();
		CurrentVertices.add(newRoot);	
		currentEdges.clear();
		
		for (Edge e: this.graph.getEdgesFrom(newRoot))
		{
			EdgeCanAdd.add(e);
		}
	}	
	
	public void setup() {
		pCode.getLine1().setText("T = {s}");
		pCode.getLine2().setText("enqueue edges connected to s in PQ (by inc weight)");
		pCode.getLine3().setText("while (!PQ.isEmpty)");
		pCode.getLine4().setText("  if (vertex v linked with e = PQ.remove ∉ T)");
		pCode.getLine5().setText("    T = T ∪ {v, e}, enqueue edges connected to v");
		pCode.getLine6().setText("  else ignore e");
		pCode.getLine7().setText("MST = T //");
		this.change(this.root);
	}
	
	public void line2() {
		String str = "";
    	for(Edge e: graph.getEdgesFrom(root)) {
			str = str + "(" + e.getWeightString() + ",(" + e.getVertex1().getIDString() + "," + e.getVertex2().getIDString() + ")), ";
		}
    	str = str + "is added to the PQ.\nThe PQ is now ";
    	for(Edge e: graph.getEdgesFrom(root)) {
			str = str + "(" + e.getWeightString() + ",(" + e.getVertex1().getIDString() + "," + e.getVertex2().getIDString() + ")), ";
		}
    	descriptionText.setText(str); 
	}
	
	public void line4(Edge e) {
		String str = "Remove edge (" + e.getVertex1().getIDString() + "," + e.getVertex2().getIDString() + "). Checking if a cycle will \nappear if we add this edge: (" 
				+ e.getWeightString() + ",(" + e.getVertex1().getIDString() + "," + e.getVertex2().getIDString() + "))";
		descriptionText.setText(str);
	}
	
	public void line5(Edge e, Vertex v) {
		String str = "Adding the light-green edges from " + v.getIDString() + " to PQ. \n" + 
					 v.getIDString() + " is not in T. " +
					 "\nAdding (" + e.getVertex1().getIDString() + ", " + e.getVertex2().getIDString() + ") " + "to current edges.\nThe current weight of T remains at "
					 		+ String.valueOf(totalWeight());
		descriptionText.setText(str);
	}

	public void Line6(Edge e) {
		String str = "That edge will form a cycle, so we ignore it.\n The current weight of T remains at " + String.valueOf(totalWeight());
		descriptionText.setText(str);
	}
	
}

