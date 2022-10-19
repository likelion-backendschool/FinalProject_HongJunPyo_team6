package com.ll.exam.ebook.app.post.repository;

import com.ll.exam.ebook.app.post.entity.DeleteType;
import com.ll.exam.ebook.app.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Page<Post> findByDeleteYn(DeleteType deleteYn, Pageable pageable);
    List<Post> findByDeleteYn(DeleteType deleteYn);
    Optional<Post> findByIdAndDeleteYn(Long id, DeleteType deleteYn);
}
