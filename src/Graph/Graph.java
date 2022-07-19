package Graph;

import java.util.List;

import Algorithm.Drawable;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Collection;

public class Graph implements Drawable {
	private List<Edge> ListEdges = new ArrayList<Edge>();
	private List<Vertex> ListVertice = new ArrayList<Vertex>();
	
	public Graph(List<Edge> listEdges, List<Vertex> listVertice) {
		super();
		ListEdges = listEdges;
		ListVertice = listVertice;
	}

	public Graph() {
		super();
	}
	
	public void addEdge(Edge e)
	{
		this.ListEdges.add(e);
	}
	
	public void removeEdge(Edge e)
	{
		this.ListEdges.remove(e);
	}
	
	public void addVertex(Vertex v)
	{
		this.ListVertice.add(v);
	}
	
	public void removeVertex(Vertex v)
	{
		for(Edge i : this.ListEdges)
		{
			if (i.getVertex1().getID()==v.getID() || i.getVertex2().getID()==v.getID())
			{
				this.ListEdges.remove(i);
			}
		}
		this.ListVertice.remove(v);
	}
	
	public List<Edge> getListEdges() {
		return ListEdges;
	}
	
	public List<Vertex> getListVertice() {
		return ListVertice;
	}
	
	public List<Edge> getEdgesFrom(Vertex v)
	{
		List<Edge> adj = new ArrayList<Edge>();
		
		for (Edge i: this.ListEdges) {
			if (i.getVertex1().equals(v) || i.getVertex2().equals(v)) {
					adj.add(i);
		}
		}
		return adj;
	}
	
	public List<Vertex> getVertexFrom(Vertex v)
	{
		List<Vertex> adj = new ArrayList<Vertex>();
		
		for (Edge i: this.ListEdges) {
			if (i.getVertex1().equals(v)) {
				adj.add(i.getVertex2());
			}
			if (i.getVertex2().equals(v)) {
				adj.add(i.getVertex1());
			}
		}
		return adj;
	}
		
	public boolean isExistsEdge(Vertex ver1, Vertex ver2)
	{
		for (Edge i: this.ListEdges)
		{
			if ((i.getVertex1().equals(ver1) && i.getVertex2().equals(ver2)) || (i.getVertex1().equals(ver2) && i.getVertex2().equals(ver1)))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean isExistVertex(Vertex v) 
	{
		for(Vertex i: this.ListVertice) {
			if(i.equals(v)) {
				return true;
			}
		}
		return false;
	}
	
	public void initNewGraph() {	
		ListVertice.clear();
		ListEdges.clear();
	}
	
	public void draw(Color color)
	{
	}
	
	public Collection<Node> drawableObjects()
	{
		ArrayList<Node> allObjects = new ArrayList<Node>();
		for (Edge e: this.ListEdges)
		{
			allObjects.addAll(e.drawableObjects());
		}
		for (Vertex v: this.ListVertice)
		{
			allObjects.addAll(v.drawableObjects());
		}
		return allObjects;
	}
	
}
