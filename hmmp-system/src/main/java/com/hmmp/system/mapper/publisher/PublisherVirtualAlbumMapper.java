package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherVirtualAlbum;

public interface PublisherVirtualAlbumMapper {
    public List<PublisherVirtualAlbum> selectAlbumList(PublisherVirtualAlbum album);
    public PublisherVirtualAlbum selectAlbumById(Long albumId);
    public int insertAlbum(PublisherVirtualAlbum album);
    public int updateAlbum(PublisherVirtualAlbum album);
    public int deleteAlbumById(Long albumId);
    public int deleteAlbumByIds(Long[] albumIds);
}