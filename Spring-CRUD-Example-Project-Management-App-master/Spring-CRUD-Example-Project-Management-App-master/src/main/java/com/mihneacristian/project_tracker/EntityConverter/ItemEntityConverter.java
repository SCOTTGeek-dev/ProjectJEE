package com.mihneacristian.project_tracker.EntityConverter;

import com.mihneacristian.project_tracker.DTO.ItemDTO;
import com.mihneacristian.project_tracker.Entities.Deadline;
import com.mihneacristian.project_tracker.Entities.Item;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;


@Service
public class ItemEntityConverter implements EntityConverter<Item, ItemDTO> {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public ItemDTO convertToDTO(Item itemEntity) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(itemEntity.getItemId());
        itemDTO.setTitle(itemEntity.getTitle());
        itemDTO.setDescription(itemEntity.getDescription());
        itemDTO.setStatusOfItem(itemEntity.getStatusOfItem().getStatusName());
        itemDTO.setTypeOfItem(itemEntity.getTypeOfItem().getName());
        itemDTO.setTeamMemberId(itemEntity.getTeamMemberOfItem().getMemberId());
        itemDTO.setTeamMemberOfProjectFirstName(itemEntity.getTeamMemberOfItem().getFirstName());
        itemDTO.setTeamMemberOfProjectLastName(itemEntity.getTeamMemberOfItem().getLastName());
        itemDTO.setTeamMemberOfProjectEmailAddress(itemEntity.getTeamMemberOfItem().getEmailAddress());

        if (itemEntity.getDeadline() != null) {
            itemDTO.setDeadline(dateFormat.format(itemEntity.getDeadline().getDueDate()));
        }

        return itemDTO;
    }

    @Override
    public Item convertToEntity(ItemDTO itemDTO) {
        Item itemEntity = new Item();
        itemEntity.setTitle(itemDTO.getTitle());
        itemEntity.setDescription(itemDTO.getDescription());

        if (itemDTO.getDeadline() != null && !itemDTO.getDeadline().isEmpty()) {
            try {
                Deadline deadline = new Deadline();
                deadline.setDueDate(dateFormat.parse(itemDTO.getDeadline()));
                itemEntity.setDeadline(deadline);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return itemEntity;
    }
}
