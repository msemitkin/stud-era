package ua.knu.csc.studera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.knu.csc.studera.domain.provider.Provider;

@Repository
public interface ProviderRepository extends JpaRepository<Provider, Integer> {
}
