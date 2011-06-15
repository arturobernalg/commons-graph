package org.apache.commons.graph.visit;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;

import org.apache.commons.graph.Edge;
import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;

/**
 * Contains different implementations of Graph visitor algorithms.
 */
public final class Visit
{

    /**
     * Breadth-first search algorithm implementation.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph instance has to be visited
     * @param source the root node the search begins from
     */
    public final <V extends Vertex, E extends Edge<V>> void breadthFirstSearch( Graph<V, E> graph, V source )
    {
        breadthFirstSearch( graph, source, null );
    }

    /**
     * Breadth-first search algorithm implementation.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph instance has to be visited
     * @param source the root node the search begins from
     * @param handler the handler intercepts visit actions
     */
    public final <V extends Vertex, E extends Edge<V>> void breadthFirstSearch( Graph<V, E> graph, V source,
                                                                                GraphVisitHandler<V, E> handler )
    {
        if ( graph == null )
        {
            throw new IllegalArgumentException( "Graph has to be visited can not be null." );
        }
        if ( source == null )
        {
            throw new IllegalArgumentException( "Root node the search begins from can not be null." );
        }

        if ( handler != null )
        {
            handler.discoverGraph( graph );
        }

        LinkedList<V> vertexQueue = new LinkedList<V>();
        vertexQueue.add( source );

        Set<V> visitedVetices = new HashSet<V>();
        visitedVetices.add( source );

        while ( !vertexQueue.isEmpty() )
        {
            V v = vertexQueue.remove();

            if ( handler != null )
            {
                handler.discoverVertex( v );
            }

            for ( E e : graph.getEdges( v ) )
            {
                V w = e.getTail();

                if ( !visitedVetices.add( w ) )
                {
                    if ( handler != null )
                    {
                        handler.discoverEdge( e );
                    }

                    vertexQueue.addFirst( v );

                    if ( handler != null )
                    {
                        handler.finishEdge( e );
                    }
                }
            }

            if ( handler != null )
            {
                handler.finishVertex( v );
            }
        }

        if ( handler != null )
        {
            handler.finishGraph( graph );
        }
    }

    /**
     * Depth-first search algorithm implementation.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph instance has to be visited
     * @param source the root node the search begins from
     */
    public final <V extends Vertex, E extends Edge<V>> void depthFirstSearch( Graph<V, E> graph, V source )
    {
        depthFirstSearch( graph, source, null );
    }

    /**
     * Depth-first search algorithm implementation.
     *
     * @param <V> the Graph vertices type
     * @param <E> the Graph edges type
     * @param graph the Graph instance has to be visited
     * @param source the root node the search begins from
     * @param handler the handler intercepts visit actions
     */
    public final <V extends Vertex, E extends Edge<V>> void depthFirstSearch( Graph<V, E> graph, V source,
                                                                              GraphVisitHandler<V, E> handler )
    {
        if ( graph == null )
        {
            throw new IllegalArgumentException( "Graph has to be visited can not be null." );
        }
        if ( source == null )
        {
            throw new IllegalArgumentException( "Root node the search begins from can not be null." );
        }

        if ( handler != null )
        {
            handler.discoverGraph( graph );
        }

        Stack<V> vertexStack = new Stack<V>();
        vertexStack.push( source );

        Set<V> visitedVetices = new HashSet<V>();
        visitedVetices.add( source );

        while ( !vertexStack.isEmpty() )
        {
            V v = vertexStack.pop();

            if ( handler != null )
            {
                handler.discoverVertex( v );
            }

            for ( E e : graph.getEdges( v ) )
            {
                V w = e.getTail();

                if ( !visitedVetices.add( w ) )
                {
                    if ( handler != null )
                    {
                        handler.discoverEdge( e );
                    }

                    vertexStack.push( w );

                    if ( handler != null )
                    {
                        handler.finishEdge( e );
                    }
                }
            }

            if ( handler != null )
            {
                handler.finishVertex( v );
            }
        }

        if ( handler != null )
        {
            handler.finishGraph( graph );
        }
    }

    /**
     * Hidden constructor, this class can't be instantiated
     */
    private Visit()
    {
        // do nothing
    }

}