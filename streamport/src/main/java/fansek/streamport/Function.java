package fansek.streamport;

/**
 * Represents a function that accepts one argument and returns a result.
 * 
 * @param <T>
 *           the type of the input to the function
 * @param <R>
 *           the type of the result of the function
 */
public interface Function<T, R> {

	/**
	 * Applies this function to the given argument.
	 *
	 * @param t
	 *           the input argument
	 * @return the function result
	 */
	R apply(T t);
}