package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.model.Quote;
import edu.depaul.ntessema.jaxrs.repository.Repository;
import edu.depaul.ntessema.jaxrs.repository.SimpleQuotesRepository;
import org.junit.Test;

public class RepoTest {
    @Test
    public void testQuotesRepository() {
        Repository<Quote> repo = SimpleQuotesRepository.getInstance();
        Quote q = repo.getOne(2);
        System.out.println(q.getQuote());
        System.out.println(repo.getAll().size());
        System.out.println(repo.add(new Quote("A quotable quote")));
        System.out.println(repo.getAll().size());
        repo.delete(1);
        System.out.println(repo.getAll().size());
        repo.getAll().forEach(x -> System.out.println(x.getId() + ". " + x.getQuote()));
    }
}
