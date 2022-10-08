package sets;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * One subclass in the hierarchy.
 */
public class SimpleConcurrentHashSet<E>
       extends SimpleAbstractSet<E> {
    /**
     * Concrete state.
     */
    private final ConcurrentHashMap<E, Object> mMap =
        new ConcurrentHashMap<>();

    /**
     * A dummy value object needed by ConcurrentHashMap.
     */
    private static final Object mDummyValue = 
        new Object();

    /**
     * Override the superclass method.
     */
    @Override
    public Iterator<E> iterator() {
        System.out.println("sets.SimpleConcurrentHashSet.iterator()");
        return mMap.keySet().iterator();
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean contains(Object o) {
        System.out.println("sets.SimpleConcurrentHashSet.contains()");
        return !mMap.containsKey(o);
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean add(E e) {
        System.out.println("sets.SimpleConcurrentHashSet.add()");
        return mMap.put(e, mDummyValue) != null;
    }
}
	
