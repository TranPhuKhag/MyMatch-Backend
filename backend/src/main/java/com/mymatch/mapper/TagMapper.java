package com.mymatch.mapper;

import com.mymatch.dto.response.tag.TagResponse;
import com.mymatch.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TagMapper {


    TagResponse toTagResponse(Tag tag);

}
