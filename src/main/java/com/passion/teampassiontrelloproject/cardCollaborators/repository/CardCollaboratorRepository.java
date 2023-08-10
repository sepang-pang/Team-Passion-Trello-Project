package com.passion.teampassiontrelloproject.cardCollaborators.repository;

import com.passion.teampassiontrelloproject.cardCollaborators.entity.CardCollaborators;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardCollaboratorRepository extends JpaRepository<CardCollaborators,Long> {
}
