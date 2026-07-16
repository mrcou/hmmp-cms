package com.hmmp.system.mapper.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherInventory;

public interface PublisherInventoryMapper {
    public List<PublisherInventory> selectInventoryList(PublisherInventory inventory);
    public PublisherInventory selectInventoryById(Long inventoryId);
    public PublisherInventory selectInventoryByIssueId(Long issueId);
    public int insertInventory(PublisherInventory inventory);
    public int updateInventory(PublisherInventory inventory);
    public int deleteInventoryById(Long inventoryId);
    public int deleteInventoryByIds(Long[] inventoryIds);
}