package fansek.streamported.impl;

import java.util.Objects;

import fansek.streamported.Accumulator;
import fansek.streamported.Consumer;
import fansek.streamported.Function;
import fansek.streamported.Predicate;
import fansek.streamported.Stream;
import fansek.streamported.Traversable;

public class DefaultStream<T> implements Stream<T> {
	private final Traversable<T> traversable;

	public DefaultStream(Traversable<T> traversable) {
		Objects.requireNonNull(traversable);
		this.traversable = traversable;
	}

	@Override
	public void forEach(Consumer<? super T> consumer) {
		traversable.forEach(consumer);
	}

	@Override
	public Stream<T> filter(Predicate<? super T> predicate) {
		return null;
	}

	@Override
	public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
		return null;
	}

	@Override
	public <R> Stream<R> flatMap(Function<? super T, ? extends Traversable<? extends R>> mapper) {
		return null;
	}

	@Override
	public <R> Stream<R> accumulate(Accumulator<? super T, ? extends R> accumulator) {
		return null;
	}

	@Override
	public <G> G reduce(Function<Traversable<T>, G> reduction) {
		return null;
	}
}