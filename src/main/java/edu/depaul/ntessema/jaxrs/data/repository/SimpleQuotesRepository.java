package edu.depaul.ntessema.jaxrs.data.repository;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.exception.BadRequestException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Stream;

/**
 * This class simulates the persistent layer.
 * @author nardos
 */
public class SimpleQuotesRepository implements Repository<Quote, Integer> {

    private final Map<Integer, Quote> quotes;

    private static SimpleQuotesRepository INSTANCE = null;

    private SimpleQuotesRepository() {
        this.quotes = new ConcurrentSkipListMap<>();
        /*
         * Seed the quotes silo.
         */
        Stream.of(QUOTES).forEach(q -> {
            Quote quote = new Quote(q);
            final int index = 1 + quotes.size();
            quote.setId(index);
            quotes.put(index, quote);
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
            throw new BadRequestException("Null quote cannot be saved.");
        }
        /*
         * Generate the next id and assign to quote.
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
     * If a non-existent id is provided,
     * an exception will be thrown.
     *
     * @param quote - must exist and cannot be null
     * @return the old quote object
     */
    @Override
    public Quote update(Quote quote) {
        if(quote == null) {
            throw new BadRequestException("Quote cannot be null.");
        }
        Integer id = quote.getId();
        if(id == null || !quotes.containsKey(id)) {
            return null;
        }
        /*
         * At this point, we know that a quote with the given id exists.
         */
        Quote oldQuote = quotes.get(id);
        quote.setId(id);
        quotes.put(id, quote);
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

    private static final String[] QUOTES = new String[] {
            "Conscience does make cowards of us all.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "There is no fate that cannot be surmounted by scorn.",
            "There are so many dawns that have not yet broken."
    };
}
