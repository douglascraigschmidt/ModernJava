package sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A subclass in the {@code Simple*Set} hierarchy that is implemented
 * by {@link HashSet}.
 */
@SuppressWarnings("SuspiciousMethodCalls")
public class SimpleHashSet<E>
       extends SimpleAbstractSet<E> {
    /**
     * Constructor initializes the {@link Set} to use
     * a {@link HashSet}.
     */
    public SimpleHashSet() {
        super(new HashSet<>());
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public Iterator<E> iterator() {
        System.out.println("SimpleHashSet.iterator()");
        return super.iterator();
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public boolean contains(Object o) {
        System.out.println("SimpleHashSet.contains()");
        return super.contains(o);
    }

    /**
     * This method decorates the corresponding superclass method.
     */
    @Override
    public boolean add(E e) {
        System.out.println("SimpleHashSet.add()");
        return super.add(e);
    }
}
	
