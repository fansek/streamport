package fansek.streamported.impl;

import fansek.streamported.Consumer;
import fansek.streamported.Function;
import fansek.streamported.Stream;
import fansek.streamported.Traversable;

class FlatMapStream<T, R> extends DownStream<T, R> {
	final Function<? super T, ? extends Traversable<? extends R>> mapper;

	FlatMapStream(Stream<T> upStream, Function<? super T, ? extends Traversable<? extends R>> mapper) {
		super(upStream);
		this.mapper = mapper;
	}

	@Override
	Consumer<? super T> createUpConsumer(Consumer<? super R> resultConsumer) {
		return new FlatMappingConsumer(resultConsumer);
	}

	class FlatMappingConsumer extends UpConsumer<T, R> {
		FlatMappingConsumer(Consumer<? super R> resultConsumer) {
			super(resultConsumer);
		}

		@Override
		public void accept(T t) {
			mapper.apply(t).forEach(resultConsumer);
		}
	}
}