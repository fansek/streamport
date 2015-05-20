package fansek.streamport;

public interface Pipeline<S, T> extends Function<Traversable<S>, Traversable<T>> {

	Pipeline<S, T> filter(Predicate<? super T> predicate);

	<R> Pipeline<S, R> map(Function<? super T, ? extends R> mapper);

	<R> Pipeline<S, R> flatMap(Function<? super T, ? extends Traversable<? extends R>> mapper);

	<R> Pipeline<S, R> accumulate(Accumulator<? super T, ? extends R> accumulator);
}
