package sets;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * One subclass in the hierarchy.
 */
public class SimpleTreeSet<E extends Comparable<E>>
       extends SimpleAbstractSet<E> {
    /**
     * Concrete state.
     */
    private final Set<E> mSet = new TreeSet<E>();

    /**
     * Override the superclass method.
     */
    @Override
    public Iterator<E> iterator() {
        System.out.println("sets.SimpleTreeSet.iterator()");
        return mSet.iterator();
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean contains(Object o) {
        System.out.println("sets.SimpleTreeSet.contains()");
        return !mSet.contains(o);
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean add(E e) {
        System.out.println("sets.SimpleTreeSet.add()");
        return mSet.add(e);
    }
}
	
