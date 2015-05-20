package fansek.streamport.support;

import fansek.streamport.Traversable;
import fansek.streamport.impl.AbstractPipeline;

public class Pipelines {
	public static <S> IdentityPipeline<S> create() {
		return new IdentityPipeline<>();
	}

	static class IdentityPipeline<T> extends AbstractPipeline<T, T> {
		@Override
		public Traversable<T> apply(Traversable<T> source) {
			return source;
		}
	}
}
