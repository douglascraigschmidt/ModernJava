package sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * One subclass in the hierarchy.
 */
public class SimpleHashSet<E>
       extends SimpleAbstractSet<E> {
    /**
     * Concrete state.
     */
    private final Set<E> mSet = new HashSet<>();

    /**
     * Override the superclass method.
     */
    @Override
    public Iterator<E> iterator() {
        System.out.println("sets.SimpleHashSet.iterator()");
        return mSet.iterator();
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean contains(Object o) {
        System.out.println("sets.SimpleHashSet.contains()");
        return !mSet.contains(o);
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean add(E e) {
        System.out.println("sets.SimpleHashSet.add()");
        return mSet.add(e);
    }
}
	
