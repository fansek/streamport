package fansek.streamported.impl;

import fansek.streamported.Accumulator;
import fansek.streamported.Consumer;
import fansek.streamported.Stream;

class AccumulatorStream<T, R> extends DownStream<T, R> {
	final Accumulator<? super T, ? extends R> accumulator;

	AccumulatorStream(Stream<T> upStream, Accumulator<? super T, ? extends R> accumulator) {
		super(upStream);
		this.accumulator = accumulator;
	}

	@Override
	Consumer<? super T> createUpConsumer(Consumer<? super R> resultConsumer) {
		return new AccumulatingConsumer(resultConsumer);
	}

	class AccumulatingConsumer extends UpConsumer<T, R> {
		AccumulatingConsumer(Consumer<? super R> resultConsumer) {
			super(resultConsumer);
		}

		@Override
		public void accept(T t) {
			accumulator.accept(t, resultConsumer);
		}
	}
}