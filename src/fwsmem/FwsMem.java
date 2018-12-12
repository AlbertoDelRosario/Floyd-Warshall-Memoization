package fwsmem;

import java.io.IOException;
import static java.lang.Integer.min;

public class FwsMem {
    
    final static int INF = 99999; 
    private static int V;
    private static int[][][] M;
    private static boolean debugIn = false;
    private static boolean debugOut = false;
    private static boolean debugTime = false;
    private static long startTime;
    
    public static void initM(){
        // Initialize 3d array M to INF
        M = new int[V][V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                for (int k = 0; k < V; k++) {
                    M[i][j][k] = INF;
                }
            }
        }
    }
    
    public static void floydWarshall(int graph[][]){
        
        // Debug input//////////////////////////////////////////////////////////
        if (debugIn == true){
            // Print the input matrix
            printSolution(graph,"Input vertexs weigth matrix");
        }
        ////////////////////////////////////////////////////////////////////////
        
        int dist[][] = new int[V][V]; 
        int i, j; 
        for (i = 0; i < V; i++) {
            for (j = 0; j < V; j++) {
                dist[i][j] = fwsRec(graph, V-1, i, j);
            }
        }
        
        double stopTime = System.nanoTime();
        double elapsedTime = (stopTime - startTime)/1000000000;
        
        // Debug output/////////////////////////////////////////////////////////
        if (debugOut == true){
            // Print the shortest distance matrix 
            printSolution(dist,"\nThe following matrix shows the shortest "
                               + "distances between every pair of vertices");
        }
        ////////////////////////////////////////////////////////////////////////
        
        // Debug time//////////////////////////////////////////////////////
        if (debugTime == true){
            System.out.println("--------------------------------------"
                                + "\nTime: " + elapsedTime + " seconds");
        }
        ////////////////////////////////////////////////////////////////////////
    } 
    
    private static int fwsRec(int graph[][], int k, int i, int j){
        
        if (M[k][i][j] == INF){
            // Base case: the shortest path is the weight of the edge connecting i and j
            if (k == 0) {
                M[k][i][j] = graph[i][j];
            } else {
                // Find the shortest path between i and j
                int path1 = fwsRec(graph, k-1, i, j);
                // Find the shortest path between i to k and k to j
                int path2 = fwsRec(graph, k-1, i, k) + fwsRec(graph, k-1, k, j);
                // Store result for further searches
                M[k][i][j] = min(path1, path2);
            }
        }
        
        // Return the stored value for the shortest path between i and j
        return M[k][i][j];
    }
    
    private static void printSolution(int dist[][], String msg){
        
        // Print all the given matrix's elements and a message
        System.out.println(msg); 
        for (int i=0; i<V; ++i){
            for (int j=0; j<V; ++j){
                if (dist[i][j]==INF){ 
                    System.out.print("INF\t"); 
                }else{
                    System.out.print(dist[i][j]+"\t");
                }
            }
            
            System.out.println(); 
        } 
    } 
    
    public static void main(String[] args) throws IOException {
        startTime = System.nanoTime();
        int graph[][] = null;
        // Read arguments and check for switches
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-di")){
                debugIn = true;
            }
            
            if (args[i].equals("-do")){
                debugOut = true;
            }
            
            if (args[i].equals("-dt")){
                debugTime = true;
            }
            
            if (args[i].equals("-f") && i < args.length - 1){
               graph = FileIO.initializeGraph(args[i+1]);
               V = FileIO.getNumOfVertices();
               i++;
            }
        }
        
        if (graph != null){
            // Initialize the matrix to store results of the recursive calls
            initM();
            // Print the solution 
            floydWarshall(graph); 
        }   
    }
}
