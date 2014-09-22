package fansek.streamport;

public interface Traversable<T> {
	void forEach(Consumer<? super T> consumer);
}