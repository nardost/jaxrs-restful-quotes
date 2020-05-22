package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepoDeleteTest {

    private final Repository<Quote, Integer> repository;

    public RepoDeleteTest() {
        this.repository = SimpleQuotesRepository.getInstance();
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> repository.delete(null));
    }

}
