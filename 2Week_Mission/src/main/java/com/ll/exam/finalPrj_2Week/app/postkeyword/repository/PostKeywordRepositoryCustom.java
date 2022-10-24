package com.ll.exam.finalPrj_2Week.app.postkeyword.repository;

import com.ll.exam.finalPrj_2Week.app.postkeyword.entity.PostKeyword;

import java.util.List;

public interface PostKeywordRepositoryCustom {
    List<PostKeyword> getQslAllByAuthorId(Long authorId);
}
