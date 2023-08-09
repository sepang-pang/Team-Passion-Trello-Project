package com.passion.teampassiontrelloproject.card.repository;

import com.passion.teampassiontrelloproject.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

}
