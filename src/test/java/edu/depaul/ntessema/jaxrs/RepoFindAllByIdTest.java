package edu.depaul.ntessema.jaxrs;

import edu.depaul.ntessema.jaxrs.data.model.Quote;
import edu.depaul.ntessema.jaxrs.data.repository.Repository;
import edu.depaul.ntessema.jaxrs.data.repository.SimpleQuotesRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RepoFindAllByIdTest {

    private final Repository<Quote, Integer> repository;

    public RepoFindAllByIdTest() {
        this.repository = SimpleQuotesRepository.getInstance();
    }

    @Test
    public void returnsEmptyListIfQuotesWithGivenIdsDoNotExist() {
        final Integer[] ids = { 0, -5 };
        List<Quote> found = new ArrayList<>();
        repository.findAllById(Arrays.asList(ids)).forEach(found::add);
        assertTrue(found.isEmpty());
    }
}
