package fansek.streamport.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Test;

import fansek.streamport.Consumer;
import fansek.streamport.Traversable;
import fansek.streamport.support.Traversables;

public class TraversablesTest {

	@Test(expected = NullPointerException.class)
	public void testCreateFromIterableWithNull() {
		Iterable<?> iterable = null;
		Traversables.create(iterable);
	}

	@Test(expected = NullPointerException.class)
	public void testCreateFromArrayWithNull() {
		Object[] array = null;
		Traversables.create(array);
	}

	@Test
	public void testCreateWithNonEmptyIterable() {
		LinkedList<Object> sourceList = new LinkedList<>();
		sourceList.add(1);
		sourceList.add(2);
		final LinkedList<Object> resultList = new LinkedList<>(sourceList);

		Traversables.create(sourceList)
				.forEach(new Consumer<Object>() {
					@Override
					public void accept(Object object) {
						assertTrue(resultList.remove(object));
					}
				});
		assertThat(resultList.size(), equalTo(0));
	}

	@Test
	public void testCreateWithEmptyIterable() {
		Traversables.create(new LinkedList<>())
				.forEach(new Consumer<Object>() {
					@Override
					public void accept(Object object) {
						fail("Traversable should be empty.");
					}
				});
	}

	@Test(expected = NullPointerException.class)
	public void testToIterableWithNull() {
		Traversables.toIterable(null);
	}

	@Test
	public void testToIterableWithNonEmptyTraversable() {
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
	public void testToIterableWithEmptyTraversable() {
		Iterable<Object> iterable = Traversables.toIterable(new Traversable<Object>() {
			@Override
			public void forEach(Consumer<? super Object> consumer) {
			}
		});
		Iterator<Object> iterator = iterable.iterator();
		iterator.next();
	}
}