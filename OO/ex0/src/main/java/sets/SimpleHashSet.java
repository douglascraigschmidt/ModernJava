package sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A subclass in the Set hierarchy that is implemented
 * by {@link HashSet}. */
@SuppressWarnings("SuspiciousMethodCalls")
public class SimpleHashSet<E>
       extends SimpleAbstractSet<E> {
    /**
     * Concrete state uses {@link HashSet}.
     */
    private final Set<E> mSet = new HashSet<>();

    /**
     * Override the superclass method.
     */
    @Override
    public Iterator<E> iterator() {
        System.out.println("SimpleHashSet.iterator()");
        return mSet.iterator();
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean contains(Object o) {
        System.out.println("SimpleHashSet.contains()");
        return mSet.contains(o);
    }

    /**
     * Override the superclass method.
     */
    @Override
    public boolean add(E e) {
        System.out.println("SimpleHashSet.add()");
        return mSet.add(e);
    }
}
	
