package sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A subclass in the {@code Simple*Set} hierarchy that is implemented
 * by {@link TreeSet}.
 */
@SuppressWarnings("SuspiciousMethodCalls")
public class SimpleTreeSet<E extends Comparable<E>>
       extends SimpleAbstractSet<E> {
    /**
     * Constructor initializes the {@link Set} to use
     * a {@link TreeSet}.
     */
    public SimpleTreeSet() {
        super(new TreeSet<>());
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public Iterator<E> iterator() {
        System.out.println("SimpleTreeSet.iterator()");
        return super.iterator();
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public boolean contains(Object o) {
        System.out.println("SimpleTreeSet.contains()");
        return super.contains(o);
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public boolean add(E e) {
        System.out.println("SimpleTreeSet.add()");
        return super.add(e);
    }
}
	
