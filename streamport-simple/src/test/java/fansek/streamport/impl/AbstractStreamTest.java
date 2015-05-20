package fansek.streamport.impl;

import static fansek.streamport.support.Predicates.TAUTOLOGY;
import static fansek.streamport.support.Predicates.equalsTo;
import static fansek.streamport.support.Predicates.not;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import fansek.streamport.Accumulator;
import fansek.streamport.Consumer;
import fansek.streamport.Function;
import fansek.streamport.Stream;
import fansek.streamport.Traversable;
import fansek.streamport.support.Streams;
import fansek.streamport.support.Traversables;

public class AbstractStreamTest {
	Stream<Integer> nonEmptyStream;
	Stream<Integer> emptyStream;

	@Before
	public void init() {
		nonEmptyStream = Streams.create(1, 2, 3);
		emptyStream = Streams.create();
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
				return Traversables.create(1, 2);
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

	@Test
	public void complexTestOnNonEmptyStream() {
		final StringBuilder sb = new StringBuilder();
		nonEmptyStream
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
				.forEach(new Consumer<String>() {
					@Override
					public void accept(String str) {
						sb.append(str);
					}
				});
		assertThat(sb.toString(), equalTo("t < 2t < 2t < 2"));
	}
}