package sets;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A subclass in the Set hierarchy that is implemented
 * by {@link ConcurrentHashMap.KeySetView}.
 */
@SuppressWarnings("SuspiciousMethodCalls")
public class SimpleConcurrentHashSet<E>
       extends SimpleAbstractSet<E> {
    /**
     * Concrete state uses a {@link ConcurrentHashMap.KeySetView}.
     */
    private final ConcurrentHashMap.KeySetView<E, Boolean> mSet =
        ConcurrentHashMap.newKeySet();

    /**
     * Override the superclass method.
     */
    @Override
    public Iterator<E> iterator() {
        System.out.println("SimpleConcurrentHashSet.iterator()");
        return mSet.iterator();
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean contains(Object o) {
        System.out.println("SimpleConcurrentHashSet.contains()");
        return mSet.contains(o);
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean add(E e) {
        System.out.println("SimpleConcurrentHashSet.add()");
        return mSet.add(e);
    }
}
	
