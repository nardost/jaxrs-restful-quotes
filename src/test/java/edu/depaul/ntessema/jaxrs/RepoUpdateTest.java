package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepoUpdateTest {

    private final Repository<Quote, Integer> repository;

    public RepoUpdateTest() {
        this.repository = SimpleQuotesRepository.getInstance();
    }

    @Test
    public void throwsIllegalArgumentExceptionOnNullInput() {
        assertThrows(IllegalArgumentException.class, () -> repository.update(null));
    }

    @Test
    public void throwsIllegalArgumentExceptionOnNullId() {
        assertNull(repository.update(new Quote("Update should throw exception")));
    }
}
