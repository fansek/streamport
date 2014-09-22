package fansek.streamported;

public interface Traversable<T> {
	void forEach(Consumer<? super T> consumer);
}