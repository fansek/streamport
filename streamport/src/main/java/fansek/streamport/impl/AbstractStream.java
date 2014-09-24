package fansek.streamport.impl;

import fansek.streamport.Accumulator;
import fansek.streamport.Consumer;
import fansek.streamport.Function;
import fansek.streamport.Predicate;
import fansek.streamport.Stream;
import fansek.streamport.Traversable;

public abstract class AbstractStream<T> implements Stream<T> {
	@Override
	public abstract void forEach(Consumer<? super T> consumer);

	@Override
	public Stream<T> filter(final Predicate<? super T> predicate) {
		return new FilterStream<>(this, predicate);
	}

	@Override
	public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
		return new MapStream<>(this, mapper);
	}

	@Override
	public <R> Stream<R> flatMap(Function<? super T, ? extends Traversable<? extends R>> mapper) {
		return new FlatMapStream<>(this, mapper);
	}

	@Override
	public <R> Stream<R> accumulate(Accumulator<? super T, ? extends R> accumulator) {
		return new AccumulatorStream<>(this, accumulator);
	}
}