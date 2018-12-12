package fwsmem;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileIO {
    private static int numOfVertices;
    
    public static int[][] initializeGraph(String fileName) throws IOException {
        // Reads a file and initialize the input matrix
        try(Scanner scanner = new Scanner(new File(fileName))){
            String auxString;
            // Get number of vertices from the file
            numOfVertices = scanner.nextInt();
            int[][] graph = new int[numOfVertices][numOfVertices];
            // For each position in the matrix, get next value of the file, 
            // check for a number and insert it in the matrix
            for (int i = 0; i < numOfVertices; i++) {
                for (int j = 0; j < numOfVertices; j++) {
                    auxString = scanner.next();
                    try{
                        graph[i][j] = Integer.parseInt(auxString);
                    }catch(NumberFormatException | NullPointerException nfe){
                        // If next item from the file is not a number,
                        // set the corresponding matrix position to INF(99999)
                        graph[i][j] = 99999;
                    }
                }
            }
            
            return graph;
        }
        catch(IOException e){
            System.out.println("Error: No se ha encontrado el fichero " + fileName);
            System.exit(0);
            return null;
        }
    }

    public static int getNumOfVertices() {
        // Return the number of vertices of the readed file
        return numOfVertices;
    }
    
}
