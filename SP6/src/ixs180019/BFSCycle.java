package ixs180019; /** Breadth-first search
 /**
 * Class implements a BFS using queue and stack to find the odd length cycle in the graph
 * It consists of following methods: findCycle() , printCycle()
 * @aurthor Ishan Suketu Shah(ixs180019)
 * @author Ayesha Gurnani (ang170003)
 * @param
 * */

import ixs180019.Graph.Edge;
import ixs180019.Graph.Vertex;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class BFSCycle{
	List<Vertex> cycle = new ArrayList<>();

	/**
	 * Method: findCycle()
	 * Finds odd length cycle in the graph and returns it
	 * @param g: Graph in which cycle is to found
	 * @return: List containing cycle
	 * */
	public List<Vertex> findCycle(Graph g) {
		/*Vertex[] vertices = g.getVertexArray();*/
		BFSOO b = null;
		for (Vertex src : g) {
			if ( !b.getSeen(src) || b == null ) { //Traverse over all the unvisited vertices of the graph
				b = BFSOO.breadthFirstSearch(g, src); //BFS the first connected component of the graph

				for (Vertex u : g) {
					if (b.getSeen(u)) { //Traverse over all the visited vertices of the graph
						for (Edge e : g.outEdges(u)) {
							Vertex v = e.otherEnd(u);
							if (b.getDistance(u) == b.getDistance(v)) { // Check if the distance of vertices in an (u,v) edge is same
								List<Vertex> cycle1  = new ArrayList<Vertex>(); //Contain the ancestors of vertex u
								List<Vertex> cycle2  = new ArrayList<>(); //Contains the ancestors of vertex v

								while (u != v) {
									cycle1.add(u);
									cycle2.add(v);
									u = b.getParent(u);
									v = b.getParent(v);
								}
								cycle1.add(u);
								cycle2.add(v);
								Collections.reverse(cycle2); //Reverses the list to form a cycle after merging with cycle 1

								//Merge cycle1 and cycle2 to cycle
								cycle.addAll(cycle2);
								cycle.addAll(cycle1);
								return cycle;
							}
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * Method: printCycle()
	 * Prints the cycle
	 * @param ls: List containing cycle
	 * @return
	 * */
	public void printCycle(List<Vertex> ls){
		if(ls.size() != 0) {
			System.out.println(ls);
		}
		else{
			System.out.println("No cycle exists");
		}
	}

	public static void main(String[] args) throws Exception {
		String string = "6 7   1 2 2   1 3 3   1 6 1   2 4 5   3 4 4   4 5 1   5 6 1 1";
		Scanner in;
		// If there is a command line argument, use it as file from which
		// input is read, otherwise use input from string.
		in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

		// Read undirected graph from input
		Graph g = Graph.readGraph(in, false);
		int s = in.nextInt();
		g.printGraph(false);

		BFSCycle bfsCycle = new BFSCycle();
		List<Vertex> oddCycle = bfsCycle.findCycle(g);

		//Prints cycle if exists
		System.out.println("Cycle:");
		bfsCycle.printCycle(oddCycle);
	}
}

