package com.atechproc.mapper;

import com.atechproc.dto.IngredientsItemDto;
import com.atechproc.model.IngredientsItem;

import java.util.List;

public class IngredientsItemMapper  {
    public static IngredientsItemDto toDto(IngredientsItem item) {
        IngredientsItemDto itemDto = new IngredientsItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setIsInStock(item.getIsInStock());
        itemDto.setIngredientCategory(item.getCategory().getName());

        return itemDto;
    }

    public static List<IngredientsItemDto> toDTOs(List<IngredientsItem> items) {
        return items.stream()
                .map(IngredientsItemMapper::toDto).toList();
    }
}
