package fansek.streamported.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import fansek.streamported.Consumer;
import fansek.streamported.Predicate;
import fansek.streamported.Stream;

public class AbstractStreamTest {
	AbstractStream<?> nonEmptyStream;
	AbstractStream<?> emptyStream;

	@Before
	public void init() {
		nonEmptyStream = new AbstractStream<Object>() {
			@Override
			public void forEach(Consumer<? super Object> consumer) {
				consumer.accept(1);
				consumer.accept(2);
				consumer.accept(3);
			}
		};
		emptyStream = new AbstractStream<Object>() {
			@Override
			public void forEach(Consumer<? super Object> consumer) {
			}
		};
	}

	@Test(expected = NullPointerException.class)
	public void testFilterCallWithNull() {
		nonEmptyStream.filter(null);
	}

	@Test
	public void testFilterCallWithNonEmptyFilter() {
		Stream<?> resultStream = nonEmptyStream.filter(new Predicate<Object>() {
			@Override
			public boolean test(Object t) {
				return !new Integer(2).equals(t);
			}
		});
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
		Stream<?> resultStream = nonEmptyStream.filter(new Predicate<Object>() {
			@Override
			public boolean test(Object t) {
				return true;
			}
		});
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
		Stream<?> resultStream = emptyStream.filter(new Predicate<Object>() {
			@Override
			public boolean test(Object t) {
				return !new Integer(2).equals(t);
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
	public void testMapCallWithNull() {
		nonEmptyStream.map(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFlatMapCallWithNull() {
		nonEmptyStream.flatMap(null);
	}

	@Test(expected = NullPointerException.class)
	public void testAccumulateCallWithNull() {
		nonEmptyStream.accumulate(null);
	}

	@Test(expected = NullPointerException.class)
	public void testReduceCallWithNull() {
		nonEmptyStream.reduce(null);
	}
}