package com.lghcode.briefbook.service;

import org.springframework.transaction.annotation.Transactional;

/**
 * @Author lgh
 * @Date 2020/8/22 23:16
 */
@Transactional(rollbackFor = Exception.class)
public interface UserLikeCommentService {
}
