package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.hmmp.common.utils.SecurityUtils;
import com.hmmp.system.domain.publisher.PublisherVirtualAlbum;
import com.hmmp.system.mapper.publisher.PublisherAlbumArticleMapper;
import com.hmmp.system.mapper.publisher.PublisherVirtualAlbumMapper;
import com.hmmp.system.service.publisher.IPublisherVirtualAlbumService;

@Service
public class PublisherVirtualAlbumServiceImpl implements IPublisherVirtualAlbumService {

    @Autowired
    private PublisherVirtualAlbumMapper albumMapper;

    @Autowired
    private PublisherAlbumArticleMapper albumArticleMapper;

    @Override
    public List<PublisherVirtualAlbum> selectAlbumList(PublisherVirtualAlbum album) {
        return albumMapper.selectAlbumList(album);
    }

    @Override
    public PublisherVirtualAlbum selectAlbumById(Long albumId) {
        return albumMapper.selectAlbumById(albumId);
    }

    @Override
    public int insertAlbum(PublisherVirtualAlbum album) {
        album.setCreateBy(SecurityUtils.getUsername());
        return albumMapper.insertAlbum(album);
    }

    @Override
    public int updateAlbum(PublisherVirtualAlbum album) {
        album.setUpdateBy(SecurityUtils.getUsername());
        return albumMapper.updateAlbum(album);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteAlbumByIds(Long[] albumIds) {
        for (Long albumId : albumIds) {
            albumArticleMapper.deleteByAlbumId(albumId);
        }
        return albumMapper.deleteAlbumByIds(albumIds);
    }
}