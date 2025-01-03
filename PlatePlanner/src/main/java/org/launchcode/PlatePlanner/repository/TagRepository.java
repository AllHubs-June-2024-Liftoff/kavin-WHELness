package org.launchcode.PlatePlanner.repository;

import org.launchcode.PlatePlanner.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
