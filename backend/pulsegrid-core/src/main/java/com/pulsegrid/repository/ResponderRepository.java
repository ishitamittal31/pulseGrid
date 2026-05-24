package com.pulsegrid.repository;

import com.pulsegrid.domain.Responder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponderRepository extends JpaRepository<Responder, Long> {

    List<Responder> findByIsAvailableTrue();
}
