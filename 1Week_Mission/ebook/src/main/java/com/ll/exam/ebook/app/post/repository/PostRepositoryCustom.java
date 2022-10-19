package com.ll.exam.ebook.app.post.repository;

import com.ll.exam.ebook.app.post.entity.Post;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getQslArticlesOrderByIdDesc();
}
