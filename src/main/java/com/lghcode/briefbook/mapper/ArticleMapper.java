package com.lghcode.briefbook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lghcode.briefbook.model.Article;
import com.lghcode.briefbook.model.vo.ArticleVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lgh
 * @Date 2020/8/18 15:47
 */
public interface ArticleMapper extends BaseMapper<Article> {

    /**
     * 分页条件查询
     *
     * @Author lghcode
     * @return IPage<Article>
     * @Date 2020/8/19 15:29
     */
    IPage<Article> selectListPageWithKey(Page<Article> page,@Param("keyWord") String keyWord);

    /**
     * 分页查询
     *
     * @Author lghcode
     * @return IPage<Article>
     * @Date 2020/8/19 15:29
     */
    IPage<Article> selectListPage(Page<Article> page);

    List<ArticleVo> selectListByCorpusId(Long corpusId);
}
