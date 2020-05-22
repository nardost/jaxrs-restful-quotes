package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepoFindByIdTest {

    private final Repository<Quote, Integer> repository;
    private Iterable<String> quotes = Arrays.asList(
            "Quote 1",
            "Quote 2",
            "Quote 3",
            "Quote 4"
    );

    public RepoFindByIdTest() {
        this.repository = SimpleQuotesRepository.getInstance(quotes);
    }

    @Test
    public void throwsExceptionOnNullIdInput() {
        assertThrows(IllegalArgumentException.class, () -> repository.findById(null));
    }

    @Test
    public void returnsNullIfIdDoesNotExist() {
        assertNull(repository.findById(0));
    }

    @Test
    public void returnTheCorrectQuoteForGivenId() {

    }
}
