package fansek.streamported.impl;

import fansek.streamported.Consumer;
import fansek.streamported.Function;
import fansek.streamported.Stream;

class MapStream<T, R> extends DownStream<T, R> {
	final Function<? super T, ? extends R> mapper;

	MapStream(Stream<T> upStream, Function<? super T, ? extends R> mapper) {
		super(upStream);
		this.mapper = mapper;
	}

	@Override
	Consumer<? super T> createUpConsumer(Consumer<? super R> resultConsumer) {
		return new MappingConsumer(resultConsumer);
	}

	class MappingConsumer extends UpConsumer<T, R> {
		MappingConsumer(Consumer<? super R> resultConsumer) {
			super(resultConsumer);
		}

		@Override
		public void accept(T t) {
			resultConsumer.accept(mapper.apply(t));
		}
	}
}