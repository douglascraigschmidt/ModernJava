package sets;

import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A subclass in the {@code Simple*Set} hierarchy that is implemented
 * by {@link ConcurrentHashMap.KeySetView}.
 */
@SuppressWarnings("SuspiciousMethodCalls")
public class SimpleConcurrentHashSet<E>
       extends SimpleAbstractSet<E> {
    /**
     * Constructor initializes the {@link Set} to use
     * a {@link ConcurrentHashMap.KeySetView}.
     */
    public SimpleConcurrentHashSet() {
        super(ConcurrentHashMap.newKeySet());
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public Iterator<E> iterator() {
        System.out.println("SimpleConcurrentHashSet.iterator()");
        return super.iterator();
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public boolean contains(Object o) {
        System.out.println("SimpleConcurrentHashSet.contains()");
        return super.contains(o);
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public boolean add(E e) {
        System.out.println("SimpleConcurrentHashSet.add()");
        return super.add(e);
    }
}
	
