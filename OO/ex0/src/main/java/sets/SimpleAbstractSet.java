package sets;

import java.util.Iterator;

/**
 * Superclass for the Set hierarchy, which is a collect that
 * contains no duplicate items.
 */
public abstract class SimpleAbstractSet<E>
       implements Iterable<E> {
    /**
     * Returns an {@link Iterator} over the elements in this collection.
     * There are no guarantees concerning the order in which the
     * elements are returned (unless this collection is an
     * instance of some class that provides a guarantee).
     *
     * @return an {@link Iterator} over the elements in this collection
     *
     * This abstract method must be overridden by concrete subclasses.
     */
    public abstract Iterator<E> iterator();

    /**
     * Returns <tt>true</tt> if this collection contains the
     * specified element.  More formally, returns <tt>true</tt> if
     * and only if this collection contains at least one element
     * <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o Element whose presence in this collection is tested
     * @return <tt>true</tt> if this collection contains the specified
     *         element
     *
     * This abstract method must be overridden by concrete
     * subclasses.
     */
    public abstract boolean contains(Object o);

    /**
     * Adds the specified element to this set.
     *
     * @param e Element to be added
     * @return {@code true} if this set changed as a result of the call
     *
     * This abstract method must be overridden by concrete
     * subclasses.
     */
    public abstract boolean add(E e);
}
	
