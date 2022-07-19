package Algorithm;

import Graph.*;

public class AlgorithmFactory {
	public static final String string = "prim";

	public static Algorithm algorithmCreate(String algorithmName, Graph graph)
	{
		for(int i=0; i<graph.getListVertice().size(); i++) {
			System.out.println("graph vertex " + graph.getListVertice().get(i).getID());
		}
		for(int i=0; i<graph.getListEdges().size(); i++) {
			System.out.println("graph weight of edge  " + graph.getListEdges().get(i).getWeight());
		}
		algorithmName = algorithmName.toLowerCase();
		System.out.println(algorithmName);
		Algorithm al;
		
		switch (algorithmName) {
			case string:
				al = new Prim(graph);
				al.setup();
				return al;
		
			default:
				al = new Kruskal(graph);
				al.setup();
				return al;
		}
	}
}
