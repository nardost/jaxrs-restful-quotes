package edu.depaul.ntessema.jaxrs.data.service;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import edu.depaul.ntessema.jaxrs.exception.BadRequestException;
import edu.depaul.ntessema.jaxrs.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class QuoteService {

    private final Repository<Quote, Integer> repository = SimpleQuotesRepository.getInstance();

    public List<Quote> getAllQuotes() {
        List<Quote> all = new ArrayList<>();
        repository.findAll().forEach(all::add);
        return all;
    }

    public Quote getQuoteById(Integer id) {
        Quote quote = repository.findById(id);
        if(quote == null) {
            throw new NotFoundException("Quote with id of " + id + " not found.");
        }
        return quote;
    }

    //TODO: check inputs for null...
    public Quote addQuote(Quote quote) {
        if(quote == null || quote.getQuote() == null || quote.getQuote().equals("")) {
            throw new BadRequestException("Adding an empty quote is not allowed.");
        }
        return repository.save(quote);
    }

    //TODO: signature differs
    public Quote updateQuote(Quote newQuote) {
        Quote oldQuote = repository.update(newQuote);
        if (oldQuote == null) {
            throw new NotFoundException("Quote with id " + newQuote.getId() + " does not exist.");
        }
        return oldQuote;
    }

    public void deleteQuote(Integer id) {
        repository.delete(id);
    }

}
