package edu.depaul.ntessema.jaxrs.data.service;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuoteService {

    private final Repository<Quote, Integer> repository = SimpleQuotesRepository.getInstance(seed);

    public List<Quote> getAllQuotes() {
        Iterable<Quote> iterable = repository.findAll();
        List<Quote> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    public Quote getQuoteById(Integer id) {
        return repository.findById(id);
    }


    /*
     * The quotes silo is initially seeded with five quotes.
     */
    private final static Iterable<String > seed = Arrays.asList(
            "Conscience does make cowards of us all.",
            "What does not destroy me makes me stronger.",
            "Everything has been figured out, except how to live.",
            "There is no fate that cannot be surmounted by scorn.",
            "There are so many dawns that have not yet broken."
    );

}
