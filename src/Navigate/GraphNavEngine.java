/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Navigate;


import _GenericAbstract.AdjacencyMapGraph;
import _GenericAbstract.LinkedQueue;
import _Interfaces.Entry;
import _Interfaces.Map;
import _Interfaces.NavEngine;
import _Interfaces.Plan;
import _Interfaces.Queue;
import _Interfaces.Vertex;
import _Interfaces.Selection;
import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Constructs a graph of vertices and edges representing a navigable path in a store.
 * Class is used to calculate the shortest (fastest) route in the store to get all items, 
 * given a particular list of items and designated locations.
 * @author Ryan
 */
public class GraphNavEngine implements NavEngine
{
    //fields of the class
    /*
    graph of Point objects as vertices and edges as Doubles (lengths between vertices)
    */
    private AdjacencyMapGraph<Point, Double> graph = new AdjacencyMapGraph<>(false);  
    
    /*
    defined starting point of a traversal to get items (the entrance of the store)
    also will be called entrance vertex throughout this class
    */
    private Vertex<Point> startVertex;  
    
    /*
    defined ending point of a traversal to get items (the checkout of the store)
    also will be called the checkout vertex throughout this class
    */
    private Vertex<Point> endVertex;      
    
    /*
    matrix representation of all Bin objects in store and the navigable distance 
    from each Bin to every other Bin
    */
    private double [][] binDistances;
    
    /*
    matrix representation of all vertices in graph and the navigable distance 
    from each vertex to every other vertex
    */
    private double [][] vertexDistances; 
    
    /*
    represents the distance shortest order of destination vertices to get all items efficiently
    */
    private List<Vertex<Point>> shortestPath;  
    
    /*
    represents a way to store reference to the vertex found to coordinate with a specific Bin
    */
    private List<DestinationPair> binVertexPairs;
    
    /*
    represents the number of permutations in a given run of brute force shortestRoute calculations
    field exists for diagnostic purposes
    */
    private int permutations = 0;  //TEMP
       
    
    // ------------------------------------- GraphNavEngine() methods ----------------------------------- //
    /**
     * Constructs all vertices and edges in graph, and designates a startVertex vertex and an endVertex vertex.
     * @param p Plan to correspond to
     * @param n number of subdivision Point objects between each PointPair
     */
    public GraphNavEngine(Plan p, int n)
    {
        PointPair [] pairs = p.getPaths(); //represent the paths to be contained in the graph
        
        for(int i = 0; i < pairs.length; i++)
        {
            Point a = pairs[i].getFirst();
            Point b = pairs[i].getSecond();
            
            Point [] line = buildPointLine(a,b,n);
            buildPath(line);
        }
        
        startVertex = buildConnection(p.getEntryPair());
        endVertex = buildConnection(p.getExitPair());
        vertexDistances = buildVertexDistanceMatrix();
    }
    

    //private utilities for constructor
    /**
     * Builds an array of Point objects representing a straight multi-nodal line in the graph.
     * Array starts with a and ends with b, with n node Point objects between them.
     * @param a first Point in line
     * @param b last Point in line
     * @param n number of subdivision Points to make between two major points
     * @return array of Point objects representing a straight multi-nodal line in the graph
     */
    private Point[] buildPointLine(Point a, Point b, int n)
    {
        Point[] line = new Point [n+2];
        
        line[0] = a;    //first position is a
        line[line.length-1] = b; //last position is b
        
        int xStart = a.getXCoordinate();
        int yStart = a.getYCoordinate();
        
        int xDifference = Math.abs(a.getXCoordinate() - b.getXCoordinate());
        int yDifference =Math.abs(a.getYCoordinate() - b.getYCoordinate());

        int xEach = xDifference / (n+1); //evenly divides distance between a and b
        int yEach = yDifference / (n+1);

        for(int j = 1; j <= n; j++) //constructs n Point objects representing nodes between a and b
        {
            Point newest = new Point(xStart + (xEach*j), yStart + (yEach*j));
            line[j] = newest; 
        }
        return line;
    }
    
    /**
     * Constructs a multi-nodal line (a path) in the graph, given an array of Point objects.
     * Builds a vertex for each Point in the array, and builds an edge for each pair of Points.
     * @param arr array of Point objects representing a multi-nodal pathway in the graph 
     */
    private void buildPath(Point[] arr)
    { 
        for(int i = 0; i < arr.length-1; i++) //for each Point in the array
        {
            Vertex<Point> u = findVertex(arr[i]); //checks whether vertex to represent the first Point already exists
            
            if( u == null) //if vertex exists, use that vertex instead of creating a new one
            {
                u = graph.insertVertex(arr[i]);//Vertex isn't in graph, and need to create a new one
            }
            
            Vertex<Point> v = findVertex(arr[i+1]);//checks whether vertex to represent the second Point already exists
            
            if( v == null) //if exists, use that vertex instead of creating a new one
            {
                v = graph.insertVertex(arr[i+1]);//Vertex isn't in graph, and need to create a new one
            }
            
            //determines the distance between vertices using pythagorean theorem
            int xLength = u.getElement().getXCoordinate() - v.getElement().getXCoordinate();
            int yLength = u.getElement().getYCoordinate() - v.getElement().getYCoordinate();
            
            double xSquared = Math.pow(xLength, 2);
            double ySquared = Math.pow(yLength, 2);
            double resultant = Math.sqrt(xSquared + ySquared);
            
            try
            {
                graph.insertEdge(u, v, resultant);  //attempts to build edge
            }
            catch(IllegalArgumentException iae) //edge already exists
            {
                //skips building that edge
            }
            
        }
    }
    
    /**
     * Builds a single vertex and edge onto a connecting vertex and edge.
     * @param p
     * @return 
     */
    private Vertex<Point> buildConnection(PointPair p)
    {
        //build the entry vertex and store reference to it for traversals
        Point a = p.getFirst();
        Point b = p.getSecond();
        
        Vertex<Point> vA = findVertex(a);
        Vertex<Point> vB = findVertex(b);
        Vertex<Point> existing;
        Vertex<Point> newest;
        
        if(vA != null) //vertex with point a already exists
        {
            newest = graph.insertVertex(b);
            existing = vA;
            
        }
        else //vertex with a doesn't exist
        {
            newest = graph.insertVertex(a);
            existing = vB;
        }
        //creates an edge between startVertex vertex and existing vertex
        
         //determines the distance between vertices
        int xLength = newest.getElement().getXCoordinate() - existing.getElement().getXCoordinate();
        int yLength = newest.getElement().getYCoordinate() - existing.getElement().getYCoordinate();

        double xSquared = Math.pow(xLength, 2);
        double ySquared = Math.pow(yLength, 2);
        double resultant = Math.sqrt(xSquared + ySquared);

        try
        {
            graph.insertEdge(newest, existing, resultant);  //attempts to build edge
        }
        catch(IllegalArgumentException iae) //edge already exists
        {
            //skips building that edge
        }
        return newest;
    }
    
    /**
     * Checks if Vertex already exists in Graph and returns it (null if vertex doesn't exist).
     * @param p Point that corresponds with a given vertex
     * @return Vertex if it is exists in graph, null otherwise
     */
    private Vertex<Point> findVertex(Point p)
    {
        for(Vertex<Point> v : graph.vertices()) //searches through all vertices in graph
        {
            if(v.getElement().equals(p)) //checks if the Point of the vertex equals p
            {
                return v;   //vertex is in the graph
            }
        }
        return null;    //vertex isn't in the graph
    }
    
    /**
     * Returns a 2D array representing the navigable distance between each vertex and every other vertex in the plan.
     * @return a 2D array representing the navigable distance between each vertex and every other vertex in the plan.
     */
    private double [][] buildVertexDistanceMatrix()
    {
        Map<Vertex<Point>, Double> answer;  //to hold all distances between a single vertex and all other vertices in graph
        int rowID;
        int columnID;
        
        int n = graph.numVertices();
        double [][] matrix = new double [n][n]; //2D array to hold all distances
        
        for(Vertex<Point> v : graph.vertices()) //for each vertex in the graph
        {
            rowID = v.getID(); //row of matrix represents v vertex ID
            answer = AdjacencyMapGraph.shortestPathLengths(graph, v);//find distances from v to every other vertex
            
            for(Entry<Vertex<Point>,Double> e : answer.entrySet())//for every vertex in answer
            {
                columnID = e.getKey().getID(); //column of matrix represents e vertex ID
                double distance = e.getValue();
                
                matrix[rowID][columnID] = distance;    //save value in matrix in appropriate location
            }
        }
 
        return matrix;
    }
    
    
    
    // ------------------------------------- fastestRoute() ----------------------------------- //
    
    /**
     * Calculates the shortest (fastest) route in a store from Entrance to Checkout, given a unordered list of Bins to visit.
     * @param list Bins to visit in the store
     * @return ordered list of Bins, representing this fastest route to visit selected bins
     */
    @Override
    public List<Bin> fastestRoute(List<Bin> list)
    {    
//        long startTime = System.currentTimeMillis();//Diagnostic
        List<Bin> ordered = new ArrayList<>();  
        
        
        //"part 1 - calculate route as vertices"
        List<Vertex<Point>> dest = getDestinations(list, true);
        
        if(dest.size() > 11)    //above 9 bins, need to cluster to meet performance requirements
        {
            List<Vertex<Point>>[] clusters = buildClusters(dest, dest.size()); //clusters bins into groups of less than 10
            printClusters(clusters); //Diagnostic
            shortestPath = overallRouteBetter(clusters);  //calculates the shortest path given these clusters
        }
        else //brute force all vertices directly
        {
            GraphNavEngine.this.calcShortestPath(dest, 1, 1, Double.MAX_VALUE); //calculates shortest path of all selected vertices at once
        }
        
        System.out.println(Arrays.toString(shortestPath.toArray()));//Diagnostic
//        long endTime = System.currentTimeMillis();//Diagnostic
//        System.out.println("Time for part 1: " + (endTime - startTime));//Diagnostic
        
        

        //"part 2 - translate into ordered list of Bins"
//        startTime = System.currentTimeMillis();//Diagnostic
        for(int i = 1; i < shortestPath.size()-1; i++)  //for every vertex in shortest path except first and last (no Bins at these two)
        {
            Vertex<Point> v = shortestPath.get(i);  
            Queue<DestinationPair> matches = new LinkedQueue<>(); //represents a list of all bin/vertex pairs that match given vertex
            
            for(DestinationPair dp : binVertexPairs)    //for each remaining pair
            {
                if(dp.getElement().equals(v))   //find the pair or pairs that matches distance vertex (some vertices have bins on both sides)
                {
                    matches.enqueue(dp); //add it to back of queue
                }
            } //found all bin/vertex pairs that match the distance vertex
            
            while(!matches.isEmpty()) //for each pair in the queue
            {
               DestinationPair pair = matches.dequeue();
               Bin newest = pair.getFirst(); 
               ordered.add(newest); //add the Bin of this pair to back of the ordered list of Bins
               binVertexPairs.remove(pair);    //remove pair from list for next search
            }
   
        }
//        endTime = System.currentTimeMillis();//Diagnostic
//        System.out.println("Time for part 1: " + (endTime - startTime));//Diagnostic
        return ordered;
    }
    
    
    
    //private utilities for fastestRoute()
    /**
     * Returns an unordered list of destination vertices, given an unordered list of bins selected by the customer.
     * @param selected list of bins selected by customer to visit
     * @param includeEnds whether list should include predefined startVertex and endVertex vertices
     * @return  unordered list of vertices as destinations
     */
    private List<Vertex<Point>> getDestinations(List<Bin> selected, boolean includeEnds)
    {
        List<Vertex<Point>> list = new ArrayList<>();
        binVertexPairs = new ArrayList<>(); //to store for later use when needing to reference which bins correspond to which vertices
        
        if(includeEnds)
        {
            list.add(startVertex); //list starts with entrance vertex
        }
        for(Bin b : selected)   //for each bin selected
        {
            int xSearch = b.getXFront();
            int ySearch = b.getYFront();
            int unit = b.getUnit();
            
            //searches through vertices in graph to find the one with coordinates that most closely match the bin's 'front' coordinates
            for(Vertex<Point> v : graph.vertices())
            {
                int vertexX = v.getElement().getXCoordinate();
                int vertexY = v.getElement().getYCoordinate();
                
                if(Math.abs(vertexX - xSearch) < unit/2 && Math.abs(vertexY - ySearch) < unit/2 )
                {
                    list.add(v); //add the vertex to our list of vertices to visit
                    binVertexPairs.add(new DestinationPair(b,v)); //add the vertex / bin pair to our stored list of bin/vertex pairs for later
                }
            }
        }
        
        if(includeEnds)
        {
            list.add(endVertex); //list ends with checkout vertex
        }
        
        removeDoubles(list);//now checks for doubles and removes them; only need to visit a vertex once
        
        return list;
    }
    
    /**
     * Returns a single destination vertex, given a selected bin.
     * @param b Bin to determine destination vertex for
     * @return a single destination vertex
     */
    private Vertex<Point> getDestinations(Bin b)
    {
        int xSearch = b.getXFront();
        int ySearch = b.getYFront();
        int unit = b.getUnit();
        
        //searches through vertices in graph to find the one with coordinates that most closely match the bin's 'front' coordinates
        for(Vertex<Point> v : graph.vertices())
        {
            int vertexX = v.getElement().getXCoordinate();
            int vertexY = v.getElement().getYCoordinate();

            if(Math.abs(vertexX - xSearch) < unit/2 && Math.abs(vertexY - ySearch) < unit/2 )
            {
                binVertexPairs.add(new DestinationPair(b, v));//add the vertex / bin pair to our stored list of bin/vertex pairs for later
                return v;
            }
        }
        return null; //no vertex exists in the graph with coordinates that closely match b
    }
    
    /**
     * Takes unordered list of all destination vertices to visit and groups them
     * into an ordered array clusters organized in the store from left to right.
     * This is so brute force can be employed to calculate the shortest
     * path between all destination vertices in a reasonable time frame.
     * Checks to ensure that no single cluster has more than 10 vertices
     * (brute force shortest path is too slow with larger quantities).
     * Only use this method if list has greater than 10 vertices. 
     * With 10 or less, direct brute force has a reasonable running time.
     * @param list unordered list of all destination vertices to visit
     * @param items number of items to get in the store; used to calculate number of clusters to break list into
     * @return array of 'clusters' of vertices, organized in the store from left to right
     */
    private List<Vertex<Point>>[] buildClusters(List<Vertex<Point>> list, int items)
    {
        int n = items;
        List<Vertex<Point>>[] clustersArray = cluster(list, n); //run once

        //if a cluster with more than 10 vertices, too long to calculate; better to re-cluster with larger number of clusters than continue
        while(!validateClusters(clustersArray)) //checks whether any cluster has more than 10 vertices
        { 
            n += 5;
            clustersArray = cluster(list, n); //try again 
        }
        
        return clustersArray;    
    } 
    
    /**
     * Computes the brute force shortest distance for each 'cluster' of vertices 
     * in the array of ordered clusters and combines them into a single overall route.
     * This version is an old version.
     * 
     * @param arr ordered array clusters organized in the store from left to right
     * @return list of vertices ordered in the shortest (fastest) route from the first vertex to the last in the list
     */
    private List<Vertex<Point>> overallRoute(List<Vertex<Point>>[] arr)
    {
        List<Vertex<Point>> route = new ArrayList<>();
        
        //"first"
        //brute force the starting and ending clusters
        System.out.println("First");
        
        int entranceIndex = -1;
        int checkoutIndex = -1;
        
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i].contains(startVertex))
            {
                entranceIndex = i;
                break;
            }   
        }
        
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i].contains(endVertex))
            {
                checkoutIndex = i;
                break;
            }   
        }
        //put entrance vertex at beginning of first cluster
        for(int i = 0; i < arr[entranceIndex].size(); i++)
        {
            if(arr[entranceIndex].get(i).equals(startVertex))
            {
                Vertex<Point> removed = arr[entranceIndex].remove(i); 
                arr[entranceIndex].add(0,removed);
            }
        }
        
        //brute force first cluster
        List<Vertex<Point>> current = arr[entranceIndex]; //get first cluster
        if(current.size() == 1)
        {
            route.add(current.get(0));
        }
        else
        {
           GraphNavEngine.this.calcShortestPath(current, 1, 0, Double.MAX_VALUE);
            for(int i = 0; i < shortestPath.size(); i++)
            {
                route.add(shortestPath.get(i)); //add values to route
            } 
        }
        
        
        //"middle"
        System.out.println("Middle");
        //brute force every cluster except the checkout cluster
        for(int i = 0; i < arr.length; i++)
        {
            if(i == checkoutIndex || arr[i].isEmpty())
            {
                //skip remainder of distance loop   
            }
            else if(i != checkoutIndex && i != entranceIndex && arr[i].size() == 1)
            {
                route.add(arr[i].get(0));
            }
            else if(i != checkoutIndex && i != entranceIndex) //save for last
            {
                System.out.println("Cluster " + i);
                int last = shortestPath.size()-1;
                arr[i].add(0, shortestPath.get(last));//get last element in calcShortestPath and add to front of distance list
                
                current = arr[i]; //get this cluster
                GraphNavEngine.this.calcShortestPath(current, 1, 0, Double.MAX_VALUE);
                
                for(int j = 1; j < shortestPath.size(); j++)    //don't add duplicate of first
                {
                    route.add(shortestPath.get(j)); //add values to route
                }  
            }
        }
        //all middle clusters have been added to route
        
        System.out.println("Last");
        //"last"
        //make sure endVertex is at end of clusters
        
        
//        for(int i = 0; i < arr[checkoutIndex].size(); i++)
//        {
//            if(arr[checkoutIndex].get(i).equals(endVertex))
//            {
//                Vertex<Point> removed = arr[checkoutIndex].remove(i); 
//                int last =  arr[checkoutIndex].size()-1;
//                arr[checkoutIndex].add(last,removed);
//            }
//        }
        
        
        int last = shortestPath.size()-1;
        arr[checkoutIndex].add(0, shortestPath.get(last));//get last element in calcShortestPath and add to front of distance list
        
        current = arr[checkoutIndex]; //get last cluster
        if(current.size() > 3)
        {
            GraphNavEngine.this.calcShortestPath(current, 1, 1, Double.MAX_VALUE);  //doesn't change first or last vertices
        }
        else    //contains 1 or two items
        {
            shortestPath = current;
        }

        for(int j = 1; j < shortestPath.size(); j++) //don't add duplicate of first
        {
            route.add(shortestPath.get(j)); //add values to route
        }
        
         
        
        System.out.println("Calculated route.");
        
        return route;
        
    }
    
    /**
     * Computes the brute force shortest distance for each 'cluster' of vertices 
     * in the array of ordered clusters and combines them into a single overall route.
     * 
     * This version take into account that the clusters are sorted first, and that the 
     * first cluster is the entrance cluster and the last cluster is the checkout cluster.
     * 
     * @param arr ordered array clusters organized in the store from left to right
     * @return list of vertices ordered in the shortest (fastest) route from the first vertex to the last in the list
     */
    private List<Vertex<Point>> overallRouteBetter(List<Vertex<Point>>[] arr) 
    {
        List<Vertex<Point>> route = new ArrayList<>();
        
        //"add entrance cluster to route"
        if(arr[0].size() == 1) //add single vertex to route
        {
            route.add(arr[0].get(0));
            shortestPath = arr[0]; //stores distance shortest path
        }
        else//cluster contains more than one vertex, and shortest route between vertices must be calculated
        {
            //makes sure that entrance vertex (69) is moved to the beginning of the list
            for(int i = 0; i < arr[0].size(); i++)
            {
                if(arr[0].get(i).equals(startVertex)) //current index holds entrance vertex
                {
                    Vertex<Point> removed = arr[0].remove(i); 
                    arr[0].add(0,removed); //move to front of list
                }
            }
            
            //uses brute force to calculate shortest path between all nodes
            //index 0 (entrance vertex) in list remains fixed at the start for all permutations
            //method stores the shortest path in the class field
            GraphNavEngine.this.calcShortestPath(arr[0], 1, 0, Double.MAX_VALUE);     
            
            for(int i = 0; i < shortestPath.size(); i++) //include first vertex in path
            {
                route.add(shortestPath.get(i)); //add vertices to route
            }   
        }
        
        //"add middle clusters to route"
        for(int i = 1; i < arr.length-1; i++)
        {
            if(arr[i].size() == 1) //add single value to route
            {
                route.add(arr[i].get(0));  
                shortestPath = arr[i]; //store as shortest path
            }
            
            else//cluster contains more than one vertex, and shortest route between vertices must be calculated
            {
                int last = shortestPath.size()-1;
                arr[i].add(0, shortestPath.get(last));//get end vertex of previous cluster's shortest path and add to front of distance list
                
                //uses brute force to calculate shortest path between all nodes
                //first index in list (end vertex of previous cluster's shortest path) remains fixed during permutations
                //method stores the shortest path in the class field
                GraphNavEngine.this.calcShortestPath(arr[i], 1, 0, Double.MAX_VALUE);
                
                for(int j = 1; j < shortestPath.size(); j++)    //don't add duplicate of end vertex of previous cluster's shortest path
                {
                    route.add(shortestPath.get(j)); //add values to route
                }  
            }
        }
        
        
        //"add checkout cluster to route"
        int index = arr.length-1;
        if(arr[index].size() == 1) //add single vertex to route
        {
            route.add(arr[index].get(0));
            shortestPath = arr[index];
        }
        else //cluster contains more than one vertex, and shortest route between vertices must be calculated
        {
            for(int i = 0; i < arr[index].size(); i++) //put checkout vertex (70) at the end of the list
            {
                if(arr[index].get(i).equals(endVertex))
                {
                    Vertex<Point> removed = arr[index].remove(i); 
                    arr[index].add(removed);    //add to end
                } 
            }

            int last = shortestPath.size()-1;
            arr[index].add(0, shortestPath.get(last));//get end vertex of previous cluster's shortest path and add to front of distance list
            
            //uses brute force to calculate shortest path between all nodes
            //first index in list (end vertex of previous cluster's shortest path) remains fixed during permutations
            //last index in list (checkout vertex) remains fixed during permutations
            //method stores the shortest path in the class field
            GraphNavEngine.this.calcShortestPath(arr[index], 1, 1, Double.MAX_VALUE);
            
            for(int i = 1; i < shortestPath.size(); i++) //don't add duplicate of end vertex of previous cluster's shortest path
            {                                                                                           //??Start at 1 instead?
                route.add(shortestPath.get(i)); //add vertices to route
            }
        }
  
        System.out.println("Calculated route.");

        return route;
    }
    
    /**
     * Determines shortest path between startVertex, subset, and endVertex. Old version.
     * All permutations startVertex with startVertex and endVertex with endVertex.
     * 
     * @param start
     * @param end
     * @param subset 
     * @param k 
     * @param d 
     * @return  
     */
    private double calcShortestPath(Vertex<Point> start, Vertex<Point> end, List<Vertex<Point>> subset, int k, double d)
    {    
        
        double minimum = d;
        
        for(int i = k; i < subset.size(); i++)
        {
            swap(subset, i, k); //swap index i and k
            minimum = calcShortestPath(start, end, subset, k+1, minimum);  //recursive call to method
            swap(subset, k, i); //swap index in and k back
        }
        
        if(k == subset.size()-1)    //you've created a permutation of the trip subset
        {
            List<Vertex<Point>> permutation = new ArrayList<>(); //holds the permutation plus the startVertex and endVertex
            
            permutation.add(start);
            for(Vertex<Point> v : subset)
            {
                permutation.add(v);
            }
            permutation.add(end);
            
            
//            System.out.print(Arrays.toString(permutation.toArray())); //DIAGNOSTIC ONLY
            
              
            
            //calculate the length of the path of this permutation
            int total = 0;
            for(int j = 0; j < permutation.size()-1; j++)
            {
                Vertex<Point> a = permutation.get(j);
                Vertex<Point> b = permutation.get(j+1);
                
                total += vertexDistances[a.getID()][b.getID()];
                
                if(total >= minimum) //if at any point, total is greater than or equal to minimum
                {
//                    System.out.println("Ended"); //DIAGNOSTIC ONLY
                    permutations++;
                    return minimum; //stop adding up total, because this permutation isn't the minimum
                }
            }
            System.out.println(" Total: " + total + ":" +permutations++);  //DIAGNOSTIC ONLY
            
            //store distance and permutation number if it is the shortest
            if(total < minimum)
            {
                minimum = total;
                shortestPath = permutation;
                return total;
            }   
        } 
        return minimum;
    }
    
    /**
     * Determines shortest path between all vertices, given starting index in list.
     * Use if 12 or fewer vertices to traverse.
     * First element to vary in permutation is k.
     * Last element to vary in permutation is (list.length - 1 - n).
     * Indexes k-1 remain in static ordered. Indexes (list.length - 1 - n) and above remain in static ordered.
     * 
     * i.e. if k = 2, first list index to vary in permutations is 2 (the third item in list)
     * i.e. if n = 2, the last list index to vary in permutations is 2 positions before the last position.
     * 
     * @param list list of must-pass vertices
     * @param k first index to vary in permutation
     * @param n last n indexes from end of list are not varied in permutation
     * @param dist distance shortest distance of any permutation
     * @return shortest distance of any permutation
     */
    private double calcShortestPath(List<Vertex<Point>> list, int k, int n, double dist)
    {    
        
        double minimum = dist; //represents distance minimum path length
        
        for(int i = k; i < list.size()-n; i++)    //doesn't include last position in list
        {
            swap(list, i, k); //swap index i and startVertex
            minimum = GraphNavEngine.this.calcShortestPath(list, k+1, n, minimum);  //recursive call to method with incremented k value
            swap(list, k, i); //swap index in and startVertex back
        }
        
        if(k == list.size()-1-n)    //you've created a permutation of the trip list (doesn't include last n positions in k)
        {      
            List<Vertex<Point>> permutation = new ArrayList<>(); //holds the permutation

            for(Vertex<Point> v : list) //for each vertex in list (now a single permutation)
            {
                permutation.add(v);// adds to permutation
            }
            
            System.out.print(Arrays.toString(permutation.toArray())); //DIAGNOSTIC ONLY
            
              
            
            //calculate the length of the path of this permutation
            int total = 0;
            for(int j = 0; j < permutation.size()-1; j++) //for each pair of vertices in the permutation
            {
                Vertex<Point> a = permutation.get(j);
                Vertex<Point> b = permutation.get(j+1);
                
                total += vertexDistances[a.getID()][b.getID()]; //get per-calculated distance from Vertex Distance Matrix
                
                if(total >= minimum) //if at any point, total is greater than or equal to minimum
                {
                    System.out.println(" Ended"); //DIAGNOSTIC ONLY
                    permutations++; //DIAGNOSTIC ONLY
                    return minimum; //stop adding up total and skip remainder of method, because this permutation isn't the minimum
                }
            }
            System.out.println(" Total: " + total + ":" + permutations++);  //DIAGNOSTIC ONLY
            
            //for each permutation, compares its total length to the distance shortest length
            //store distance and permutation number if it is the shortest
            if(total < minimum)
            {
                minimum = total;
                shortestPath = permutation;
                return total;
            }   
        } 
        return minimum;
    }
    
      //START HERE TOMORROW
    ////utilities for getDestinations()
    /**
     * Removes any doubles from the unordered list of vertices.
     * Customers only need to visit a single vertex once throughout their trip.
     * @param list list of unordered vertices
     */
    private void removeDoubles(List<Vertex<Point>> list)
    {
        for(int i = 0; i < list.size(); i++) //for each vertex in list
        {
            for(int j = 0; j < list.size(); j++) //for each vertex in the list
            {
                if(j == i)
                {
                    continue;   //skip, because it is the same vertex (not a double)
                }
                
                if(list.get(i).equals(list.get(j))) //vertex appears twice in list
                {
                    list.remove(j); //removes the double
                }
            }
        }
    }
    
  
    ////utilities for buildClusters()
    /**
     * Takes a list of m unordered vertices and makes k clusters, where each cluster contains its nearest neighbors.
     * @param list list of unordered vertices
     * @param m provides a way to calculate number of clusters to end up with, want each cluster to have 10 or fewer m in it
     * @return array of vertex clusters, where each cluster is a list of vertices
     */
    private List<Vertex<Point>>[] cluster(List<Vertex<Point>> list, int m)
    {
        List<Vertex<Point>>[] buckets = new List[list.size()];  //bucket array for vertices
        List<EdgePair> used = new ArrayList<>(); //bucket of used shortest distances to ensure no shortest distance is used twice
        int numBuckets = 0;
        
        for(int i = 0; i < buckets.length; i++) //construct all buckets
        {
            buckets[i] = new ArrayList<>();
            numBuckets++;
        }
        
        //start with m clusters
        for(int i = 0; i < list.size(); i++) //put each element from list into its own bucket
        {
            buckets[i].add(list.get(i));
        }
        
        //calculate number of buckets to end up with
        int k = m / 2; //i.e. with 20 m, using 9 buckets seemed to work fine
        
        //reduce to k clusters
        while(numBuckets > k) //until we have k or fewer buckets remaining
        {
            //"shortest new path"
            //determine overall shortest path between any two vertices in the list
            double shortest = Double.MAX_VALUE; //reset value
            double distance;
            Vertex<Point> first = null;    //determine the two vertices that formed the shortest path
            Vertex<Point> second = null;
            EdgePair winner;    //represents a pair of vertices and a distance between them
            
            for(Vertex<Point> v : list) //for each vertex in list
            {
                for(Vertex<Point> u : list) //for each vertex in list
                {
                    distance = vertexDistances[v.getID()][u.getID()]; //get distance based on vertex IDs and vertex distance matrix
                    EdgePair newest = new EdgePair(v,u,distance);    //construct temporary object that is easy to compare
                    
                    //if distance is equal to zero, v and u are the same, disregard
                    //if distance is not shorter than current shortest, disregard
                    //shortest distance may be the same as another distance, but needs a unique combination of vertices to count
                    //equals method returns true if vertex pair is in either 1,2 order or 2,1 order (i.e. u,v or v, u)
                    if (distance != 0 && distance < shortest && !used.contains(newest))
                    {
                        shortest = distance; //store for future comparison
                        first = v;
                        second = u;
                    }
                }
            }//we have the new shortest edge
            
            if(first == null || second == null) //value-safe check
            {
                continue; //skip rest of loop
            }
            
            used.add(new EdgePair(first,second,shortest)); //add to used list to prevent duplication
            

            //"add to cluster"
            int bucketFirst = -1;
            int bucketSecond = -1;
            
            for(int i = 0; i < buckets.length; i++) //for all buckets in the array
            {
                if(buckets[i].contains(first))//find the bucket that contains the first vertex
                {
                    bucketFirst = i;
                    break; //stop searching once it is found
                }
            }
            
            for(int i = 0; i < buckets.length; i++) //for all buckets in the array
            {            
                if(buckets[i].contains(second))//find bucket that contains second vertex
                {
                    bucketSecond = i;
                    break; //stop searching once it is found
                }
            }
            
            if(bucketFirst == bucketSecond) //vertices are already in the same bucket
            {
                //do nothing
            }
            else//if vertices are not already in the same bucket
            {
                int counter = 0;
                while(!buckets[bucketSecond].isEmpty())//remove all contents from second bucket and add to first bucket
                {
                    Vertex<Point> removed = buckets[bucketSecond].remove(counter);
                    buckets[bucketFirst].add(removed);
                }
                
                //remove second bucket from array (reduce numBuckets, which does a similar thing without needing to rebuild the array)
                numBuckets--;
                
            }
            System.out.println(numBuckets);
        }
        System.out.println("Finished clustering."); //diagnostic
        
        return sortClusters(buckets); //organizes the clusters in the bucket array        
    }
    
    /**
     * Tests whether each cluster in the array has 10 or fewer vertices.
     * @param arr bucket array of clusters (vertices)
     * @return true if no cluster has more than 10 vertices, false otherwise
     */
    private boolean validateClusters(List<Vertex<Point>>[] arr)
    {
        for(List<Vertex<Point>> l : arr) //for each bucket in the array
        {
            if(l.size() > 10) //if the bucket has more than 10 vertices in it
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Standardizes an unordered bucket array of vertices (clusters).
     * Removes any empty buckets from array
     * Places the cluster with the entrance vertex (69) at the front of the array
     * Places the cluster with the checkout vertex (70) at the end of the array
     * Organizes the remaining clusters so that second cluster is the rightmost in the store
     * and the second to last is the leftmost.
     * @param arr unordered bucket array of vertices (clusters)
     * @return standardized bucket array of vertices
     */
    private List<Vertex<Point>>[] sortClusters(List<Vertex<Point>>[] arr)
    {
        int n = 0;
        int arrStart = -1;
        int arrEnd = -1;
        int tempIndex = 1;
        
        //determine number of filled buckets in arr
        for(int i = 0; i < arr.length; i++)
        {
            if(!arr[i].isEmpty())
            {
                n++;
            }   
        }
        
        List<Vertex<Point>>[] temp = new List[n];
        
        for(int i = 0; i < temp.length; i++) //create a bucket for each filled bucket in arr
        {
            temp[i] = new ArrayList<>();
        }
        
        //first index position contains list with startVertex
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i].contains(startVertex)) //find cluster with entrance vertex (69)
            {
                temp[0] = arr[i];
                arrStart = i;
            }   
        }
        
        //last index position contains list with endVertex
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i].contains(endVertex)) //find cluster with checkout vertex (70)
            {
                temp[temp.length-1] = arr[i];
                arrEnd = i;
            }   
        }
        
        while(tempIndex < n-1) //until remaining positions in temp array have values
        {
            //middle clusters are organized left to right in store
            //calculate average x-coordinate for a single cluster
            //find the largest x-coordinate cluster in remaining clusters
            //add that cluster next in temp
            
            int eastmost = -1;
            int largestX = Integer.MIN_VALUE;
            

            for(int i = 0; i < arr.length; i++) //for each remaining cluster
            {
                int total = 0;  //reset to zero
                
                if(i == arrStart || i == arrEnd) //bucket array contains a single cluster
                {
                    continue; //skip remainder of loop
                }
                if(!arr[i].isEmpty()) //bucket array contains clusters
                {
                    for(int m = 0; m < arr[i].size(); m++)
                    {
                        total += arr[i].get(m).getElement().getXCoordinate();
                    }
                    int average = total / arr[i].size(); //determine the average x-coordinate value for this cluster
                    
                    if(average > largestX) //find the right-most average for all remaining clusters
                    {
                        eastmost = i;
                        largestX = average;
                    }   
                } 
            }

            //found the rightmost cluster in remaining clusters
            //add cluster to temp, delete cluster from arr
            for(int i = 0; i < arr[eastmost].size(); i++)
            {
                Vertex<Point> newest = arr[eastmost].get(i);
                temp[tempIndex].add(newest);
            }
            tempIndex++;
            arr[eastmost].clear();
        }
        
        //add remaining clusters have been added to temp
        return temp;
        
    }
    
    /**
     * Prints out a String representation of all clusters to the console.
     * @param arr bucket array of clusters
     */
    private void printClusters(List<Vertex<Point>>[] arr)
    {
        for(int i = 0; i < arr.length; i++)
        {
            if(arr[i].isEmpty())
            {
                continue;   //skip remainder of loop
            }
            else
            {
                System.out.println("Cluster " + i + ": ");
                System.out.println(Arrays.toString(arr[i].toArray()));
            }
            System.out.println("");
        }
    }
    
    
    ////utilities for calcShortestPath()
    /**
     * Swaps given index positions in given list.
     * @param list list of vertices
     * @param a first position in list to swap with second
     * @param b second position in list to swap with first
     */
    private void swap(List<Vertex<Point>> list, int a, int b) throws IndexOutOfBoundsException
    {
        if(a < 0 || a >= list.size())
        {
            throw new IndexOutOfBoundsException("a is out of bounds: " + a);
        }
        if(b < 0 || b >= list.size())
        {
            throw new IndexOutOfBoundsException("b is out of bounds: " + b);
        }
        
        if(list.get(a).equals(list.get(b)))
        {
            return; //do nothing
        }
        
        //swap values
        Vertex<Point> temp = list.get(a);
        list.set(a, list.get(b));
        list.set(b, temp);      
    }
    
    
    // ------------------------------------- getDirections() ----------------------------------- //
    
    /**
     * Returns a String representation of a grocery list, describing items to get in the store and their locations.
     * @param route ordered list of Bins, representing the route order to visit Bins
     * @param select list of all Selections which contains the same Bins as the first list (albeit out of order)
     * @return a String representation of a grocery list
     */
    @Override
    public String getDirections(List<Bin> route, List<Selection> select)
    {
        String directions = "Here's the shortest route to pick up your items:\n";
        
        //make copy of select
        List<Selection> copy = new ArrayList<>(select.size());
        for(Selection s : select)
        {
            copy.add(s);
        }
        
        while(!copy.isEmpty()) //while select still contains Selections
        {
            int column = 0; //reset
            
            for(Bin b : route) //for each bin on route
            {
                //determine the aisle
                if(b.getColumn() == column) //same aisle as before
                {
                    //do nothing
                }
                else
                {
                    column = b.getColumn();    //different aisle from before                   
                    double conversion = (double) column/ 2 + 0.5;//convert column of Bin into store aisle

                    directions += "\nGo to Aisle " +  (int) conversion + ":\n";  
                }
                
                
                
                //determine the items to get in that aisle
                Selection match = null; //reset
                
                for(Selection s : copy) //search through remaining Selection objects in select
                {
                    if(s.getItem().getBin().equals(b)) //Bin of Selection matches Bin of route
                    {
                        match = s;  //found a match
                        break;  //stop searching through select
                    }
                }
                
                if(match != null) //found a match
                {
                    int number = match.getCount(); //get count of the Item in Selection
                    String plural = number > 1 ? "s" : ""; //for counts greater than one, adds an "s" to the end of Item's name

                    //i.e. " - Get 5 bananas"
                    directions += " - Get " + number + " " + match.getItem().getName() + plural + " from Bin " + b.getName() + "\n";
                    
                    copy.remove(match);   //remove from selection list
                }  
                
            }
            
        }
        directions += "\nYou've got everything. Checkout!";
        
        return directions;
    }
    
  
    
    // ------------------------------------- paint() ----------------------------------- //
    /**
     * Creates a graphic object representing all of the vertices in the graph.
     * @param g Graphic object on which graph is drawn.
     */
    @Override
    public void paint(Graphics g)
    {         
        for(Vertex<Point> p : graph.vertices())//constructs a graphical sphere for each vertex in graph
        {
            Sphere newest = new Sphere(p, Color.GRAY);

            newest.paint(g);
        }   
    }
    
 
    // ------------------------------------- distance matrix creation methods ----------------------------------- //
    /**
     * Returns a 2D array representing the navigable distance between each bin and every other bin in the plan.
     * @param plan plan of a store
     * @return 2D array representing the navigable distance between each bin and every other bin in the plan
     */
    private double [][] buildBinDistanceMatrix(StorePlan plan)
    {
        Map<Vertex<Point>, Double> answer;  //to hold all distances between a single vertex and all other vertices in graph
        double [][] matrix;
        double distance;
        int n;
 
        n = plan.getBinCount(); //total number of columns and rows in matrix
        matrix = new double [n][n]; //construct matrix
        
        
        for(int i = 0; i < n; i++)  //once for each bin in plan
        { 
            distance = -1;  //flag for something wrong
            Bin current = plan.getBin(i);        //get bin i     
            Vertex<Point> destination = getDestinations(current);    //get vertex for distance bin        
           
            answer = AdjacencyMapGraph.shortestPathLengths(graph, destination); //find distances from vertex to every other vertex
           
            
            for(int j = 0; j < n; j++)  //find distance from i bin to every other bin j
            {
               if(i == j) //distance from bin 0 to bin 0 (itself)
               {
                   matrix[i][j] = 0; //zero distance between bin and itself
                   continue;    //skip remainder of this iteration
               }
               
               
               Bin other = plan.getBin(j); //get other bin
               Vertex<Point> beginning = getDestinations(other);   //get vertex for other bin             
               
               
               for(Entry<Vertex<Point>,Double> e : answer.entrySet())//search through answer to find the vertex that matches beginning
               {
                   if(e.getKey().equals(beginning)) //e matches beginning
                   {
                       distance = e.getValue(); //get distance from destination to e
                       break;   //got the value needed, stop searching
                   }
               }
               
               matrix[i][j] = distance; //store that value for the correct bin
            }
        }
        return matrix;
        
    }
    
    
    /**
     * Prints to the console all distances between each vertex and every other vertex in the plan.
     */
    private void printVertexDistanceMatrix()
    {
        Map<Vertex<Point>, Double> answer;  //holds all matrix between a single vertex and all other vertices in graph
        
        int n = graph.numVertices();
        double [][] distances = new double [n][n];
        int row = 0;
        int col = 0;

        for(Vertex<Point> v : graph.vertices()) //for each vertex in graph
        {
            answer = AdjacencyMapGraph.shortestPathLengths(graph, v);    //get matrix between v and all other vertices
 
            for(Entry<Vertex<Point>,Double> e : answer.entrySet()) //for every vertex in graph
            {
                System.out.println(v.getElement() + " to " + e.getKey().getElement() + "= " + e.getValue());
            }            
            row++;
        }
    }
    
    /**
     * Prints a string representation of given 2D matrix to the console, for diagnostic purposes.
     * @param arr 2D matrix to print
     * @param letter letter to go before each category (i.e. "B" for bin, "V" for vertex)
     */
    private void printMatrix(double [][] arr, String letter)
    {
        //header
        System.out.printf("%10s"," ");
        
        for(int i = 0; i < arr.length; i++) //for each row of matrix
        {
            System.out.printf("%10s",letter + i);
        }
        System.out.println("");
        
        
        
        //body of matrix
        for(int i = 0; i < arr.length; i++) //for each row of matrix
        {
            System.out.printf("%10s",letter + i + " ");
            
            for(int j = 0; j < arr[i].length; j++) //for each column in that row
            {
                System.out.printf("%10s",arr[i][j]);
            }
            System.out.println("\n");
        }
    }
    
    /**
     * Writes 2D matrix to a text file as space separated values, with newline denoting subsequent row.
     * Does not include letters in front of the row or above column denoting identification.
     * You must keep track of which value is which. For example, with the Bin matrix, Bins 
     * are numbered 0 - 80, and each position in the matrix corresponds to those numbers.
     * Row 0, Column 80 corresponds to the distance between Bin 0 and Bin 80.
     * 
     * @param arr matrix to save to a file
     * @param filename name of the file
     */
    private void saveMatrix(double [][] arr, String filename)
    {
        try 
        {
            FileOutputStream fos = new FileOutputStream(filename, false);
            PrintStream ps = new PrintStream(fos);
            
            for(int i = 0; i < arr.length; i++) //for each row
            {
                for(int j = 0; j < arr[i].length; j++) //for each column
                {
                    ps.printf("%10s",arr[i][j]);
                }
                ps.println("\n");
            }
            
            ps.close(); //to save the file
            
        } catch (FileNotFoundException e) 
        {
            System.out.println("Not Found!");
        }
       
    }
    
    
    
    // ------------------------------------- additional class methods ----------------------------------- //        
    //private accessor methods
    /**
     * Returns the startVertex (entrance vertex) of the graph.
     * @return the startVertex (entrance vertex) of the graph.
     */
    private Vertex<Point> getStartVertex()
    {
        return startVertex;
    }
    
    /**
     * Returns the endVertex (checkout vertex) of the graph.
     * @return the endVertex (checkout vertex) of the graph.
     */
    private Vertex<Point> getEndVertex()
    {
        return endVertex;
    }
    
    /**
     * Returns the currently stored shortest path in the graph that includes all destination vertices.
     * @return the currently stored shortest path in the graph that includes all destination vertices.
     */
    private List<Vertex<Point>> getShortestPath()
    {
        return shortestPath;
    }
    
    /**
     * Returns a count of the current permutation number in the current brute force calculation, for diagnostics.
     * @return a count of the current permutation number in the current brute force calculation
     */
    private int getPermutations()
    {
        return permutations;
    }
    
        
    //private update methods
    /**
     * Stores given 2D array as the distance between each vertex and every other vertex.
     * @param arr 2D array representing the distance between each vertex and every other vertex
     */
    private void setVertexDistances(double [][] arr)
    {
        vertexDistances = arr;
    }
    
    /**
     * Stores given 2D array as the distance between each Bin and every other Bin.
     * @param arr 2D array representing the distance between each Bin and every other Bin 
     */
    private void setBinDistances(double [][] arr)
    {
        binDistances = arr;
    }
    
    
    //private additional methods
    /**
     * Prints a representation of the graph to the console for diagnostic purposes.
     */
    private void printGraph()
    {
        System.out.println(graph);
    }
    
    /**
     * Resets permutation count to 0.
     */
    private void resetPermutations()
    {
        permutations = 0;
    }
    
    
   // ------------------------------------- main method for diagnostics ----------------------------------- //
     
    /**
     * Testing only. 
     * @param args 
     */
    public static void main(String[] args) {
        StorePlan plan = new StorePlan(25,25,800, Color.LIGHT_GRAY);
        List<Bin> selected = new ArrayList<>();
        selected.add(plan.getBin(1));
        selected.add(plan.getBin(66));
        selected.add(plan.getBin(45));
        selected.add(plan.getBin(74));
        selected.add(plan.getBin(13));
        selected.add(plan.getBin(24));
        selected.add(plan.getBin(35));
        selected.add(plan.getBin(54));
        selected.add(plan.getBin(80));
        selected.add(plan.getBin(29));//10 1730 ms v1 and 639 ms v2
        selected.add(plan.getBin(48));//11 11800 ms v1 and 3187 ms v2
        selected.add(plan.getBin(44));//12 95588 ms v1 and 31362 ms v2
        
//        Item a = new FoodItem(plan.getBin(1), "banana");
//        Item b = new FoodItem(plan.getBin(66), "toothpaste");
//        
//        
//        
//        List<Selection> s = new ArrayList<>();
//        s.add(new ItemSelection(a, 1, 4.00));
//        s.add(new ItemSelection(b, 2, 5.00));
        
        
        GraphNavEngine grid = new GraphNavEngine(plan,3);
        List<Bin> result = grid.fastestRoute(selected);
        System.out.println(Arrays.toString(result.toArray()));
        
//        System.out.println(grid.getDirections(result, s));
        
//        System.out.println(plan.getBin(24).getAisle());
//        System.out.println(plan.getBin(30).getAisle());
//        System.out.println(plan.getBin(36).getAisle());
//        System.out.println(plan.getBin(42).getAisle());
//        System.out.println(plan.getBin(48).getAisle());
//        System.out.println(plan.getBin(54).getAisle());
//        System.out.println(plan.getBin(60).getAisle());
//        System.out.println(plan.getBin(66).getAisle());
//        System.out.println(plan.getBin(72).getAisle());
//        System.out.println(plan.getBin(80).getAisle());
//        System.out.println(plan.getBin(0).getAisle());
        
        
//        grid.setVertexDistances(grid.buildVertexDistanceMatrix());
//        List<Vertex<Point>> destinations = grid.getDestinations(selected, false);
//        List<Vertex<Point>> alternate = grid.getDestinations(selected, true);
//        
//        
//        long startTime = System.currentTimeMillis();
//        int n = 15;
//        List<Vertex<Point>>[] clustersArray = grid.cluster(alternate, n);
//        grid.printClusters(clustersArray);  
//        while(!grid.validateClusters(clustersArray)) 
//        { 
//            n += 5;
//            clustersArray = grid.cluster(alternate, n);  //need to check if this creates a cluster with more than 10 items
//            grid.printClusters(clustersArray);         //if so, go back and re-cluster with larger n value    
//        }
//        List<Vertex<Point>> finalRoute = grid.overallRouteBetter(clustersArray);
//        
//        
//        long endTime = System.currentTimeMillis();
//        System.out.println("\nTime: " + (endTime-startTime));
//        System.out.println(Arrays.toString(finalRoute.toArray()));
//        
//        
//        //test with one version
//        System.out.println(grid.getPermutations());
//        System.out.println("First Version");
//        long startTime = System.currentTimeMillis();
//        double t = grid.calcShortestPath(grid.getStartVertex(), grid.getEndVertex(), destinations, 0, Double.MAX_VALUE);
//        System.out.println(t);
//        System.out.print("First Winner: " + Arrays.toString(grid.getShortestPath().toArray()));
//        long endTime = System.currentTimeMillis();
//        System.out.println("\nTime: " + (endTime-startTime));
//        System.out.println(grid.getPermutations());
//        
//        
//        //test with second version
//        grid.resetPermutations();
//        System.out.println(grid.getPermutations());
//        System.out.println("\nSecond version");
//         startTime = System.currentTimeMillis();
//         t = grid.calcShortestPath(alternate,1,1,Double.MAX_VALUE);
//        System.out.println(t);
//        System.out.print("Second Winner: " + Arrays.toString(grid.getShortestPath().toArray()));
//         endTime = System.currentTimeMillis();
//        System.out.println("\nTime: " + (endTime-startTime));
//        System.out.println(grid.getPermutations());
//
//        grid.printGraph();
//        
//        Map<Vertex<Point>, Double> answer= AdjacencyMapGraph.shortestPathLengths(grid.graph, grid.startVertex);
//        for(Entry<Vertex<Point>,Double> e : answer.entrySet())
//        {
//            System.out.println("Key: " + e.getKey().getElement());
//            System.out.println("Value: " + e.getValue());
//        }
//        
//
//        grid.displayMatrix(grid.printVertexDistanceMatrix());
//        grid.saveMatrix(grid.printVertexDistanceMatrix());
//
//        selected.add(plan.getBin(26));
//        selected.add(plan.getBin(60));
//        selected.add(plan.getBin(55));
//        selected.add(plan.getBin(17));
//        selected.add(plan.getBin(30));
//        selected.add(plan.getBin(40));
//        selected.add(plan.getBin(5));
//        selected.add(plan.getBin(78));//20
//        selected.add(plan.getBin(11));
//        selected.add(plan.getBin(77));
//        selected.add(plan.getBin(19));
//        selected.add(plan.getBin(49));
//        selected.add(plan.getBin(10));
//        selected.add(plan.getBin(3));
//        selected.add(plan.getBin(71));
//        selected.add(plan.getBin(61));
//        selected.add(plan.getBin(51));
//        selected.add(plan.getBin(41)); //30
//        selected.add(plan.getBin(31));
//        selected.add(plan.getBin(21));
            
    }
}

