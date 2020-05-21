package edu.depaul.ntessema.jaxrs.service;

import edu.depaul.ntessema.jaxrs.model.Quote;
import edu.depaul.ntessema.jaxrs.repository.Repository;
import edu.depaul.ntessema.jaxrs.repository.SimpleQuotesRepository;

import java.util.List;

public class QuoteService {

    private final Repository<Quote> repository = SimpleQuotesRepository.getInstance();

    public List<Quote> getAllQuotes() {
        return repository.getAll();
    }

    public Quote getQuoteById(Integer id) {
        return repository.getOne(id);
    }

}
