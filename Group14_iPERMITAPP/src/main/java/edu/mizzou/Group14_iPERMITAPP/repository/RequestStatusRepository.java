package edu.mizzou.Group14_iPERMITAPP.repository;

import edu.mizzou.Group14_iPERMITAPP.model.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.mizzou.Group14_iPERMITAPP.model.PermitRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface RequestStatusRepository extends JpaRepository<RequestStatus, Long> {

    @Query("""
    SELECT DISTINCT r.permitRequest
    FROM RequestStatus r
    WHERE r.permitRequestStatus = 'Payment Accepted'
    """)
    List<PermitRequest> findPaidPermitRequests();

    @Query("""
    SELECT rs
    FROM RequestStatus rs
    WHERE rs.date = (
        SELECT MAX(r2.date)
        FROM RequestStatus r2
        WHERE r2.permitRequest = rs.permitRequest
    )
    """)
    List<RequestStatus> findLatestStatuses();

}
