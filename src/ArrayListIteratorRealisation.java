import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public final class ArrayListIteratorRealisation<T> implements List<T> {

    private T[] array = (T[]) new Object[1];

    private int size = 0;

    @Override
    public final int size() {
        return this.size;
    }

    @Override
    public final boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public final boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public final Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public final T[] toArray() {
        final T[] newM = (T[]) new Object[this.size()];
        System.arraycopy(array, 0, newM, 0, this.size());
        return newM;
    }

    @Override
    public final <T1> T1[] toArray(final T1[] a) {
        if (a.length < size) {
            return (T1[]) Arrays.copyOf(array, size, a.getClass());
        }
        System.arraycopy(array, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public final boolean add(final T t) {
        if (array.length == size) {
            final T[] oldM = array;
            array = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldM, 0, array, 0, oldM.length);
        }
        array[size++] = t;
        return true;
    }

    @Override
    public final void add(final int index, final T element) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size  == 0 || index == size) {
            add(element);
        } else if (array.length == size) {
            final T[] tempM = array;
            array = (T[]) new Object[this.size() * 2];

            System.arraycopy(tempM, 0, array, 0,  index);
            System.arraycopy(tempM, index, array, index + 1, size() - index);

            set(index, element);
            size++;
        } else {
            final T[] tempM = array;
            System.arraycopy(tempM, 0, array, 0, index + 1);
            System.arraycopy(tempM, index, array, index + 1, size() - index);
            set(index, element);
            size++;
        }
    }

    @Override
    public final boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public final boolean remove(final Object o) {
        for (int i = 0; i < size(); i++) {
            if (array[i].equals(o)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public final T remove(final int index) {
        final T element = array[index];
        if (index != this.size() - 1) {
            System.arraycopy(array, index + 1, array, index, this.size() - index - 1);
        }
        size--;
        return element;
    }

    @Override
    public final boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public final boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public final boolean retainAll(final Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) {
                this.remove(item);
            }
        }
        return true;
    }

    @Override
    public final void clear() {
        array = (T[]) new Object[1];
        size = 0;
    }

    @Override
    public final List<T> subList(final int start, final int end) {
        return null;
    }

    @Override
    public final ListIterator<T> listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public final ListIterator<T> listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @Override
    public final int lastIndexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final int indexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final T set(final int index, final T element) {
        array[index] = element;
        return element;
    }

    @Override
    public final T get(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return array[index];
    }

    private class ElementsIterator implements ListIterator<T> {

        private static final int LAST_IS_NOT_SET = -1;
        private int index;
        private int lastIndex = LAST_IS_NOT_SET;

        ElementsIterator() {
            this(0);
        }

        ElementsIterator(final int index) {
            this.index = index;
        }

        @Override
        public boolean hasNext() {
            return ArrayListIteratorRealisation.this.size() > index;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            lastIndex = index++; // or lastIndex = nextIndex(); index++;
            return ArrayListIteratorRealisation.this.array[lastIndex];
        }

        @Override
        public int nextIndex() {
            // BEGIN (write your solution here)   nextIndex
            if (!hasNext()) {
                return ArrayListIteratorRealisation.this.size();
            }
            return index;
            // END
        }

        @Override
        public boolean hasPrevious() {
            // BEGIN (write your solution here)   hasPrevious
            return index > 0;
            // END
        }

        @Override
        public T previous() {
            // BEGIN (write your solution here)   previous
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            lastIndex = --index;
            return ArrayListIteratorRealisation.this.array[lastIndex];
            // END
        }

        @Override
        public int previousIndex() {
            // BEGIN (write your solution here)  previousIndex
            if (!hasPrevious()) {
                return -1;
            }
            return index - 1;
            // END
        }

        @Override
        // . a ^ b . c . d . e . f .
        public void add(final T element) {
            // BEGIN (write your solution here)  add
            ArrayListIteratorRealisation.this.add(index, element);
            index++;
            lastIndex = LAST_IS_NOT_SET;
            // END
        }

        @Override
        public void set(final T element) {
            // BEGIN (write your solution here)  set
            if (lastIndex == LAST_IS_NOT_SET) {
                throw new IllegalStateException();
            }
            ArrayListIteratorRealisation.this.array[lastIndex] = element;
            lastIndex = LAST_IS_NOT_SET;
            // END
        }

        @Override
        public void remove() {
            secretRemove();
        }

        private void secretRemove() {
            if (lastIndex == LAST_IS_NOT_SET) {
                throw new IllegalStateException();
            }
            ArrayListIteratorRealisation.this.remove(lastIndex);
            index--;
            lastIndex = LAST_IS_NOT_SET;
        }
    }
}
