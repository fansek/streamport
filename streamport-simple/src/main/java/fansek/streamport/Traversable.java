package fansek.streamport;

/**
 * Represents a set of elements that are to be traversed by provided consumer
 * when applied.
 * 
 * (Similar to Java 8 {@code Iterable<T>}.)
 *
 * @param <T>
 *           the type of the input to the predicate
 */
public interface Traversable<T> {

	/**
	 * Performs the given action for each element of the {@code Traversable}
	 * until all elements have been processed or the action throws an exception.
	 *
	 * @param action
	 *           The action to be performed for each element
	 */
	void forEach(Consumer<? super T> consumer);
}