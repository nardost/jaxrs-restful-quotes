package edu.depaul.ntessema.jaxrs.repository;

import edu.depaul.ntessema.jaxrs.model.Quote;

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
public class SimpleQuotesRepository implements Repository<Quote> {

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

    @Override
    public Quote getOne(Integer id) {
        return quotes.get(id);
    }

    @Override
    public List<Quote> getAll() {
        List<Quote> allQuotes = new ArrayList<>();
        for(Map.Entry<Integer, Quote> q : quotes.entrySet()) {
            allQuotes.add(q.getValue());
        }
        return allQuotes;
    }

    @Override
    public Integer add(Quote quote) {
        //TODO ifPresent(...).get()
        final Integer nextId = 1 + quotes.keySet().stream().max(Comparator.naturalOrder()).get();
        quote.setId(nextId);
        //TODO puIfAbsent
        quotes.put(nextId, quote);
        return quote.getId();
    }

    @Override
    public Quote update(Integer id, Quote newQuote) {
        Quote oldQuote = quotes.get(id);
        if(oldQuote != null) {
            newQuote.setId(id);
            quotes.put(id, newQuote);
        }
        return oldQuote;
    }

    @Override
    public void delete(Integer id) {
        //TODO returns boolean. What does it mean?
        quotes.remove(id);
    }

    private final static String [] QUOTES = new String[] {
            "Conscience does make cowards of us all.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "There is no fate that cannot be surmounted by scorn.",
            "There are so many dawns that have not yet broken."
    };
}
