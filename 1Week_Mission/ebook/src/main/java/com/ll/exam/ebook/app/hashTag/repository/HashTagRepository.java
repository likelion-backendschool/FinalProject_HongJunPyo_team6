package com.ll.exam.ebook.app.hashTag.repository;

import com.ll.exam.ebook.app.hashTag.entity.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    List<HashTag> findAllByPostId(Long id);

    Optional<HashTag> findByPostIdAndKeywordId(Long postId, Long keywordId);

    List<HashTag> findAllByPostIdIn(long[] ids);
}
