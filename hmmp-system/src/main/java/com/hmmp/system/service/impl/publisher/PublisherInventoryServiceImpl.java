package com.hmmp.system.service.impl.publisher;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmmp.system.domain.publisher.PublisherInventory;
import com.hmmp.system.mapper.publisher.PublisherInventoryMapper;
import com.hmmp.system.service.publisher.IPublisherInventoryService;

@Service
public class PublisherInventoryServiceImpl implements IPublisherInventoryService {

    @Autowired
    private PublisherInventoryMapper inventoryMapper;

    @Override
    public List<PublisherInventory> selectInventoryList(PublisherInventory inventory) {
        return inventoryMapper.selectInventoryList(inventory);
    }

    @Override
    public PublisherInventory selectInventoryById(Long inventoryId) {
        return inventoryMapper.selectInventoryById(inventoryId);
    }

    @Override
    public int insertInventory(PublisherInventory inventory) {
        return inventoryMapper.insertInventory(inventory);
    }

    @Override
    public int updateInventory(PublisherInventory inventory) {
        return inventoryMapper.updateInventory(inventory);
    }

    @Override
    public int deleteInventoryByIds(Long[] inventoryIds) {
        return inventoryMapper.deleteInventoryByIds(inventoryIds);
    }
}