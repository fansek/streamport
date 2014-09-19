package fansek.streamported.impl;

import java.util.Objects;

import fansek.streamported.Accumulator;
import fansek.streamported.Consumer;
import fansek.streamported.Function;
import fansek.streamported.Predicate;
import fansek.streamported.Stream;
import fansek.streamported.Traversable;

public abstract class AbstractStream<T> implements Stream<T> {
	@Override
	public abstract void forEach(Consumer<? super T> consumer);

	@Override
	public Stream<T> filter(final Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		return new FilterStream<T>(this, predicate);
	}

	@Override
	public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
		Objects.requireNonNull(mapper);
		return new MapStream<T, R>(this, mapper);
	}

	@Override
	public <R> Stream<R> flatMap(Function<? super T, ? extends Traversable<? extends R>> mapper) {
		Objects.requireNonNull(mapper);
		return new FlatMapStream<>(this, mapper);
	}

	@Override
	public <R> Stream<R> accumulate(Accumulator<? super T, ? extends R> accumulator) {
		Objects.requireNonNull(accumulator);
		return new AccumulatorStream<>(this, accumulator);
	}

	@Override
	public <G> G reduce(Function<Traversable<T>, G> reduction) {
		Objects.requireNonNull(reduction);
		return reduction.apply(this);
	}
}