/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _Interfaces;

import java.util.Iterator;

/**
 * Interface for a graph structure, made of collection of vertices and edges.
 * @author Ryan
 * @param <V> generic type of vertices to be implemented
 * @param <E> generic type of edges to be implemented
 */
public interface Graph<V, E>
{
    /**
     * Returns the number of vertices in the graph.
     * @return 
     */
    int numVertices();
    
    /**
     * Returns the number of edges in the graph.
     * @return 
     */
    int numEdges();
    
    /**
     * Returns an iteration of all vertices in the graph.
     * @return 
     */
    Iterable<Vertex<V>> vertices();
    
    
    /**
     * Returns an iteration of all edges in the graph.
     * @return 
     */
    Iterable<Edge<E>> edges();
    
    
    
    /**
     * Returns the number of outgoing edges from given vertex.
     * @param v
     * @return 
     */
    int outDegree(Vertex<V> v) throws IllegalArgumentException;
    
    /**
     * Returns the number of incoming edges to given vertex.
     * @param v
     * @return 
     */
    int inDegree(Vertex<V> v) throws IllegalArgumentException;
    
    /**
     * Returns an iteration of all outgoing edges from given vertex.
     * For an undirected graph, this returns the same collection as does incomingEdges().
     * @param v
     * @return 
     */
    Iterable<Edge<E>> outgoingEdges(Vertex<V> v) throws IllegalArgumentException;
    
    /**
     * Returns an iteration of all incoming edges to given vertex.
     * For an undirected graph, this returns the same collection as does outgoingEdges().
     * @param v
     * @return 
     */
    Iterable<Edge<E>> incomingEdges(Vertex<V> v) throws IllegalArgumentException;    
    
    
    /**
     * Returns the edge from u to v, if one exists; returns null otherwise.
     * For an undirected graph, there is no difference between getEdge(u,v) and getEdge(v,u).
     * @param u
     * @param v
     * @return
     * @throws IllegalArgumentException 
     */
    Edge<E> getEdge(Vertex<V> u, Vertex<V> v) throws IllegalArgumentException;
    
    /**
     * Returns an array containing the two endpoint vertices of given edge.
     * If graph is directed, first vertex is the origin and second is the destination.
     * @param e
     * @return 
     */
    Vertex<V>[] endVertices(Edge<E> e) throws IllegalArgumentException;
    
    /**
     * For edge e incident to vertex v, returns the other vertex of the edge.
     * @param v
     * @param e
     * @return 
     * @throws IllegalArgumentException if e is not incident to v.
     */
    Vertex<V> opposite(Vertex<V> v, Edge<E> e) throws IllegalArgumentException;
    
    
    /**
     * Constructs and returns a new Vertex storing element x.
     * @param element
     * @return 
     */
    Vertex<V> insertVertex(V element);
    
    /**
     * Constructs and returns a new Edge storing element x.
     * @param u
     * @param v
     * @param element
     * @return 
     */
    Edge<E> insertEdge(Vertex<V> u, Vertex<V> v, E element) throws IllegalArgumentException;
    
    
    //update methods
    /**
     * Removes given v and all its incident edges from the graph.
     * @param v 
     */
    void removeVertex(Vertex<V> v) throws IllegalArgumentException;
    
    /**
     * Removes given edge from the graph.
     * @param e 
     */
    void removeEdge(Edge<E> e) throws IllegalArgumentException;
    
    
    //additional methods
    
    
    

    
  
    
   
    
    
    
}
