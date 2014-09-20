package fansek.streamported.impl;

import java.util.Objects;

import fansek.streamported.Predicate;

public class Predicates {
	public static final Predicate<Object> TAUTOLOGY = new Predicate<Object>() {
		@Override
		public boolean test(Object t) {
			return true;
		}
	};

	public static Predicate<Object> equalsTo(Object testObject) {
		return new EqualityPredicate(testObject);
	}

	public static Predicate<Object> not(Predicate<Object> predicate) {
		return new NegationPredicate(predicate);
	}

	static class EqualityPredicate implements Predicate<Object> {
		private final Object testObject;

		public EqualityPredicate(Object testObject) {
			this.testObject = testObject;
		}

		@Override
		public boolean test(Object t) {
			return Objects.equals(testObject, t);
		}
	}

	static class NegationPredicate implements Predicate<Object> {
		private final Predicate<Object> predicate;

		public NegationPredicate(Predicate<Object> predicate) {
			this.predicate = predicate;
		}

		@Override
		public boolean test(Object t) {
			return !predicate.test(t);
		}
	}
}