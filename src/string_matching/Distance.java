package edu.ualr.oyster.utilities.acma.string_matching;



/**
 * The <code>Distance</code> interface provides a general method for
 * defining distances between two objects.  Distance is a kind of
 * dissimilarity measure, because the larger the distance between two
 * objects, the less similar they are.  The distance interface
 * provides a single method {@link #distance(Object,Object)} returning
 * the distance between objects.
 *
 * <p>A proper distance is said to form a metric if it satisfies the
 * following four properties:
 *
 * <ul>
 * <li> Positive:  <code>distance(x,y) &gt;= 0</code>
 * <li> Self Distance Zero:  <code>distance(x,x) = 0</code>
 * <li> Symmetric: <code>distance(x,y) = distance(y,x)</code>
 * <li> Triangle Inequaltiy: <code>distance(x,y) + distance(y,z) &gt;= distance(x,z)</code>
 * </ul>
 *
 * <p>For example, the Euclidean distance between vectors is
 * a proper metric.
 *
 * <blockquote><pre>
 * distance(x,y) = sqrt(<big>&Sigma;</big><sub><sub>i</sub></sub> (x[i] * y[i])<sup>2</sup>)</pre></blockquote>
 *
 * as is the Manhattan metric, or taxicab distance:
 *
 * <blockquote><pre>
 * distance(x,y) = <big>&Sigma;</big><sub><sub>i</sub></sub> abs(x[i] - y[i])</pre></blockquote>
 *
 * Cosine is also popular for vectors:
 *
 * <blockquote><pre>
 * distance(x,y) = dotProduct(x,y) / (length(x) * length(y))</pre></blockquote>
 *
 * <p>A good introduction to distance may be found at:
 *
 * <ul>
 * <li><a href="http://en.wikipedia.org/wiki/Distance">Wikipedia: Distance</a></li>
 * </ul>
 *
 * @author  Bob Carpenter
 * @version 3.0
 * @since   LingPipe3.0
 * @param <E> the type of objects over which distances are defined
 */
public interface Distance<E> {

    /**
     * Returns the distance between the specified pair of objects.
     *
     * @param e1 First object.
     * @param e2 Second object.
     * @return Distance between the two objects.
     */
    public double distance(E e1, E e2);

}
