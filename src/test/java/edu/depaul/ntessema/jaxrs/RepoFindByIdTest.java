package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RepoFindByIdTest {

    private final Repository<Quote, Integer> repository;

    public RepoFindByIdTest() {
        this.repository = SimpleQuotesRepository.getInstance();
    }

    @Test
    public void throwsIllegalArgumentExceptionOnNullIdInput() {
        assertThrows(IllegalArgumentException.class, () -> repository.findById(null));
    }

    @ParameterizedTest
    @ValueSource(ints = { -5, -3, -2, -1, 0, 2000, Integer.MAX_VALUE })
    public void returnsNullIfIdDoesNotExist(int id) {
        assertNull(repository.findById(id));
    }

}
