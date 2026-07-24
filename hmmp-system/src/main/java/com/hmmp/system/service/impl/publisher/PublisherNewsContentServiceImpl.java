package com.hmmp.system.service.impl.publisher;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hmmp.common.exception.ServiceException;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.common.utils.StringUtils;
import com.hmmp.system.domain.publisher.PublisherNewsContent;
import com.hmmp.system.mapper.publisher.PublisherNewsContentMapper;
import com.hmmp.system.service.publisher.IPublisherNewsContentService;

@Service
public class PublisherNewsContentServiceImpl implements IPublisherNewsContentService
{
    @Autowired
    private PublisherNewsContentMapper contentMapper;

    @Override
    public List<PublisherNewsContent> selectNewsContentList(PublisherNewsContent content)
    {
        return contentMapper.selectNewsContentList(content);
    }

    @Override
    public PublisherNewsContent selectNewsContentById(Long contentId)
    {
        return contentMapper.selectNewsContentById(contentId);
    }

    @Override
    public int insertNewsContent(PublisherNewsContent content)
    {
        normalize(content);
        content.setCreateBy(SecurityUtils.getUsername());
        return contentMapper.insertNewsContent(content);
    }

    @Override
    public int updateNewsContent(PublisherNewsContent content)
    {
        if (content.getContentId() == null)
        {
            throw new ServiceException("内容ID不能为空");
        }
        normalize(content);
        content.setUpdateBy(SecurityUtils.getUsername());
        return contentMapper.updateNewsContent(content);
    }

    @Override
    @Transactional
    public int updateNewsContentSort(List<PublisherNewsContent> contents)
    {
        if (contents == null || contents.isEmpty())
        {
            throw new ServiceException("排序数据不能为空");
        }
        int rows = 0;
        String username = SecurityUtils.getUsername();
        for (PublisherNewsContent content : contents)
        {
            if (content.getContentId() != null)
            {
                content.setSortOrder(content.getSortOrder() == null ? 0 : content.getSortOrder());
                content.setUpdateBy(username);
                rows += contentMapper.updateNewsContentSort(content);
            }
        }
        return rows;
    }

    @Override
    public int deleteNewsContentByIds(Long[] contentIds)
    {
        return contentMapper.deleteNewsContentByIds(contentIds);
    }

    private void normalize(PublisherNewsContent content)
    {
        if (content.getColumnId() == null)
        {
            throw new ServiceException("请选择栏目");
        }
        if (StringUtils.isEmpty(content.getTitle()))
        {
            throw new ServiceException("标题不能为空");
        }
        if (StringUtils.isEmpty(content.getContent()))
        {
            throw new ServiceException("内容不能为空");
        }
        content.setTitle(content.getTitle().trim());
        if (!"published".equals(content.getStatus()))
        {
            content.setStatus("draft");
            content.setPublishTime(null);
        }
        else if (content.getPublishTime() == null)
        {
            content.setPublishTime(new Date());
        }
        if (content.getSortOrder() == null)
        {
            content.setSortOrder(0);
        }
    }
}
