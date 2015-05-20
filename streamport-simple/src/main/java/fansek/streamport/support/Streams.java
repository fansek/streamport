package fansek.streamport.support;

import java.util.Objects;

import fansek.streamport.Consumer;
import fansek.streamport.Stream;
import fansek.streamport.Traversable;
import fansek.streamport.impl.AbstractStream;
import fansek.streamport.support.Traversables.ArrayTraversable;

public class Streams {

	public static <T> Stream<T> create(Traversable<T> traversable) {
		return new DefaultStream<>(traversable);
	}

	@SafeVarargs
	public static <T> Stream<T> create(T... ts) {
		return new DefaultStream<>(new ArrayTraversable<>(ts));
	}

	public static <T> Stream<T> create(final Iterable<T> iterable) {
		Traversable<T> traversable = Traversables.create(iterable);
		return create(traversable);
	}

	static class DefaultStream<T> extends AbstractStream<T> {
		private final Traversable<T> traversable;

		public DefaultStream(Traversable<T> traversable) {
			this.traversable = Objects.requireNonNull(traversable);
		}

		@Override
		public void forEach(Consumer<? super T> consumer) {
			traversable.forEach(consumer);
		}
	}
}
