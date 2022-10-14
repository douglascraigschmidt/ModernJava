package sets;

import java.util.Iterator;
import java.util.Set;

/**
 * Super class for the {@code Simple*Set} hierarchy, which define
 * collections that contain no duplicate items.
 */
public abstract class SimpleAbstractSet<E>
       implements Iterable<E> {
    /**
     * Stores a reference to the concrete {@link Set} subclass.
     */
    protected final Set<E> mSet;

    /**
     * Constructor assigns the concrete {@link Set} subclass.
     *
     * @param set The concrete {@link Set} subclass
     */
    SimpleAbstractSet(Set<E> set) {
        mSet = set;
    }

    /**
     * Returns an {@link Iterator} over the elements in this collection.
     *
     * @return An {@link Iterator} over the elements in this collection
     */
    @Override
    public Iterator<E> iterator() {
        return mSet.iterator();
    }

    /**
     * Returns <tt>true</tt> if this collection contains the
     * specified element.
     *
     * @param o Element whose presence in this collection is tested
     * @return <tt>true</tt> if this collection contains the specified
     *         element
     */
    public boolean contains(Object o) {
        return mSet.contains(o);
    }

    /**
     * Adds the specified element to this set.
     *
     * @param e Element to be added
     * @return {@code true} if this set changed as a result of the call
     */
    public boolean add(E e) {
        return mSet.add(e);
    }
}
	
