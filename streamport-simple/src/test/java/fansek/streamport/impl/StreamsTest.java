package fansek.streamport.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.LinkedList;

import org.junit.Test;

import fansek.streamport.Consumer;
import fansek.streamport.Stream;
import fansek.streamport.Traversable;
import fansek.streamport.support.Streams;

public class StreamsTest {

	@Test(expected = NullPointerException.class)
	public void testCreateWithNullTraversable() {
		Traversable<?> traversable = null;
		Streams.create(traversable);
	}

	@Test
	public void testCreateWithNonEmptyTraversable() {
		final LinkedList<Object> sourceList = new LinkedList<>();
		sourceList.add(1);
		sourceList.add(2);
		final LinkedList<Object> resultList = new LinkedList<>(sourceList);

		Stream<Object> stream = Streams.create(new Traversable<Object>() {
			@Override
			public void forEach(Consumer<? super Object> consumer) {
				for (Object element : sourceList) {
					consumer.accept(element);
				}
			}
		});

		stream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object object) {
				assertTrue(resultList.remove(object));
			}
		});
		assertThat(resultList.size(), equalTo(0));
	}

	@Test
	public void testCreateWithEmptyTraversable() {
		Stream<Object> stream = Streams.create(new Traversable<Object>() {
			@Override
			public void forEach(Consumer<? super Object> consumer) {
			}
		});

		stream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object object) {
				fail("Stream should be empty.");
			}
		});
	}

	@Test(expected = NullPointerException.class)
	public void testCreateWithNullIterable() {
		Iterable<?> iterable = null;
		Streams.create(iterable);
	}

	@Test
	public void testCreateWithNonEmptyIterable() {
		final LinkedList<Object> sourceList = new LinkedList<>();
		sourceList.add(1);
		sourceList.add(2);
		final LinkedList<Object> resultList = new LinkedList<>(sourceList);

		Stream<Object> stream = Streams.create(sourceList);

		stream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object object) {
				assertTrue(resultList.remove(object));
			}
		});
		assertThat(resultList.size(), equalTo(0));
	}

	@Test
	public void testCreateWithEmptyIterable() {
		Stream<Object> stream = Streams.create(new LinkedList<>());
		stream.forEach(new Consumer<Object>() {
			@Override
			public void accept(Object object) {
				fail("Stream should be empty.");
			}
		});
	}
}