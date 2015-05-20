package fansek.streamport.support;

import java.util.LinkedList;
import java.util.Objects;

import fansek.streamport.Consumer;
import fansek.streamport.Traversable;

public class Traversables {

	public static <T> Traversable<T> create(final Iterable<T> iterable) {
		return new DefaultTraversable<>(iterable);
	}

	@SafeVarargs
	public static <T> Traversable<T> create(final T... ts) {
		return new ArrayTraversable<>(ts);
	}

	public static <T> Iterable<T> toIterable(Traversable<T> traversable) {
		Objects.requireNonNull(traversable);
		DefaultIterableAccumulator<T> iterableAccumulator;
		iterableAccumulator = new DefaultIterableAccumulator<T>();
		traversable.forEach(iterableAccumulator);
		return iterableAccumulator.getIterable();
	}

	static class DefaultTraversable<T> implements Traversable<T> {
		private final Iterable<T> iterable;

		public DefaultTraversable(Iterable<T> iterable) {
			this.iterable = Objects.requireNonNull(iterable);
		}

		@Override
		public void forEach(Consumer<? super T> consumer) {
			for (T t : iterable) {
				consumer.accept(t);
			}
		}
	}

	static class ArrayTraversable<T> implements Traversable<T> {
		private final T[] ts;

		@SafeVarargs
		public ArrayTraversable(T... ts) {
			this.ts = Objects.requireNonNull(ts);
		}

		@Override
		public void forEach(Consumer<? super T> consumer) {
			for (T t : ts) {
				consumer.accept(t);
			}
		}
	}

	static class DefaultIterableAccumulator<T> implements Consumer<T> {
		private final LinkedList<T> iterable = new LinkedList<>();

		@Override
		public void accept(T t) {
			iterable.add(t);
		}

		public Iterable<T> getIterable() {
			return iterable;
		}
	}
}
