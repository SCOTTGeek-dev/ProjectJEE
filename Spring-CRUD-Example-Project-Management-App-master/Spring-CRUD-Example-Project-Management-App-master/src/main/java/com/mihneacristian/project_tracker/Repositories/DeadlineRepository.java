package com.mihneacristian.project_tracker.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mihneacristian.project_tracker.Entities.Deadline;

@Repository
public interface DeadlineRepository extends JpaRepository<Deadline, Integer> {
	Optional<Deadline> findByItem_ItemId(Integer itemId);

}
