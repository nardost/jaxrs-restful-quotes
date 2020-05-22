package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import edu.depaul.ntessema.jaxrs.exception.BadRequestException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepoSaveTest {

    private final Repository<Quote, Integer> repository;

    public RepoSaveTest() {
        this.repository = SimpleQuotesRepository.getInstance();
    }

    @Test
    public void throwsIllegalArgumentExceptionOnNullInput() {
        assertThrows(BadRequestException.class, () -> repository.save(null));
    }
}
