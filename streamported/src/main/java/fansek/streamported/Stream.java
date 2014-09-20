package fansek.streamported;

public interface Stream<T> extends Traversable<T> {
	Stream<T> filter(Predicate<? super T> predicate);

	<R> Stream<R> map(Function<? super T, ? extends R> mapper);

	<R> Stream<R> flatMap(Function<? super T, ? extends Traversable<? extends R>> mapper);

	<R> Stream<R> accumulate(Accumulator<? super T, ? extends R> accumulator);

	<G> G reduce(Function<Traversable<T>, G> reduction);
}