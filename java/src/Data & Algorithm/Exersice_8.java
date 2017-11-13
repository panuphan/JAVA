

import java.io.*;
import java.util.*;
import javax.swing.*;
import org.jgrapht.*;
import org.jgrapht.graph.*;
import org.jgrapht.traverse.*;
import org.jgrapht.alg.*;

///////////////////////////////////////////////////////////////////////////////
class Home
{
	private String name;
	public Home(String n)	{ name = n;}
	public String getName()	{ return name; }

	// equality based on name
	public boolean equals(Object o)
	{
		Home other = (Home)o;
		return this.name.equalsIgnoreCase(other.name);
	}
	// hashcode based on the hashcode of name
	public int hashCode()
	{
		return name.toLowerCase().hashCode();
	}
}

////////////////////////////////////////////////////////////////////////////////
class MyGraph{
    
    protected HashMap<String, Home> AllHomes;            // real objects
    protected ArrayList<String> HomeNames;		 // graph nodes
    protected Graph<String, DefaultWeightedEdge> G;
    private SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> SG;
    protected DijkstraShortestPath<String, DefaultWeightedEdge> DSP;
        
    public MyGraph(String infile){
        
        // Check input'file
        boolean openfile = true;
        Scanner scan = null;
        
        do {
            try {
                scan = new Scanner(new File("proposition/Data/EX8/"+infile ));
                openfile = false;
            } catch (Exception e) {
                System.err.println(" Error file : "+ infile );
                System.err.print(" Enter file : proposition/Data/EX8/");
                Scanner scan_err = new Scanner(System.in);
                infile = scan_err.nextLine();
            }
        } while (openfile);
        
        
        // Creating Object 
        AllHomes = new HashMap<>();
        HomeNames = new ArrayList<>();
        SG = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
        G = (Graph<String, DefaultWeightedEdge>) SG;
        
        
        // read text file and construct the graph Given a person’s name as an input 
        while (scan.hasNext()) {

            String line = scan.nextLine();
            String[] buf = line.split("\\s+");

            String home_1 = buf[0];
            String home_2 = buf[1];
            int distance = Integer.parseInt(buf[2]);
        
            //If it doesn’t exist in AllHomes(HM), put it in AllHomes && HomeNames
            if (!AllHomes.containsKey(home_1)) {
                AllHomes.put(home_1, new Home(home_1));
                HomeNames.add(home_1);
            } else if (!AllHomes.containsKey(home_2)) {
                AllHomes.put(home_2, new Home(home_2));
                HomeNames.add(home_2);
            }
            
            //add input in graphs
            Graphs.addEdgeWithVertices(G, home_1, home_2, distance);

        }
        
        scan.close();
        
    }//end constructor
    
    
    // Methods to print
    public Home searchHome(String name) {
        return AllHomes.get(name);
    }

    public void printGraph() {
        Set<DefaultWeightedEdge> allEdges = G.edgeSet();
        printDefaultWeightedEdges(allEdges);
    }

    public void printDefaultWeightedEdges(Collection<DefaultWeightedEdge> E) {

        for (DefaultWeightedEdge e : E) {

            Home source = searchHome(G.getEdgeSource(e));
            Home target = searchHome(G.getEdgeTarget(e));
            double weight = G.getEdgeWeight(e);

            System.out.printf("%s-%s(%.0f) ",
                    source.getName(), target.getName(), weight);

        }
    }
        
    
    //Methods to find ShortesRoutes on graph 
    public void ShortestRoutes() {
        
        // Check error source node
        boolean error = true;
        Scanner scan = new Scanner(System.in);
        while (error) {

            System.out.print("Source node : ");
            String key1 = scan.nextLine().toUpperCase();
            try {
                
                // Sort ArrayList(HomeNames) by name
                Collections.sort(HomeNames);
                
                for (String key2 : HomeNames) {
                    DSP = new DijkstraShortestPath<>(SG, key1, key2);
                    List<DefaultWeightedEdge> routes = DSP.getPathEdgeList();

                    //Show routes Forward node 
                    shortestRoutes("Forward", key1, key2);
                    
                    //If not have Forward routes ,so not have Backward routes
                    if (routes != null) {
                        shortestRoutes("Backward", key2, key1);
                    }
                    //choose visit or not 
                    visit(key1, key2);
                }
                
              error = false;
              
            } catch (Exception e) {
                System.err.println("ERROR NODE !! Enter again");
            }

        }//end while
        
    }//end ShortestRoutes
        
    public void shortestRoutes(String str, String key1, String key2) {
        
        // source and target must exist, otherwise error
        if (G.containsVertex(key1) && G.containsVertex(key2) && !key1.equalsIgnoreCase(key2)) {
            DSP = new DijkstraShortestPath<>(SG, key1, key2);
            List<DefaultWeightedEdge> path = DSP.getPathEdgeList();

            if (path != null) {
                String str2;
                if (str.equalsIgnoreCase("Forward")) {
                    str2 = "to";
                } else {
                    str2 = "from";
                    key2 = key1;
                }

                System.out.printf("%-8s distance %-4s %s = %2.0f : ",
                        str, str2, key2, DSP.getPathLength());
                printDefaultWeightedEdges(path);
            } else {
                System.out.printf("No %-8s routes ", str);
            }

            System.out.println("");
        }

    }
    
    
    //Methods to check visit other friends' home or not
    public void visit(String key1, String key2) {
        
        DSP = new DijkstraShortestPath<>(SG, key1, key2);
        List<DefaultWeightedEdge> path = DSP.getPathEdgeList();
        double F = DSP.getPathLength();
        
        DSP = new DijkstraShortestPath<>(SG, key2, key1);
        double B = DSP.getPathLength();
        
        if (F < B || path == null) {
            System.out.print("Not ");
        }
        if (!key1.equalsIgnoreCase(key2)) {
            System.out.println("visit\n");
        }
    }


}


////////////////////////////////////////////////////////////////////////////////
public class Exersice_8 {
  
    
    public static void main(String[] args) {
        // TODO code application logic here
        MyGraph mygraph = new MyGraph("");
        mygraph.ShortestRoutes();
        
    }
}
