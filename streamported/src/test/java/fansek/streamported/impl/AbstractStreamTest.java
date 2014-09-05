package fansek.streamported.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import fansek.streamported.Consumer;
import fansek.streamported.Predicate;
import fansek.streamported.Stream;

public class DefaultStreamTest {
	DefaultStream<?> nonEmptyStream;
	DefaultStream<?> emptyStream;

	@Before
	public void init() {
//		nonEmptyStream = new DefaultStream<>();
//		emptyStream = new DefaultStream<>();
	}

	@Test(expected = NullPointerException.class)
	public void testForEachCallWithNonEmpty() {
		nonEmptyStream.forEach(null);
	}

	@Test(expected = NullPointerException.class)
	public void testForEachCallWithNull() {
		nonEmptyStream.forEach(null);
	}

	@Test(expected = NullPointerException.class)
	public void testFilterCallWithNull() {
		nonEmptyStream.filter(null);
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