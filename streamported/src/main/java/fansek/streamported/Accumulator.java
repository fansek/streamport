package fansek.streamported;

public interface Accumulator<T, R> {
	public void accept(T t, Consumer<? super R> resultConsumer);
}