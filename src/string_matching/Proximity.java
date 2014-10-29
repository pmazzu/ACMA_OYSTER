package edu.ualr.oyster.utilities.acma.string_matching;




/**
 * The <code>Proximity</code> interface provides a general method for
 * defining closeness between two objects.  Proximity is a similarity
 * measure, with two objects having higher proximity being more
 * similar to one another.  It provides a single method {@link
 * #proximity(Object,Object)} returning the proximity between two
 * objects.  The closer two objects are, the higher their proximity
 * value.
 *
 * <p>Proximity runs in the other direction from distance.  With
 * distance, the closer two objects are, the lower their distance
 * value.  Many classes implement both <code>Proximity</code> and
 * {@link Distance}, with one method defined in terms of the other.
 * For instance, negation converts a distance into a proximity.
 *
 * @author  Bob Carpenter
 * @version 3.0
 * @since   LingPipe3.0
 * @param <E> the type of objects between which proximity is defined
 */
public interface Proximity<E> {

    /**
     * Returns the distance between the specified pair of objects.
     *
     * @param e1 First object.
     * @param e2 Second object.
     * @return Proximity between the two objects.
     */
    public double proximity(E e1, E e2);

}
