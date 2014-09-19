package fansek.streamported.impl;

import fansek.streamported.Consumer;
import fansek.streamported.Stream;

abstract class DownStream<T, R> extends AbstractStream<R> {
	final Stream<T> upStream;
	
	public DownStream(Stream<T> upStream) {
		this.upStream = upStream;
	}
	
	@Override
	public void forEach(Consumer<? super R> resultConsumer) {
		Consumer<? super T> upConsumer = createUpConsumer(resultConsumer);
		upStream.forEach(upConsumer);
	}
	
	abstract Consumer<? super T> createUpConsumer(Consumer<? super R> resultConsumer);
}