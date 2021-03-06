/*
 * Copyright (c) 2020.
 * File : DetectCycle.java
 * Author : Ankur
 * Last modified : 26/11/2020
 * Problem Statement at the end of the code
 *
 * All code is for practice purpose only and strictly non-commercial.
 * All rights reserved.
 * Please refer to apache license terms in the project.
 */

package practice.graph;

import dsa.Graph;

import java.util.ArrayList;

public class DetectCycle {
    Graph G;

    DetectCycle(Graph in){
        G = in;
    }

    /*
    There is a cycle in a graph only if there is a back edge present in the graph.
    A back edge is an edge that is joining a node to itself (self-loop) or one of
    its ancestor in the tree produced by DFS.
    To find the back edge to any of its ancestor keep a visited array and if there
    is a back edge to any visited node then there is a loop and return true.

    *** Approach is simple : Just do a DFS and pass parent as one of parameter. Compare parent with
    next visited element, if they are not same then there is a back edge ***
     */
    boolean isGraphCyclic(){
        boolean[] visited = new boolean[G.size()];
        for(int i = 0; i<G.size(); ++i){
            if(!visited[i])
                if(DFSUtil(i, -1, visited))
                    return true;
        }

        return false;
    }

    boolean DFSUtil(int i, int parent, boolean[] visited){
        visited[i] = true;
        for(int k : G.adjacencyList.get(i)){
            if(!visited[k])
                DFSUtil(k,i,visited);
            else if(k!=parent)
                return true;
        }

        return false;
    }

    void kCores(int k, ArrayList<Integer> affected, int[] degree){
        boolean[] visited = new boolean[G.size()];

        // Iterate over the list of affected vertices
        for(int indx = 0; indx<affected.size(); ++indx){
            int curr = affected.get(indx);
            if(!visited[curr] && degree[curr]<k) {
                visited[curr] = true;
                for(int neighbour : G.adjacencyList.get(curr)){
                    --degree[neighbour];
                    if(degree[neighbour]<k)
                        affected.add(neighbour);
                }
            }
        }

        System.out.println("\nPrint K Cores :: ");
        for(int i = 0; i<G.size(); ++i){
            if(!visited[i]){
                System.out.print(i + " => ");
                for(int j : G.adjacencyList.get(i)){
                    if(!visited[j])
                        System.out.print(j + " ");
                }

                System.out.println();
            }
        }
    }

    public static void main(String[] args) {
        DetectCycle input = new DetectCycle(new Graph(6));
        input.G.addEdge(0,1);
        input.G.addEdge(0,2);
        input.G.addEdge(0,3);
        input.G.addEdge(1,4);
        input.G.addEdge(2,5);

        System.out.println(input.isGraphCyclic()?"Cyclic" : "Not cyclic");
        input.G.addEdge(0,5);
        System.out.println(input.isGraphCyclic()?"Cyclic" : "Not cyclic");

        System.out.println("\nPrint Graph ::");
        input.G.printGraph();

        ArrayList<Integer> affected = new ArrayList<>();
        int[] degree = new int[input.G.size()];
        for(int i = 0; i<6; ++i){
            affected.add(i);
            degree[i] = input.G.adjacencyList.get(i).size();
        }

        input.kCores(2,affected, degree);
    }
}
