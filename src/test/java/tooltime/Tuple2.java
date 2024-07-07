package tooltime;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Simple tuple object for utility
 *
 * @param <A> first value
 * @param <B> second value
 */
@Setter
@Getter
public class Tuple2<A, B> implements Serializable {

    private A a;
    private B b;

    public Tuple2() {
    }

    public Tuple2(final A a, final B b) {
        super();
        this.a = a;
        this.b = b;
    }

    public static <A, B> Tuple2<A, B> of(final A a, final B b) {
        return new Tuple2<>(a, b);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((a == null) ? 0 : a.hashCode());
        result = (prime * result) + ((b == null) ? 0 : b.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Tuple2<?, ?> other)) {
            return false;
        }
        if (a == null) {
            if (other.a != null) {
                return false;
            }
        } else if (!a.equals(other.a)) {
            return false;
        }
        if (b == null) {
            return other.b == null;
        } else return b.equals(other.b);
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Tuple2 [" + (a != null ? "a=" + a + ", " : "") + (b != null ? "b=" + b : "") + "]";
    }

    public int size() {
        boolean isanull = null == a;
        boolean isbnull = null == b;
        if (isanull && isbnull) {
            return 0;
        }
        if (!isanull && !isbnull) {
            return 2;
        }
        return 1;
    }
}

