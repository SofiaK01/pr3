package pack.pr231.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pack.pr231.model.RightsRequest;


@Repository
public interface RightsRequestRepository extends JpaRepository<RightsRequest, Integer> {
}
