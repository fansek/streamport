package fansek.streamport.impl;

import java.util.Objects;

import fansek.streamport.Accumulator;
import fansek.streamport.Consumer;
import fansek.streamport.Function;
import fansek.streamport.Pipeline;
import fansek.streamport.Predicate;
import fansek.streamport.Traversable;

public abstract class AbstractPipeline<S, T> implements Pipeline<S, T> {

	@Override
	public Pipeline<S, T> filter(final Predicate<? super T> predicate) {
		Objects.requireNonNull(predicate);
		return new AbstractPipeline<S, T>() {
			@Override
			public Traversable<T> apply(Traversable<S> source) {
				Objects.requireNonNull(source);
				final Traversable<T> inner = AbstractPipeline.this.apply(source);
				return new Traversable<T>() {
					@Override
					public void forEach(final Consumer<? super T> resultConsumer) {
						Objects.requireNonNull(resultConsumer);
						inner.forEach(new Consumer<T>() {
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
		};
	}

	@Override
	public <R> Pipeline<S, R> map(final Function<? super T, ? extends R> mapper) {
		Objects.requireNonNull(mapper);
		return new AbstractPipeline<S, R>() {
			@Override
			public Traversable<R> apply(Traversable<S> source) {
				Objects.requireNonNull(source);
				final Traversable<T> inner = AbstractPipeline.this.apply(source);
				return new Traversable<R>() {
					@Override
					public void forEach(final Consumer<? super R> resultConsumer) {
						Objects.requireNonNull(resultConsumer);
						inner.forEach(new Consumer<T>() {
							@Override
							public void accept(T t) {
								resultConsumer.accept(mapper.apply(t));
							}
						});
					}
				};
			}
		};
	}

	@Override
	public <R> Pipeline<S, R> flatMap(final Function<? super T, ? extends Traversable<? extends R>> mapper) {
		Objects.requireNonNull(mapper);
		return new AbstractPipeline<S, R>() {
			@Override
			public Traversable<R> apply(Traversable<S> source) {
				Objects.requireNonNull(source);
				final Traversable<T> inner = AbstractPipeline.this.apply(source);
				return new Traversable<R>() {
					@Override
					public void forEach(final Consumer<? super R> resultConsumer) {
						Objects.requireNonNull(resultConsumer);
						inner.forEach(new Consumer<T>() {
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
		};
	}

	@Override
	public <R> Pipeline<S, R> accumulate(final Accumulator<? super T, ? extends R> accumulator) {
		Objects.requireNonNull(accumulator);
		return new AbstractPipeline<S, R>() {
			@Override
			public Traversable<R> apply(Traversable<S> source) {
				Objects.requireNonNull(source);
				final Traversable<T> inner = AbstractPipeline.this.apply(source);
				return new Traversable<R>() {
					@Override
					public void forEach(final Consumer<? super R> resultConsumer) {
						Objects.requireNonNull(resultConsumer);
						inner.forEach(new Consumer<T>() {
							@Override
							public void accept(T t) {
								accumulator.accept(t, resultConsumer);
							}
						});
					}
				};
			}
		};
	}
}