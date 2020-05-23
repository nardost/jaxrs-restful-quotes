package edu.depaul.ntessema.jaxrs.data.repository;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import static edu.depaul.ntessema.jaxrs.data.repository.DB.QUOTES;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

/**
 * This class simulates the persistent layer.
 * @author nardos
 */
public class SimpleQuotesRepository implements Repository<Quote, Integer> {

    /*
     * Quotes are stored in a map structure where the keys
     * are the ids and the values are the quotes.
     *
     * Why this particular map implementation is chosen:
     *
     * (1) A scalable concurrent ConcurrentNavigableMap implementation.
     * (2) Sorted according to the natural ordering of its keys.
     * (3) Expected average log(n) time cost for the containsKey, get, put and remove operations.
     * (4) Insertion, removal, update, and access operations safely execute concurrently by multiple threads.
     *
     * https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ConcurrentSkipListMap.html
     */
    private final Map<Integer, Quote> quotes = new ConcurrentSkipListMap<>();

    private static SimpleQuotesRepository INSTANCE = null;

    private SimpleQuotesRepository() {

        /*
         * Create a quote object for each quote,
         * assign generated id for the object and
         * save it in the chosen data structure.
         */
        Stream.of(QUOTES).forEach(q -> {
            Quote quote = new Quote(q);
            /*
             * Id numbering begins from 1.
             * id = 1, 2, 3, 4, ...
             */
            final int id = 1 + quotes.size();
            quote.setId(id);
            quotes.put(id, quote);
        });
    }

    public static SimpleQuotesRepository getInstance() {
        if(INSTANCE == null) {
            synchronized (SimpleQuotesRepository.class) {
                if(INSTANCE == null) {
                    INSTANCE = new SimpleQuotesRepository();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Find a quote by its id.
     * @param id - cannot be null
     * @return quote with given id if it exists or null.
     */
    @Override
    public Quote findById(Integer id) {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return quotes.get(id);
    }

    @Override
    public Iterable<Quote> findAll() {
        List<Quote> all = new ArrayList<>();
        for(Map.Entry<Integer, Quote> q : quotes.entrySet()) {
            all.add(q.getValue());
        }
        return all;
    }

    @Override
    public Iterable<Quote> findAllById(Iterable<Integer> ids) {
        if(ids == null) {
            throw new IllegalArgumentException("Ids iterable cannot be null.");
        }
        List<Quote> iterable = new ArrayList<>();
        ids.forEach(id -> {
            if(quotes.get(id) != null) {
                iterable.add(quotes.get(id));
            }
        });
        return iterable;
    }

    /**
     * Saves a quote and returns the saved quote.
     *
     * The incoming quote will be assigned the next
     * id even if it already has a non-null id.
     *
     * @param quote - cannot be null.
     * @return the saved quote
     */
    @Override
    public Quote save(Quote quote) {
        if(quote == null) {
            throw new IllegalArgumentException("Null quote cannot be saved.");
        }
        /*
         * Generate a new id and assign to quote regardless of
         * the fact that it might already come with an id.
         *
         * This is important to save the id space.
         * If a user is allowed to choose a quote's id,
         * there is nothing preventing her from saving a
         * quote with an id of Integer.MAX_VALUE, which means that
         * there is no next integer to assign to the next quote...
         */
        final Integer id = 1 + quotes.keySet().stream().max(Comparator.naturalOrder()).orElse(0);
        quote.setId(id);
        quotes.put(id, quote);
        return quote;
    }

    /**
     * Find a quote by id, update its value,
     * and return the old quote object.
     *
     * If a quote with null or non-existent id is provided,
     * it will be assigned a new id and will be saved.
     *
     * @param quote - must exist and cannot be null
     * @return - the updated quote
     */
    @Override
    public Quote update(Quote quote) {
        if(quote == null) {
            throw new IllegalArgumentException("Quote cannot be null.");
        }
        Integer id = quote.getId();
        if(id == null || !quotes.containsKey(id)) {
            /*
             * If the incoming quote's id is null or does not
             * exist in the quotes data structure, the id of the returned
             * quote is different from that of the incoming quote
             * because it is generated by the quote access layer.
             *
             * TODO: Does this violate Idempotency?
             */
            return save(quote);
        }
        /*
         * A quote with the incoming quote's id exists.
         */
        Quote oldQuote = quotes.get(id);
        quote.setId(id);
        quotes.put(id, quote);
        /*
         * The returned quote has the same
         * id as that of the incoming one.
         */
        return oldQuote;
    }

    /**
     * Checks if a quote with given id exists.
     * @param id - cannot be null.
     * @return true if quote exists; false, otherwise.
     */
    @Override
    public boolean existsById(Integer id) {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        return quotes.get(id) != null;
    }

    /**
     * Delete a quote by id.
     * @param id - cannot be null
     * @return true if a quote was removed; false, otherwise.
     */
    @Override
    public boolean delete(Integer id) {
        if(id == null) {
            throw new IllegalArgumentException("Id cannot be null.");
        }
        Quote removed = quotes.remove(id);
        return removed != null;
    }

    @Override
    public int count() {
        return quotes.size();
    }

    @Override
    public Set<Integer> getIds() {
        return quotes.keySet();
    }
}
