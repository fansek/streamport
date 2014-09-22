package fansek.streamport;

public interface Function<T, R> {
	R apply(T t);
}