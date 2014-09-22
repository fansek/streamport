package fansek.streamported;

public interface Consumer<T> {
	void accept(T t);
}