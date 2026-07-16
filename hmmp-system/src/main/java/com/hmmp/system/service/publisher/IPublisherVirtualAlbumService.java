package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherVirtualAlbum;

public interface IPublisherVirtualAlbumService {
    public List<PublisherVirtualAlbum> selectAlbumList(PublisherVirtualAlbum album);
    public PublisherVirtualAlbum selectAlbumById(Long albumId);
    public int insertAlbum(PublisherVirtualAlbum album);
    public int updateAlbum(PublisherVirtualAlbum album);
    public int deleteAlbumByIds(Long[] albumIds);
}