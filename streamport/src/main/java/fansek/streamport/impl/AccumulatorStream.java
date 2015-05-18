package fansek.streamport.impl;

import java.util.Objects;

import fansek.streamport.Accumulator;
import fansek.streamport.Consumer;
import fansek.streamport.Stream;

class AccumulatorStream<T, R> extends DownStream<T, R> {
	final Accumulator<? super T, ? extends R> accumulator;

	AccumulatorStream(Stream<T> upStream, Accumulator<? super T, ? extends R> accumulator) {
		super(upStream);
		this.accumulator = Objects.requireNonNull(accumulator);
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