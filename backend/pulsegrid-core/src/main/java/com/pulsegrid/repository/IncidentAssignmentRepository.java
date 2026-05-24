package com.pulsegrid.repository;

import com.pulsegrid.domain.IncidentAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentAssignmentRepository extends JpaRepository<IncidentAssignment, Long> {

    boolean existsByIncidentId(Long incidentId);

}
