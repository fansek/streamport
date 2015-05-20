package fansek.streamport.impl;

import java.util.Objects;

import fansek.streamport.Accumulator;
import fansek.streamport.Consumer;
import fansek.streamport.Function;
import fansek.streamport.Predicate;
import fansek.streamport.Stream;
import fansek.streamport.Traversable;

public abstract class AbstractStream<T> implements Stream<T> {

	@Override
	public Stream<T> filter(final Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		return new AbstractStream<T>() {
			@Override
			public void forEach(final Consumer<? super T> resultConsumer) {
				Objects.requireNonNull(resultConsumer);
				AbstractStream.this.forEach(
						new Consumer<T>() {
							@Override
							public void accept(T t) {
								if (predicate.test(t)) {
									resultConsumer.accept(t);
								}
							}
						});
			}
		};
	}

	@Override
	public <R> Stream<R> map(final Function<? super T, ? extends R> mapper) {
		Objects.requireNonNull(mapper);
		return new AbstractStream<R>() {
			@Override
			public void forEach(final Consumer<? super R> resultConsumer) {
				Objects.requireNonNull(resultConsumer);
				AbstractStream.this.forEach(
						new Consumer<T>() {
							@Override
							public void accept(T t) {
								resultConsumer.accept(mapper.apply(t));
							}
						});
			}
		};
	}

	@Override
	public <R> Stream<R> flatMap(final Function<? super T, ? extends Traversable<? extends R>> mapper) {
		Objects.requireNonNull(mapper);
		return new AbstractStream<R>() {
			@Override
			public void forEach(final Consumer<? super R> resultConsumer) {
				Objects.requireNonNull(resultConsumer);
				AbstractStream.this.forEach(
						new Consumer<T>() {
							@Override
							public void accept(T t) {
								Traversable<? extends R> traversable = mapper.apply(t);
								if (traversable != null) {
									traversable.forEach(resultConsumer);
								}
							}
						});
			}
		};
	}

	@Override
	public <R> Stream<R> accumulate(final Accumulator<? super T, ? extends R> accumulator) {
		Objects.requireNonNull(accumulator);
		return new AbstractStream<R>() {
			@Override
			public void forEach(final Consumer<? super R> resultConsumer) {
				Objects.requireNonNull(resultConsumer);
				AbstractStream.this.forEach(
						new Consumer<T>() {
							@Override
							public void accept(T t) {
								accumulator.accept(t, resultConsumer);
							}
						});
			}
		};
	}
}