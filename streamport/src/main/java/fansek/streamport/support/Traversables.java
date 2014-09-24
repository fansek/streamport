package fansek.streamport.support;

import java.util.LinkedList;
import java.util.Objects;

import fansek.streamport.Consumer;
import fansek.streamport.Stream;
import fansek.streamport.Traversable;
import fansek.streamport.impl.AbstractStream;

public class Traversables {

	public static <T> Traversable<T> fromIterable(final Iterable<T> iterable) {
		return new DefaultTraversable<>(iterable);
	}

	@SafeVarargs
	public static <T> Traversable<T> fromArray(final T... ts) {
		return new ArrayTraversable<>(ts);
	}

	public static <T> Iterable<T> toIterable(Traversable<T> traversable) {
		Objects.requireNonNull(traversable);
		DefaultIterableAccumulator<T> iterableAccumulator;
		iterableAccumulator = new DefaultIterableAccumulator<T>();
		traversable.forEach(iterableAccumulator);
		return iterableAccumulator.getIterable();
	}

	public static <T> Stream<T> toStream(Traversable<T> traversable) {
		Objects.requireNonNull(traversable);
		return new DefaultStream<>(traversable);
	}

	@SafeVarargs
	public static <T> Stream<T> toStream(T... ts) {
		Objects.requireNonNull(ts);
		return new DefaultStream<>(new ArrayTraversable<>(ts));
	}

	public static <T> Stream<T> toStream(final Iterable<T> iterable) {
		Objects.requireNonNull(iterable);
		Traversable<T> traversable = fromIterable(iterable);
		return toStream(traversable);
	}

	static class DefaultStream<T> extends AbstractStream<T> {
		private final Traversable<T> traversable;

		public DefaultStream(Traversable<T> traversable) {
			Objects.requireNonNull(traversable);
			this.traversable = traversable;
		}

		@Override
		public void forEach(Consumer<? super T> consumer) {
			traversable.forEach(consumer);
		}
	}

	static class DefaultTraversable<T> implements Traversable<T> {
		private final Iterable<T> iterable;

		public DefaultTraversable(Iterable<T> iterable) {
			Objects.requireNonNull(iterable);
			this.iterable = iterable;
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
			Objects.requireNonNull(ts);
			this.ts = ts;
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
