package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepoExistsByIdTest {

    private final Repository<Quote, Integer> repository;

    public RepoExistsByIdTest() {
        this.repository = SimpleQuotesRepository.getInstance();
    }

    @ParameterizedTest
    @ValueSource(ints = { -5, -2, 0, Integer.MAX_VALUE })
    public void returnsFalseWhenIdDoesNotExist(Integer id) {
        assertFalse(repository.existsById(id));
    }

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3, 4, 5 })
    public void returnsTrueWhenIdExists(Integer id) {
        assertTrue(repository.existsById(id));
    }
}
