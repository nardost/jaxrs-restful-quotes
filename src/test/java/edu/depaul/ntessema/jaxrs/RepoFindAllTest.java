package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;

import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;

import org.junit.jupiter.api.Test;

public class RepoFindAllTest {

    private final Repository<Quote, Integer> repository;

    public RepoFindAllTest() {
        this.repository = SimpleQuotesRepository.getInstance();
    }

    @Test
    public void returnsAllQuotesCorrectly() {
        repository.findAll().forEach(q -> System.out.println(q.getQuote()));
    }
}
