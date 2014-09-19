package fansek.streamported.impl;

import java.util.LinkedList;
import java.util.Objects;

import fansek.streamported.Consumer;
import fansek.streamported.Stream;
import fansek.streamported.Traversable;

public class Traversables {

	public static <T> Traversable<T> fromIterable(final Iterable<T> iterable) {
		return new DefaultTraversable<>(iterable);
	}

	public static <T> Iterable<T> toIterable(final Traversable<T> traversable) {
		Objects.requireNonNull(traversable);
		DefaultIterableAccumulator<T> iterableAccumulator;
		iterableAccumulator = new DefaultIterableAccumulator<T>();
		traversable.forEach(iterableAccumulator);
		return iterableAccumulator.getIterable();
	}

	public static <T> Stream<T> toStream(final Traversable<T> traversable) {
		Objects.requireNonNull(traversable);
		return new DefaultStream<>(traversable);
	}

	public static <T> Stream<T> toStream(final Iterable<T> iterable) {
		Objects.requireNonNull(iterable);
		Traversable<T> traversable = fromIterable(iterable);
		return toStream(traversable);
	}

	static class DefaultStream<T> extends AbstractStream<T> {
		final Traversable<T> traversable;

		public DefaultStream(Traversable<T> traversable) {
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

	static class DefaultIterableAccumulator<T> implements Consumer<T> {
		private final LinkedList<T> iterable = new LinkedList<>();

		@Override
		public void accept(T t) {
			iterable.add(t);
		}

		public LinkedList<T> getIterable() {
			return iterable;
		}
	}
}
