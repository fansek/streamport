package fansek.streamported.impl;

import fansek.streamported.Consumer;

abstract class UpConsumer<T, R> implements Consumer<T> {
	final Consumer<? super R> resultConsumer;
	
	UpConsumer(Consumer<? super R> resultConsumer) {
		this.resultConsumer = resultConsumer;
	}
}