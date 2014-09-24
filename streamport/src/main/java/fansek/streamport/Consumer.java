package fansek.streamport;

/**
 * Represents an operation that accepts one argument and returns no result.
 * 
 * @param <T>
 *           the type of the input to the operation
 */
public interface Consumer<T> {

	/**
	 * Performs this operation on the given argument.
	 * 
	 * @param t
	 *           the input argument
	 */
	void accept(T t);
}