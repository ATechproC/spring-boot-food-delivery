package com.atechproc.mapper;

import com.atechproc.dto.CostumeIngredientCategoryDto;
import com.atechproc.dto.IngredientsItemDto;
import com.atechproc.model.IngredientCategory;
import com.atechproc.model.IngredientsItem;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IngredientsItemMapper  {
    public static IngredientsItemDto toDto(IngredientsItem item) {
        IngredientsItemDto itemDto = new IngredientsItemDto();
        itemDto.setId(item.getId());
        itemDto.setName(item.getName());
        itemDto.setIsInStock(item.getIsInStock());
        itemDto.setIngredientCategory(toCostumeIngCatDto(item.getCategory()));

        return itemDto;
    }

    public static List<IngredientsItemDto> toDTOs(List<IngredientsItem> items) {
        return items.stream()
                .map(IngredientsItemMapper::toDto).collect(Collectors.toList());
    }

    public static CostumeIngredientCategoryDto toCostumeIngCatDto(IngredientCategory category) {
        CostumeIngredientCategoryDto dto = new CostumeIngredientCategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        return dto;
    }
}
