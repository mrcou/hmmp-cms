package com.hmmp.system.domain.publisher;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hmmp.common.core.domain.BaseEntity;

/**
 * 新闻内容对象 publisher_news_content
 */
public class PublisherNewsContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 内容ID */
    private Long contentId;

    /** 栏目ID */
    private Long columnId;

    /** 栏目名称（列表查询字段） */
    private String columnName;

    /** 标题 */
    private String title;

    /** 摘要 */
    private String summary;

    /** 富文本正文 */
    private String content;

    /** 状态：draft草稿 published已发布 */
    private String status;

    /** 图片地址，多个以逗号分隔 */
    private String images;

    /** 视频地址，多个以逗号分隔 */
    private String videos;

    /** 附件地址，多个以逗号分隔 */
    private String attachments;

    /** 来源 */
    private String source;

    /** 作者 */
    private String author;

    /** 编辑 */
    private String editor;

    /** 来源链接 */
    private String sourceUrl;

    /** 发布时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date publishTime;

    /** 排序号，数值越大越靠前 */
    private Integer sortOrder;

    public Long getContentId()
    {
        return contentId;
    }

    public void setContentId(Long contentId)
    {
        this.contentId = contentId;
    }

    public Long getColumnId()
    {
        return columnId;
    }

    public void setColumnId(Long columnId)
    {
        this.columnId = columnId;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getImages()
    {
        return images;
    }

    public void setImages(String images)
    {
        this.images = images;
    }

    public String getVideos()
    {
        return videos;
    }

    public void setVideos(String videos)
    {
        this.videos = videos;
    }

    public String getAttachments()
    {
        return attachments;
    }

    public void setAttachments(String attachments)
    {
        this.attachments = attachments;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getEditor()
    {
        return editor;
    }

    public void setEditor(String editor)
    {
        this.editor = editor;
    }

    public String getSourceUrl()
    {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl)
    {
        this.sourceUrl = sourceUrl;
    }

    public Date getPublishTime()
    {
        return publishTime;
    }

    public void setPublishTime(Date publishTime)
    {
        this.publishTime = publishTime;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }
}
