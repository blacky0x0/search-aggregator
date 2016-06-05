package com.blacky.sa.strategy;

import com.blacky.sa.model.SearchResult;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Every SERP is placed one after another.
 * And the returned list has "Round Robin" behaviour, so iterating over it could be potentially infinitely.
 */
public class RoundRobinAggregationStrategy implements AggregationStrategy {

    @Override
    public List<SearchResult> aggregate(List<List<SearchResult>> serps) {
        if (Objects.isNull(serps) || serps.isEmpty()) return Collections.emptyList();

        List<SearchResult> result = new RoundRobinArrayList<>();

        for (List<SearchResult> serp : serps) {
            result.addAll(serp.stream().collect(Collectors.toList()));
        }

        return result;
    }

    static class RoundRobinArrayList<E> extends ArrayList<E> {

        @Override
        public Iterator<E> iterator() {
            return new RoundRobinItr();
        }

        @Override
        public ListIterator<E> listIterator() {
            throw new NotImplementedException();
        }

        @Override
        public ListIterator<E> listIterator(int index) {
            throw new NotImplementedException();
        }

        private class RoundRobinItr implements Iterator<E> {
            int cursor;       // index of next element to return
            int lastRet = -1; // index of last element returned; -1 if no such
            int expectedModCount = modCount;

            public boolean hasNext() {
                return true;    // for a round robin algorithm
            }

            public E next() {
                checkForComodification();
                int i = cursor;
                if (i >= size())
                    i = 0; // start from the beginning
                cursor = i + 1;
                return get(lastRet = i);
            }

            public void remove() {
                throw new NotImplementedException();
            }

            @Override
            public void forEachRemaining(Consumer<? super E> consumer) {
                throw new NotImplementedException();
            }

            final void checkForComodification() {
                if (modCount != expectedModCount)
                    throw new ConcurrentModificationException();
            }
        }
    }

}
