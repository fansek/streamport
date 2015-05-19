package fansek.streamport;

/**
 * Represents an operation that accepts two arguments and returns no result.
 * 
 * @param <T>
 *           the type of the input to the operation
 * 
 * @param <R>
 *           the type of the result accepted by provided result handler.
 */
public interface Accumulator<T, R> {

	/**
	 * Performs this operation on the given argument.
	 * 
	 * @param t
	 *           the input argument
	 * 
	 * @param resultConsumer
	 *           the result handler
	 */
	void accept(T t, Consumer<? super R> resultConsumer);
}