package com.example.retrospective;

import com.example.retrospective.model.Retrospective;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RetrospectiveRepository extends JpaRepository<Retrospective, String> {
}
