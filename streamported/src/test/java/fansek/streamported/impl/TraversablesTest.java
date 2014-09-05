package fansek.streamported.impl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Test;

import fansek.streamported.Consumer;
import fansek.streamported.Traversable;

public class TraversablesTest {

	@Test(expected = NullPointerException.class)
	public void testFromIterableCallWithNull() {
		Traversables.fromIterable(null);
	}

	@Test
	public void testFromIterableCallWithNonEmptyIterable() {
		LinkedList<Object> sourceList = new LinkedList<>();
		sourceList.add(1);
		sourceList.add(2);

		final LinkedList<Object> resultList = new LinkedList<>(sourceList);

		Traversables.fromIterable(sourceList)
				.forEach(new Consumer<Object>() {
					@Override
					public void accept(Object object) {
						assertTrue(resultList.remove(object));
					}
				});
		assertThat(resultList.size(), equalTo(0));
	}

	@Test
	public void testFromIterableCallWithEmptyIterable() {
		Traversables.fromIterable(new LinkedList<>())
				.forEach(new Consumer<Object>() {
					@Override
					public void accept(Object object) {
						fail("Traversable should be empty.");
					}
				});
	}

	@Test(expected = NullPointerException.class)
	public void testToIterableCallWithNull() {
		Traversables.toIterable(null);
	}

	@Test
	public void testToIterableCallWithNonEmptyTraversable() {
		Iterable<Object> iterable = Traversables.toIterable(new Traversable<Object>() {
			@Override
			public void forEach(Consumer<? super Object> consumer) {
				consumer.accept(1);
				consumer.accept(2);
			}
		});
		Iterator<Object> iterator = iterable.iterator();
		assertTrue(iterator.hasNext());
		assertThat(iterator.next(), equalTo((Object) 1));
		assertTrue(iterator.hasNext());
		assertThat(iterator.next(), equalTo((Object) 2));
		assertFalse(iterator.hasNext());
	}

	@Test(expected = NoSuchElementException.class)
	public void testToIterableCallWithEmptyTraversable() {
		Iterable<Object> iterable = Traversables.toIterable(new Traversable<Object>() {
			@Override
			public void forEach(Consumer<? super Object> consumer) {
			}
		});
		Iterator<Object> iterator = iterable.iterator();
		iterator.next();
	}
}