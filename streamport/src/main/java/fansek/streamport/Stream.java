package fansek.streamport;

/**
 * A sequence of elements supporting aggregate operations.
 *
 * @param <T>
 *           the type of the stream elements
 */
public interface Stream<T> extends Traversable<T> {

	/**
	 * Returns a stream consisting of the elements of this stream that match the
	 * given predicate.
	 *
	 * @param predicate
	 *           a predicate to apply to each element to determine if it should
	 *           be included
	 * @return the new stream
	 */
	Stream<T> filter(Predicate<? super T> predicate);

	/**
	 * Returns a stream consisting of the results of applying the given function
	 * to the elements of this stream.
	 *
	 * @param <R>
	 *           The element type of the new stream
	 * @param mapper
	 *           a function to apply to each element
	 * @return the new stream
	 */
	<R> Stream<R> map(Function<? super T, ? extends R> mapper);

	/**
	 * Returns a stream consisting of the results of replacing each element of
	 * this stream with the contents of a mapped traversable produced by applying
	 * the provided mapping function to each element.
	 *
	 * @param <R>
	 *           The element type of the new stream
	 * @param mapper
	 *           a function to apply to each element which produces a traversable
	 *           of new values
	 * @return the new stream
	 */
	<R> Stream<R> flatMap(Function<? super T, ? extends Traversable<? extends R>> mapper);

	/**
	 * Returns a stream consisting of the elements that are passed to result
	 * consumer within accept operation of the provided accumulator.
	 *
	 * @param <R>
	 *           The element type of the new stream
	 * @param accumulator
	 *           an operation to allow exposing result consumer of this stream to
	 *           itself for each element of this stream.
	 * @return the new stream
	 */
	<R> Stream<R> accumulate(Accumulator<? super T, ? extends R> accumulator);
}