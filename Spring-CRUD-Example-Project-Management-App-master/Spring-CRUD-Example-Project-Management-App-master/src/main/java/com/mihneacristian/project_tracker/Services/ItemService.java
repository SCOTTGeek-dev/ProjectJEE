package com.mihneacristian.project_tracker.Services;

import com.mihneacristian.project_tracker.DTO.ItemDTO;
import com.mihneacristian.project_tracker.Entities.*;
import com.mihneacristian.project_tracker.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    TeamMembersRepository teamMembersRepository;

    @Autowired
    TypeRepository typeRepository;

    @Autowired
    DeadlineRepository deadlineRepository; // Inject DeadlineRepository
    @Transactional
    public Item saveNewItem(ItemDTO itemDTO) {
        Optional<Status> byStatusName = statusRepository.findByStatusName(itemDTO.statusOfItem);
        Optional<Type> byTypeName = typeRepository.findByName(itemDTO.typeOfItem);
        Optional<TeamMembers> byMemberId = teamMembersRepository.findByMemberId(itemDTO.teamMemberId);

        TeamMembers teamMembers = byMemberId.orElseGet(() -> {
            TeamMembers newMember = new TeamMembers(itemDTO.teamMemberId);
            return teamMembersRepository.save(newMember);
        });

        Status status = byStatusName.orElseGet(() -> {
            Status newStatus = new Status(itemDTO.statusOfItem);
            return statusRepository.save(newStatus);
        });

        Type type = byTypeName.orElseGet(() -> {
            Type newType = new Type(itemDTO.typeOfItem);
            return typeRepository.save(newType);
        });

        Item itemToBeSaved = new Item();
        itemToBeSaved.setTitle(itemDTO.title);
        itemToBeSaved.setDescription(itemDTO.description);
        itemToBeSaved.setStatusOfItem(status);
        itemToBeSaved.setTypeOfItem(type);
        itemToBeSaved.setTeamMemberOfItem(teamMembers);

        Item savedItem = itemRepository.save(itemToBeSaved);

        // Save deadline if present
        if (itemDTO.deadline != null) {
            Deadline deadline = new Deadline();
            deadline.setDueDate(java.sql.Date.valueOf(itemDTO.deadline));
            deadline.setItem(savedItem);
            deadlineRepository.save(deadline);
        }

        return savedItem;
    }

    @Transactional
    public Item updateItemById(Integer id, ItemDTO itemToBeUpdated) {
        Item item;
        Status status;
        Type type;
        TeamMembers teamMembers;

        Optional<Status> statusOptional = statusRepository.findByStatusName(itemToBeUpdated.statusOfItem);
        Optional<Type> typeOptional = typeRepository.findByName(itemToBeUpdated.typeOfItem);
        Optional<TeamMembers> teamMembersOptional = teamMembersRepository.findByMemberId(itemToBeUpdated.teamMemberId);
        Optional<Item> itemOptional = itemRepository.findById(id);

        if (!itemOptional.isPresent()) {
            throw new RuntimeException("Could not find item with the id: " + id);
        } else {
            item = itemOptional.get();
            status = statusOptional.orElseGet(() -> new Status(itemToBeUpdated.statusOfItem));
            type = typeOptional.orElseGet(() -> new Type(itemToBeUpdated.typeOfItem));
            teamMembers = teamMembersOptional.orElseGet(() -> new TeamMembers(itemToBeUpdated.teamMemberId));

            status.setStatusName(itemToBeUpdated.statusOfItem);
            type.setName(itemToBeUpdated.typeOfItem);
            teamMembers.setMemberId(itemToBeUpdated.teamMemberId);

            item.setTitle(itemToBeUpdated.title);
            item.setDescription(itemToBeUpdated.description);
            item.setStatusOfItem(status);
            item.setTypeOfItem(type);
            item.setTeamMemberOfItem(teamMembers);

            // Update or create deadline
            Optional<Deadline> existingDeadline = deadlineRepository.findByItem_ItemId(id);

            if (existingDeadline.isPresent()) {
                Deadline deadline = existingDeadline.get();
                deadline.setDueDate(java.sql.Date.valueOf(itemToBeUpdated.deadline));
                deadlineRepository.save(deadline);
            } else {
                Deadline newDeadline = new Deadline();
                newDeadline.setDueDate(java.sql.Date.valueOf(itemToBeUpdated.deadline));
                newDeadline.setItem(item);
                deadlineRepository.save(newDeadline);
            }
        }
        return itemRepository.save(item);
    }
    public List<ItemDTO> getAllItems() {
        List<Item> allItems = itemRepository.findAll();
        List<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item item : allItems) {
            ItemDTO itemDTO = new ItemDTO(item);
            itemDTOS.add(itemDTO);
        }
        
        return itemDTOS;
    }
    @Transactional
    public Item findByItemId(Integer id) {
        Optional<Item> byId = itemRepository.findByItemId(id);
        if (byId.isPresent()) {
            return byId.get();
        } else {
            throw new RuntimeException("Could not find an item with the id: " + id);
        }
    }
    public boolean isItemIdPresent(Integer id) {
        return itemRepository.findById(id).isPresent();
    }
    @Transactional
    public void deleteItemById(Integer id) {
        itemRepository.deleteById(id);
    }
    
    }
