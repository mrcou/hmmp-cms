package com.hmmp.system.service.publisher;

import java.util.List;
import com.hmmp.system.domain.publisher.PublisherInventory;

public interface IPublisherInventoryService {
    public List<PublisherInventory> selectInventoryList(PublisherInventory inventory);
    public PublisherInventory selectInventoryById(Long inventoryId);
    public int insertInventory(PublisherInventory inventory);
    public int updateInventory(PublisherInventory inventory);
    public int deleteInventoryByIds(Long[] inventoryIds);
}