package fansek.streamport.impl;

import java.util.Objects;

import fansek.streamport.Consumer;

abstract class UpConsumer<T, R> implements Consumer<T> {
	final Consumer<? super R> resultConsumer;

	UpConsumer(Consumer<? super R> resultConsumer) {
		this.resultConsumer = Objects.requireNonNull(resultConsumer);
	}
}