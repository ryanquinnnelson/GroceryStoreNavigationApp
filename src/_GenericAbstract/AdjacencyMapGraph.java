/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _GenericAbstract;


import _Interfaces.AdaptablePriorityQueue;
import _Interfaces.Edge;
import _Interfaces.Entry;
import _Interfaces.Graph;
import _Interfaces.Map;
import _Interfaces.Position;
import _Interfaces.PositionalList;
import _Interfaces.Vertex;
import java.util.HashSet;
import java.util.Set;

/**
 * Implementation of a graph that maintains, for each vertex, a separate container of all edges incident to the vertex.
 * Secondary container is organized as a map, with adjacent vertex serving as a key.
 * @author Ryan
 * @param <V>
 * @param <E>
 */
public class AdjacencyMapGraph<V,E> implements Graph<V,E>
{
    
    
    //-------------- InnerVertex<V> class ----------------//
    /**
     * Vertex of an adjacency map graph representation.
     * @param <V> 
     */
    private class InnerVertex<V> implements Vertex<V>
    {
        private V element;
        private Position<Vertex<V>> position;
        private Map<Vertex<V>, Edge<E>> outgoing;
        private Map<Vertex<V>,Edge<E>> incoming;
        private int ID;
        
        /**
         * Constructs new InnerVertex object storing given element.
         * @param element
         * @param graphIsDirected 
         */
        public InnerVertex(int name, V element, boolean graphIsDirected)
        {
            this.ID = name;
            this.element = element;
            outgoing = new ProbeHashMap<>();
            
            if(graphIsDirected)
            {
                incoming = new ProbeHashMap<>();
            }
            else
            {
                incoming = outgoing;
            }
        }
        
        /**
         * Returns the element associated with the vertex.
         * @return 
         */
        @Override
        public V getElement()
        {
            return element;
        }
        
        /**
         * Returns the position of this vertex within the graph's vertex list.
         * @return 
         */
        public Position<Vertex<V>> getPosition()
        {
            return position;
        }
        
        /**
         * Returns reference to the underlying map of outgoing edges.
         * @return 
         */
        public Map<Vertex<V>,Edge<E>> getOutgoing()
        {
            return outgoing;
        }
        
        /**
         * Returns reference to the underlying map of incoming edges.
         * @return 
         */
        public Map<Vertex<V>,Edge<E>> getIncoming()
        {
            return incoming;
        }
        
        /**
         * Returns reference to the given ID of vertex.
         * @return 
         */
        public int getID()
        {
            return ID;
        }
        
        /**
         * Stores the position of this vertex within the graph's vertex list.
         * @param p 
         */
        public void setPosition(Position<Vertex<V>> p )
        {
            position = p;
        }  
        
        /**
         * Validates that this vertex instance belongs to the given graph.
         * @param graph
         * @return 
         */
        public boolean validate(Graph<V,E> graph)
        {
            return(AdjacencyMapGraph.this == graph && position != null);
        }
        
        
        public boolean equals(Object o)
        {
            if(!(o instanceof InnerVertex))
            {
                return false;
            }
            else
            {
                InnerVertex<V> other = (InnerVertex<V>) o;
                return this.getElement().equals(other.element);
            }
        }
        
        public String toString()
        {
            return ID + ": " + element.toString();
        }
    } //-------------- end of InnerVertex<V> class ----------------//
    
    //-------------- InnerEdge<E> class ----------------//
    /**
     * An edge between two vertices.
     * @param <E> 
     */
    private class InnerEdge<E> implements Edge<E>
    {
        private E element;
        private Position<Edge<E>> position;
        private Vertex<V>[] endpoints;
        
        /**
         * Constructs InnerEdge object from vertex u to vertex v, which stores given element.
         * @param u
         * @param v
         * @param element 
         */
        public InnerEdge(Vertex<V> u, Vertex<V> v, E element)
        {
            this.element = element;
            endpoints = (Vertex<V>[]) new Vertex[]{u,v};    //array of length 2
        }
        
        /**
         * Returns the element associated with the edge.
         * @return 
         */
        @Override
        public E getElement()
        {
            return element;
        }
        
        /**
         * Returns the reference to the endpoint array.
         * @return 
         */
        public Vertex<V>[] getEndpoints()
        {
            return endpoints;
        }
        
        /**
         * Returns the position of this edge within the graph's vertex list.
         * @return 
         */
        public Position<Edge<E>> getPosition()
        {
            return position;
        }
        
        /**
         * Stores the position of this edge within the graph's vertex list.
         * @param p 
         */
        public void setPosition(Position<Edge<E>> p)
        {
            position = p;
        }
        
        /**
         * Validates that this edge instance belongs to the given graph.
         * @param graph
         * @return 
         */
        public boolean validate(Graph<V,E> graph)
        {
            return AdjacencyMapGraph.this == graph && position != null;
        }
    } //-------------- end of InnerEdge<E> class ----------------//
    
    private boolean isDirected;
    private PositionalList<Vertex<V>> vertices = new LinkedPositionalList<>();
    private PositionalList<Edge<E>> edges = new LinkedPositionalList<>();
    private int currentID = 0;
    
    /**
     * Constructs an empty graph that is directed or undirected.
     * @param isDirected 
     */
    public AdjacencyMapGraph(boolean isDirected)
    {
        this.isDirected = isDirected;
    }
    
    
    //private utilties
    private InnerVertex<V> validate(Vertex<V> v)
    {
        if(!(v instanceof InnerVertex))
        {
            throw new IllegalArgumentException("Invalid vertex");
        }
        InnerVertex<V> vert = (InnerVertex<V>) v;
        
        if(!vert.validate(this))
        {
            throw new IllegalArgumentException("Invalid vertex");
        }
        return vert;
    }
    
    private InnerEdge<E> validate(Edge<E> e)
    {
        if(!(e instanceof InnerEdge))
        {
            throw new IllegalArgumentException("Invalid edge");
        }
        InnerEdge<E> edge = (InnerEdge<E>) e;
        if(!edge.validate(this))
        {
            throw new IllegalArgumentException("Invalid edge");
        }
        return edge;
    }
    
    

    @Override
    public int numVertices()
    {
        return vertices.size();
    }
    
    @Override
    public Iterable<Vertex<V>> vertices()
    {
        return vertices;
    }
    
    @Override
    public int numEdges()
    {
        return edges.size();
    }
    
    @Override
    public Iterable<Edge<E>> edges()
    {
        return edges;
    }
    
    @Override
    public int outDegree(Vertex<V> v) throws IllegalArgumentException
    {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().size();
    }
    
    
    @Override
    public Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException
    {
        InnerVertex<V> vert = validate(v);
        return vert.getOutgoing().values(); //edges are the values in the adjacency map (vertices are keys)
    }
    
    @Override
    public int inDegree(Vertex<V> v) throws IllegalArgumentException
    {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().size();
    }
    
    @Override
    public Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException
    {
        InnerVertex<V> vert = validate(v);
        return vert.getIncoming().values(); //edges are the values in the adjacency map (vertices are keys)
    }
    
    @Override
    public Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException
    {
        InnerVertex<V> origin = validate(u);
        return origin.getOutgoing().get(v); //null if no edge from u to v
    }
    
    @Override
    public Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException
    {
        InnerEdge<E> edge = validate(e);
        return edge.getEndpoints();
    }
    
    
    @Override
    public Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException
    {
        InnerEdge<E> edge = validate(e);
        Vertex<V>[] endpoints = edge.getEndpoints();
        if(endpoints[0] == v)
        {
            return endpoints[1];
        }
        else if(endpoints[1] == v)
        {
            return endpoints[0];
        }
        else
        {
            throw new IllegalArgumentException("v is not incident to this edge");
        }
    }
    
    
    @Override
    public Vertex<V> insertVertex(V element)
    {
        InnerVertex<V> v = new InnerVertex<>(currentID++, element, isDirected);
        v.setPosition(vertices.addLast(v));
        return v;
    }
    
    
    @Override
    public Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element)  throws IllegalArgumentException
    {
        if(getEdge(u,v) == null) //edge from u to v doesn't exist
        {
            InnerEdge<E> e = new InnerEdge(u, v, element);
            
            e.setPosition(edges.addLast(e));
            InnerVertex<V> origin = validate(u);
            InnerVertex<V> destination = validate(v);
            
            origin.getOutgoing().put(v, e);
            destination.getIncoming().put(u, e);
            
            return e;
        }
        else
        {
            throw  new IllegalArgumentException("Edge from u to v exists.");
        }
    }
    
    
    @Override
    public void removeVertex(Vertex<V> v)  throws IllegalArgumentException
    {
        InnerVertex<V> vert = validate(v);
        
        for(Edge<E> e : vert.getOutgoing().values()) //remove all incident edges from graph
        {
            removeEdge(e);
        }
        
        for(Edge<E> e : vert.getIncoming().values())
        {
            removeEdge(e);
        }
        
        vertices.remove(vert.getPosition());
    }
    
    @Override
    public void removeEdge(Edge<E> e) throws IllegalArgumentException
    {
        InnerEdge<E> edge = validate(e);
        
        InnerVertex<V>[] verts = (InnerVertex<V>[]) edge.getEndpoints();
        
        verts[0].getOutgoing().remove(verts[1]); //remove this edge from vertices adjacencies
        verts[1].getIncoming().remove(verts[0]);
        
        edges.remove(edge.getPosition()); //removes this edge from list of edges
        edge.setPosition(null); //invalidates this edge
    }
    
    /**
     * Borrowed from textbook. Diagnostics only.
     * @return 
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        //     sb.append("Edges:");
        //     for (Edge<E> e : edges) {
        //       Vertex<V>[] verts = endVertices(e);
        //       sb.append(String.format(" (%s->%s, %s)", verts[0].getElement(), verts[1].getElement(), e.getElement()));
        //     }
        //     sb.append("\n");
        for (Vertex<V> v : vertices) 
        {
            sb.append("Vertex " + v.getElement() + "\n");

            if (isDirected)
            {
                sb.append(" [outgoing]");
            }

            sb.append(" " + outDegree(v) + " adjacencies:");


            for (Edge<E> e: outgoingEdges(v))
            {
                sb.append(String.format(" {%s, %s}", opposite(v,e).getElement(), "Length: " + e.getElement()));
            }

            
            sb.append("\n"); //middle
        
            
            if (isDirected) 
            {
                sb.append(" [incoming]");
            }
                
            sb.append(" " + inDegree(v) + " adjacencies:");
        
            for (Edge<E> e: incomingEdges(v))
            {
                sb.append(String.format(" {%s, %s}", opposite(v,e).getElement(), "Length: " + e.getElement())); 
            }
            sb.append("\n");
            
        }
        return sb.toString();
    }
    
    /**
     * Performs depth-first search of Graph g starting at Vertex u.
     * @param <V>
     * @param <E>
     * @param g
     * @param u
     * @param known contains vertices that have already been visited
     * @param forest associates with vertex v the edge e of the graph that is used to discover v (if any)
     */
    public static <V,E> void DFS(Graph<V,E> g, Vertex<V> u, Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest)
    {
        known.add(u);   //u has been discovered
        
        for(Edge<E> e : g.outgoingEdges(u)) //for every outgoing edge from u
        {
            Vertex<V> v = g.opposite(u, e);
            
            if(!known.contains(v))
            {
                forest.put(v, e);       //e is the tree edge that discovered v
                DFS(g,v, known, forest); //recursively explore from v
            }
        }
    }
    
    /**
     * Returns an ordered list of edges comprising the directed path from u to v.
     * @param <V>
     * @param <E>
     * @param g
     * @param u
     * @param v
     * @param forest
     * @return 
     */
    public static <V,E> PositionalList<Edge<E>> constructPath(Graph<V,E> g, Vertex<V> u, Vertex<V> v, Map<Vertex<V>, Edge<E>> forest)
    {
       PositionalList<Edge<E>> path = new LinkedPositionalList<>();
       if(forest.get(v) != null)    //v was discovered during the search
       {
           Vertex<V> current = v;   //construct the path from back to front
           while(current != u)
           {
               Edge<E> edge = forest.get(current);
               path.addFirst(edge);                 //add edge to 'front' of path
               current = g.opposite(current, edge); //repeat with the opposite endpoint
           }
       }
       return path;
    }
    
    /**
     * Performs DFS for the entire graph and returns the DFS forest as a map.
     * @param <V>
     * @param <E>
     * @param g
     * @return 
     */
    public static <V,E> Map<Vertex<V>, Edge<E>> DFSComplete(Graph<V,E> g)
    {
        Set<Vertex<V>> known = new HashSet<>();
        Map<Vertex<V>,Edge<E>> forest = new ProbeHashMap<>();
        
        for(Vertex<V> u : g.vertices())
        {
            if(!known.contains(u))
            {
                DFS(g, u, known, forest);
            }
        }
        return forest;
    }
    
    /**
     * Performs breadth-first search of Graph g starting at Vertex u.
     * @param <V>
     * @param <E>
     * @param g
     * @param s
     * @param known
     * @param forest 
     */
    public static <V,E> void BFS(Graph<V,E> g, Vertex<V> s, Set<Vertex<V>> known, Map<Vertex<V>, Edge<E>> forest)
    {
        PositionalList<Vertex<V>> level = new LinkedPositionalList<>();
        known.add(s);
        level.addLast(s);   //first level only includes s
        
        while(!level.isEmpty())
        {
            PositionalList<Vertex<V>> nextLevel = new LinkedPositionalList<>();
            for(Vertex<V> u : level)
            {
                for(Edge<E> e : g.outgoingEdges(u))
                {
                    Vertex<V> v = g.opposite(u, e);
                    
                    if(!known.contains(v))
                    {
                        known.add(v);
                        forest.put(v, e);           //e is the tree edge that discovered v
                        nextLevel.addLast(v);       //v will be further considered in next pass
                    }
                }
            }
            level = nextLevel;  //relabel 'next' level to become the current
        }
    }
    
    /**
     * Implementation of Floyd-Warshall algorithm for computing transitive closure of g.
     * @param <V>
     * @param <E>
     * @param g 
     */
    public static <V,E> void transitiveClosure(Graph <V,E> g)
    {
        for(Vertex<V> k : g.vertices())
        {
            for(Vertex<V> i : g.vertices())
            {
                //verify that edge (i,k) exists in the partial closure
                if(i != k && g.getEdge(i,k) != null)
                {
                    for(Vertex<V> j : g.vertices())
                    {
                        //vertify that edge (k,j) exists in the partial closure
                        if(i != j && j != k && g.getEdge(k,j) != null)
                        {
                            //if (i,j) not yet included, add it to the closure
                            if(g.getEdge(i, j) == null)
                            {
                                g.insertEdge(i, j, null);
                            }
                        }
                    }
                }
            }
        }
    }
    
//    /**
//     * Returns a list of vertices of directed acyclic graph g in topological order.
//     * @param <V>
//     * @param <E>
//     * @param g
//     * @return 
//     */
//    public static <V,E> PositionalList<Vertex<V>> topologicalSort(Graph <V,E> g)
//    {
//        PositionalList<Vertex<V>> topo = new LinkedPositionalList<>(); //list of verties in topological order
//        Stack<Vertex<V>> ready = new LinkedStack<>();   //container of vertices that have no remaining constraints
//        Map<Vertex<V>, Integer> inCount = new ProbeHashMap<>(); //keep track of remaining in-degree for each vertex
//        
//        for(Vertex<V> u : g.vertices())
//        {
//            inCount.put(u, g.inDegree(u));  //initialize with actual in-degree
//            if(inCount.get(u) == 0)         //u has no incoming edges
//            {
//                ready.push(u);              //u is free of constraints
//            }
//        }
//        
//        while(!ready.isEmpty())
//        {
//            Vertex<V> u = ready.pop();
//            topo.addLast(u);
//            
//            for(Edge<E> e : g.outgoingEdges(u))     //considering all outgoing neighbors of u
//            {
//                Vertex<V> v = g.opposite(u,e);
//                inCount.put(v, inCount.get(v)-1);   //v has one less constraint on it
//                
//                if(inCount.get(v) == 0)
//                {
//                    ready.push(v);
//                }
//            }
//        }
//        return topo;
//    }
    
    /**
     * Computes the shortest path distances from search vertex to all reachable vertices of g. 
     * @param <V>
     * @param g
     * @param search
     * @return 
     */
    public static <V> Map<Vertex<V>, Double> shortestPathLengths(Graph<V,Double> g, Vertex<V> search)
    {
        //d.get(v) is upper bound on distance from search to v
        Map<Vertex<V>, Double> d = new ProbeHashMap<>(); 
        
        //map reachable v to its d value
        Map<Vertex<V>, Double> cloud = new ProbeHashMap<>();
        
        //pQueue has vertices as elements with d.get(v) as key
        AdaptablePriorityQueue<Double,Vertex<V>> pQueue = new HeapAdaptablePriorityQueue<>(); 
        
        //maps from vertex to its pQueue locator
        Map<Vertex<V>,Entry<Double,Vertex<V>>> pQueueTokens = new ProbeHashMap<>(); 
        
        
        //for each vertex v of the graph, add an entry to the priority queue, with 
        //the source having distance 0 and all others having infinite distance
        for(Vertex<V> v : g.vertices())
        {
            if(v == search)
            {
                d.put(v,0.0);
            }
            else
            {
                d.put(v, Double.MAX_VALUE);
            }
            pQueueTokens.put(v, pQueue.insert(d.get(v), v));    //save entry for future updates
        }
        
        //now begin adding reachable vertices to the cloud
        while(!pQueue.isEmpty())
        {
            Entry<Double,Vertex<V>> entry = pQueue.removeMin();
            double key = entry.getKey();
            Vertex<V> u = entry.getValue();
            cloud.put(u, key);      //this is the actual distance to u
            
            pQueueTokens.remove(u);     //u is no longer in pQueue
            
            for(Edge<Double> e : g.outgoingEdges(u))
            {
                Vertex<V> v = g.opposite(u, e);
                if(cloud.get(v) == null)
                {
                    //perform relaxation step on edge (u,v)
                    double weight = e.getElement();
                    if(d.get(u) + weight < d.get(v))    //better path to v?
                    {
                        d.put(v, d.get(u)+weight);  //update the distance
                        pQueue.replaceKey(pQueueTokens.get(v), d.get(v));   //update the pQueue entry
                    }
                }
            }
        }
        return cloud;   //includes only reachable vertices
    }
    
    /**
     * Reconstructs a shortest-path tree rooted at vertex s, given distance map d. 
     * Tree represented as a map from each reachable vertex v (other than s) to the edge
     * e = (u,v) that is used to reach v from its parent u in the tree.
     * @param <V>
     * @param g
     * @param s
     * @param d
     * @return 
     */
    public static <V> Map<Vertex<V>, Edge<Double>> spTree(Graph<V,Double> g, Vertex<V> s, Map<Vertex<V>, Double> d)
    {
        Map<Vertex<V>, Edge<Double>> tree = new ProbeHashMap<>();
        
        for(Vertex<V> v : d.keySet())
        {
            if( v != s)
            {
                for(Edge<Double> e : g.incomingEdges(v))
                {
                    Vertex<V> u = g.opposite(v, e);
                    double weight = e.getElement();
                    if(d.get(v) == d.get(u) + weight)
                    {
                        tree.put(v,e);  //edge is used to reach v
                    }
                }
            }
        }
        return tree;
    }
}
