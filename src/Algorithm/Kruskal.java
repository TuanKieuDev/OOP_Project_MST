package Algorithm;

import Graph.*;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.css.PseudoClass;
import java.util.Collection;

public class Kruskal extends Algorithm{
	private PriorityQueue<Edge> shortestEdges = new PriorityQueue<Edge>();
	private List<List<Vertex>> forest = new LinkedList<List<Vertex>>();
	
	public Kruskal(Graph graph) {
		super(graph);
	}

	public void setup()
	{
			pCode.getLine1().setText("Sort E edges by increasing weight");
			pCode.getLine2().setText("T = {}");
			pCode.getLine3().setText("for (i = 0; i < edgeList.length; i++)");
			pCode.getLine4().setText("  if adding e = edgelist[i] does not form a cycle");
			pCode.getLine5().setText("    add e to T");
			pCode.getLine6().setText("  else ignore e");
			pCode.getLine7().setText("MST = T //");
			this.shortestEdges = new PriorityQueue<Edge>();
			this.shortestEdges.addAll(this.graph.getListEdges());
			this.currentEdges = new ArrayList<Edge>();
			List<Vertex> allTheVertices = this.graph.getListVertice();
			this.forest = new LinkedList<List<Vertex>>();
			for (int i = 0; i < allTheVertices.size(); ++i)
			{
				this.forest.add(new LinkedList<Vertex>());
				this.forest.get(i).add(allTheVertices.get(i));
			}
			this.checkConnected();	
			
	}
	
	@Override
	public void execute() {
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
		SequentialTransition seq;
		SequentialTransition seq0;
		fillprev(fill31);
		fillprev(fill41);
		fill31.setShape(pCode.getLine3());
		fill41.setShape(pCode.getLine4());
		FillTransition fill7 = new FillTransition();
		FillTransition fill71 = new FillTransition();
		fillnext(fill7);
		fillprev(fill71);
		fill7.setShape(this.pCode.getLine7());
		fill71.setShape(this.pCode.getLine7());
		fillnext(fill6);
		fill6.setShape(pCode.getLine6());
		fillprev(fill61);
		fill61.setShape(pCode.getLine6());
		if (this.finished()||(!this.checkConnected()))
		{
			Edge shortestEdge = this.shortestEdges.poll();
			line3(shortestEdge);
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	line6();
			    			shortestEdge.highlightEdge(Color.RED);
			            }
			        }, 
			        (2000/speed)*speeddown 
			);
			new java.util.Timer().schedule( 
			        new java.util.TimerTask() {
			            @Override
			            public void run() {
			            	descriptionText.setText("The highlighted green vertices and edges\n form an MST with weight = " + String.valueOf(totalWeight()));
			            }
			        }, 
			        (4000/speed)*speeddown
			);
			seq0 = new SequentialTransition(fill3, fill31 ,fill6, fill61, fill7, fill71);
			seq0.setRate(speed/speeddown);
			seq0.play();
			return;
		}
		while (true)
		{
			Edge shortestEdge = this.shortestEdges.poll();
			System.out.println("short " + shortestEdge.getWeight());
			int tree1 = this.forestOf(shortestEdge.getVertex1()); 
			int tree2 = this.forestOf(shortestEdge.getVertex2());
			
			if (tree1 != tree2)
			{				
				System.out.println("short " + shortestEdge.getWeight());
				this.currentEdges.add(shortestEdge);
				
				for (Vertex v: this.forest.get(tree2))
				{
					this.forest.get(tree1).add(v);
				}
				this.forest.remove(tree2);
				fillnext(fill5);
				fill5.setShape(pCode.getLine5());
				fillprev(fill51);
				fill51.setShape(pCode.getLine5());
				if(this.currentEdges.size()!=1) {
					seq = new SequentialTransition(fill3, fill4, fill31, fill41, fill5, fill51);
					line3(shortestEdge);
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									line4(shortestEdge);
					            }
					        }, 
					        (2000/speed)*speeddown
					);
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									shortestEdge.highlightEdge(Color.GREEN);
									shortestEdge.getVertex1().draw(Color.GREEN);
									shortestEdge.getVertex2().draw(Color.GREEN);
									line5();
					            }
					        }, 
					        (5000/speed)*speeddown 
					);
				}
				else {
					seq = new SequentialTransition(fill1, fill2, fill11, fill21, fill3, fill4, fill31, fill41, fill5, fill51);
					seq.setRate(speed/speeddown);
					line12();
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									line3(shortestEdge);
					            }
					        }, 
					        (4000/speed)*speeddown 
					);
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									line4(shortestEdge);
					            }
					        }, 
					        (6000/speed)*speeddown 
					);
					new java.util.Timer().schedule( 
					        new java.util.TimerTask() {
					            @Override
					            public void run() {
									shortestEdge.highlightEdge(Color.GREEN);
									shortestEdge.getVertex1().draw(Color.GREEN);
									shortestEdge.getVertex2().draw(Color.GREEN);
									line5();
					            }
					        }, 
					        (8000/speed)*speeddown 
					);
				}
				break;
			}
			
			else {
				line3(shortestEdge);
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
								line4(shortestEdge);
				            }
				        }, 
				        (2000/speed)*speeddown 
				);
				new java.util.Timer().schedule( 
				        new java.util.TimerTask() {
				            @Override
				            public void run() {
				            	line6();
				            	shortestEdge.highlightEdge(Color.RED);
				            }
				        }, 
				        (4000/speed)*speeddown 
				);				
				seq = new SequentialTransition(fill3, fill4, fill31, fill41, fill6, fill61);
				seq.setRate(speed/speeddown);
				break;
			}
		}
		seq.play();
	}
	
	private int forestOf(Vertex v)
	{
		for (int i = 0; i < this.forest.size(); ++i)
		{
			if (this.forest.get(i).indexOf(v) != -1)
				return i;
		}
		return -1;	
	}
	
	public boolean finished() {
		return this.forest.size() < 2;
	}
	
	public void line12() {
		String string = "We use priority queue instead of sorting edges";
		descriptionText.setText(string);
	}
	
	public void line3(Edge e) {
		String string = "Remove the edge (" + e.getVertex1().getIDString() + "," + e.getVertex2().getIDString() + ") from PQ";	
		descriptionText.setText(string);
	}
	
	public void line4(Edge e) {
		String string = "Checking if a cycle will appear\n if we add this edge: (" + e.getWeightString() + ",(" + 
						e.getVertex1().getIDString() + "," + e.getVertex2().getIDString() + "))";
		descriptionText.setText(string);
	}
	
	public void line5() {
		String string = "Adding that edge will not form a cycle,\nso we add it to T. \nThe current weight of T is: " +
						String.valueOf(totalWeight());
		descriptionText.setText(string);

	}
	
	public void line6() {
		String string = "that edge will form a cycle, so we ignore it. \nThe current weight of T remains at " + 
						String.valueOf(totalWeight());
		descriptionText.setText(string);
	}
}
