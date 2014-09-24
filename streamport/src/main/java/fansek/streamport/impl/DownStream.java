package fansek.streamport.impl;

import java.util.Objects;

import fansek.streamport.Consumer;
import fansek.streamport.Stream;

abstract class DownStream<T, R> extends AbstractStream<R> {
	final Stream<T> upStream;

	public DownStream(Stream<T> upStream) {
		Objects.requireNonNull(upStream);
		this.upStream = upStream;
	}

	@Override
	public void forEach(Consumer<? super R> resultConsumer) {
		Consumer<? super T> upConsumer = createUpConsumer(resultConsumer);
		upStream.forEach(upConsumer);
	}

	abstract Consumer<? super T> createUpConsumer(Consumer<? super R> resultConsumer);
}