package com.ll.exam.ebook.app.post.service;

import com.ll.exam.ebook.app.member.entity.Member;
import com.ll.exam.ebook.app.post.entity.DeleteType;
import com.ll.exam.ebook.app.post.entity.Post;
import com.ll.exam.ebook.app.post.exception.DataNotFoundException;
import com.ll.exam.ebook.app.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private PostRepository postRepository;

    public Page<Post> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("create"));

        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return postRepository.findByDeleteYn(DeleteType.NORMAL, pageable);

    }

    public Post getPost(Long id) {
        return postRepository.findByIdAndDeleteYn(id, DeleteType.NORMAL)
                .orElseThrow(() -> new DataNotFoundException("no %d question not found,".formatted(id)));
    }

    @Transactional
    public Post write(Long authorId, String subject, String content) {
        Post post = Post
                .builder()
                .author(new Member(authorId))
                .subject(subject)
                .content(content)
                .createDate(LocalDateTime.now())
                .modifyDate(LocalDateTime.now())
                .build();

        postRepository.save(post);

        return post;
    }

    @Transactional
    public boolean modify(Long postId, String subject, String content) {

        Optional<Post> postOptional = postRepository.findById(postId);

        if (postOptional.isEmpty()) { //조회가 안되면 잘못 요청한 것임
            return false;
        } else {
            Post post = postOptional.get();
            post.setSubject(subject);
            post.setContent(content);
            post.setModifyDate(LocalDateTime.now());

            return true;
        }
    }

    @Transactional
    public boolean delete(Long id) {

        Optional<Post> articleOptional = postRepository.findById(id);

        if (articleOptional.isEmpty()) { //조회가 안되면 잘못 요청한 것임
            return false;
        } else {
            Post article = articleOptional.get();
            article.setDeleteYn(DeleteType.DELETE);
            return true;
        }
    }


}
