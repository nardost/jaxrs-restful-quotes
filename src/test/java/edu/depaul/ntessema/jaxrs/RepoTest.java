package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RepoTest {

    private Iterable<String> quotes = Arrays.asList(
            "Quote 1",
            "Quote 2",
            "Quote 3",
            "Quote 4"
    );

    @Test
    public void testQuotesRepository() {
        Repository<Quote, Integer> repo = SimpleQuotesRepository.getInstance(quotes);
        Quote q = repo.findById(2);
        System.out.println(q.getQuote());
        repo.findAll().forEach(x -> System.out.println(x.getQuote()));
        System.out.println(repo.save(new Quote("A quotable quote")));
        repo.findAll().forEach(x -> System.out.println(x.getQuote()));
        repo.delete(1);
        repo.findAll().forEach(x -> System.out.println(x.getQuote()));
        repo.findAll().forEach(x -> System.out.println(x.getId() + ". " + x.getQuote()));
        Iterable<Integer> ids = Arrays.asList(new Integer[] {2, 3, 4});
        repo.findAllById(ids).forEach(x -> System.out.println(x.getQuote()));
    }
}
