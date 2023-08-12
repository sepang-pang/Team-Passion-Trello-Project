package com.passion.teampassiontrelloproject.withdrawn.repository;

import com.passion.teampassiontrelloproject.withdrawn.entity.WithdrawnUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WithdrawnUserRepository extends JpaRepository<WithdrawnUser, Long> {
}
