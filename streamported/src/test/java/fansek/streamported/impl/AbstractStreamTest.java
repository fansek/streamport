package fansek.streamported.impl;

import static fansek.streamported.impl.Predicates.TAUTOLOGY;
import static fansek.streamported.impl.Predicates.equalsTo;
import static fansek.streamported.impl.Predicates.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import fansek.streamported.Accumulator;
import fansek.streamported.Consumer;
import fansek.streamported.Function;
import fansek.streamported.Stream;
import fansek.streamported.Traversable;

public class AbstractStreamTest {
	Stream<Integer> nonEmptyStream;
	Stream<Integer> emptyStream;

	@Before
	public void init() {
		nonEmptyStream = Traversables.toStream(1, 2, 3);
		emptyStream = Traversables.toStream();
	}

	@Test(expected = NullPointerException.class)
	public void testFilterCallWithNull() {
		nonEmptyStream.filter(null);
	}

	@Test
	public void testFilterCallWithNonEmptyFilter() {
		Stream<?> resultStream = nonEmptyStream.filter(not(equalsTo(2)));
		final LinkedList<Object> resultList = new LinkedList<>();
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				if (new Integer(2).equals(t)) {
					fail("'2' should be filtered out.");
				}
				resultList.add(t);
			}
		});
		assertThat(resultList.size(), equalTo(2));
	}

	@Test
	public void testFilterCallWithEmptyFilter() {
		Stream<?> resultStream = nonEmptyStream.filter(TAUTOLOGY);
		final LinkedList<Object> resultList = new LinkedList<>();
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				resultList.add(t);
			}
		});
		assertThat(resultList.size(), equalTo(3));
	}

	@Test
	public void testFilterCallWithEmptyStream() {
		Stream<?> resultStream = emptyStream.filter(not(equalsTo(2)));
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				fail("result stream should be empty");
			}
		});
	}

	@Test(expected = NullPointerException.class)
	public void testMapCallWithNull() {
		nonEmptyStream.map(null);
	}

	@Test
	public void testMapCallWithNonEmptyStream() {
		Stream<?> resultStream = nonEmptyStream.map(new Function<Object, Object>() {
			@Override
			public Object apply(Object t) {
				return (Integer) t * 2;
			}
		});
		final LinkedList<Integer> resultList =
				new LinkedList<Integer>(Arrays.asList(2, 4, 6));
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				assertTrue(resultList.remove(t));
			}
		});
		assertThat(resultList.size(), equalTo(0));
	}

	@Test
	public void testMapCallWithEmptyStream() {
		Stream<?> resultStream = emptyStream.map(new Function<Object, Object>() {
			@Override
			public Object apply(Object t) {
				return t;
			}
		});
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				fail("result stream should be empty");
			}
		});
	}

	@Test(expected = NullPointerException.class)
	public void testFlatMapCallWithNull() {
		nonEmptyStream.flatMap(null);
	}

	@Test
	public void testFlatMapCallWithNonEmptyStream() {
		Stream<?> resultStream = nonEmptyStream.flatMap(new Function<Object, Traversable<Integer>>() {
			@Override
			public Traversable<Integer> apply(Object t) {
				return Traversables.fromArray(1, 2);
			}
		});
		final LinkedList<Integer> resultList =
				new LinkedList<Integer>(Arrays.asList(1, 2, 1, 2, 1, 2));
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				assertTrue(resultList.remove(t));
			}
		});
		assertThat(resultList.size(), equalTo(0));
	}

	@Test
	public void testFlatMapCallWithEmptyStream() {
		Stream<?> resultStream = emptyStream.map(new Function<Object, Object>() {
			@Override
			public Object apply(Object t) {
				return (Integer) t * 2;
			}
		});
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				fail("result stream should be empty");
			}
		});
	}

	@Test(expected = NullPointerException.class)
	public void testAccumulateCallWithNull() {
		nonEmptyStream.accumulate(null);
	}

	@Test
	public void testAccumulateCallWithNonEmptyStream() {
		Stream<?> resultStream = nonEmptyStream.accumulate(new Accumulator<Object, Object>() {
			@Override
			public void accept(Object t, Consumer<? super Object> resultConsumer) {
				if (!((Integer) 2).equals(t)) {
					resultConsumer.accept(0);
					resultConsumer.accept(0);
				}
			}
		});
		final LinkedList<Integer> resultList =
				new LinkedList<Integer>(Arrays.asList(0, 0, 0, 0));
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				assertTrue(resultList.remove(t));
			}
		});
		assertThat(resultList.size(), equalTo(0));
	}

	@Test
	public void testAccumulateCallWithEmptyStream() {
		Stream<?> resultStream = emptyStream.accumulate(new Accumulator<Object, Object>() {
			@Override
			public void accept(Object t, Consumer<? super Object> resultConsumer) {
				resultConsumer.accept(0);
			}
		});
		resultStream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object t) {
				fail("result stream should be empty");
			}
		});
	}

	@Test(expected = NullPointerException.class)
	public void testReduceCallWithNull() {
		nonEmptyStream.reduce(null);
	}

	@Test
	public void testReduceCallWithNonEmptyStream() {
		List<Integer> result = nonEmptyStream.reduce(new Function<Traversable<Integer>, List<Integer>>() {
			@Override
			public List<Integer> apply(Traversable<Integer> traversable) {
				final LinkedList<Integer> resultList = new LinkedList<>();
				traversable.forEach(new Consumer<Integer>() {
					@Override
					public void accept(Integer t) {
						resultList.add(t);
					}
				});
				return resultList;
			}
		});
		assertThat(result.size(), equalTo(3));
	}

	@Test
	public void complexTestOnNonEmptyStream() {
		String result = nonEmptyStream
				.filter(not(equalsTo(2)))
				.map(new Function<Integer, Traversable<Integer>>() {
					@Override
					public Traversable<Integer> apply(final Integer t) {
						return new Traversable<Integer>() {
							@Override
							public void forEach(Consumer<? super Integer> consumer) {
								consumer.accept(t);
								consumer.accept(t);
								consumer.accept(t);
							}
						};
					}
				})
				.flatMap(new Function<Traversable<Integer>, Traversable<Integer>>() {
					@Override
					public Traversable<Integer> apply(Traversable<Integer> t) {
						return t;
					}
				})
				.accumulate(new Accumulator<Integer, String>() {
					@Override
					public void accept(Integer t, Consumer<? super String> resultConsumer) {
						if (t < 2) {
							resultConsumer.accept("t < 2");
						}
					}
				})
				.reduce(new Function<Traversable<String>, String>() {
					@Override
					public String apply(Traversable<String> t) {
						final StringBuilder sb = new StringBuilder();
						t.forEach(new Consumer<String>() {
							@Override
							public void accept(String str) {
								sb.append(str);
							}
						});
						return sb.toString();
					}
				});
		assertThat(result, equalTo("t < 2t < 2t < 2"));
	}
}