package fansek.streamport;

public interface Consumer<T> {
	void accept(T t);
}