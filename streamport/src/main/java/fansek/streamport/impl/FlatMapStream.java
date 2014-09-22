package fansek.streamport.impl;

import fansek.streamport.Consumer;
import fansek.streamport.Function;
import fansek.streamport.Stream;
import fansek.streamport.Traversable;

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