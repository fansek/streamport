package fansek.streamport.impl;

import java.util.Objects;

import fansek.streamport.Consumer;
import fansek.streamport.Predicate;
import fansek.streamport.Stream;

class FilterStream<T> extends DownStream<T, T> {
	final Predicate<? super T> predicate;

	FilterStream(Stream<T> upStream, Predicate<? super T> predicate) {
		super(upStream);
		Objects.requireNonNull(predicate);
		this.predicate = predicate;
	}

	@Override
	Consumer<? super T> createUpConsumer(Consumer<? super T> resultConsumer) {
		return new FilteringConsumer(resultConsumer);
	}

	class FilteringConsumer extends UpConsumer<T, T> {
		FilteringConsumer(Consumer<? super T> resultConsumer) {
			super(resultConsumer);
		}

		@Override
		public void accept(T t) {
			if (predicate.test(t)) {
				resultConsumer.accept(t);
			}
		}
	}
}