package edu.mizzou.Group14_iPERMITAPP.repository;

import edu.mizzou.Group14_iPERMITAPP.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.mizzou.Group14_iPERMITAPP.model.PermitRequest;
import java.util.Optional;
import java.util.List;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {

    // If you expect only one active status per permit request, you could use Optional
    Optional<RequestStatus> findFirstByPermitRequest(PermitRequest permitRequest);
}
