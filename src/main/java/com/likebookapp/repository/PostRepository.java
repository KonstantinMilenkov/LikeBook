package com.likebookapp.repository;

import com.likebookapp.model.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<List<Post>> findAllByUserUsername(String username);
    @Query("select p from Post p where p.user.username !=:username")
    Optional<List<Post>> findAllWhereUserIsNotCreator(String username);

    Post findPostById(Long id);

}
