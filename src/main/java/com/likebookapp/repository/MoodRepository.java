package com.likebookapp.repository;

import com.likebookapp.model.entity.Mood;
import com.likebookapp.model.enums.MoodName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoodRepository extends JpaRepository<Mood,Long> {
    Optional<Mood> findByName(MoodName mood);
}
