package com.mymatch.mapper;

import com.mymatch.dto.response.transaction.TransactionResponse;
import com.mymatch.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TransactionMapper {
    TransactionResponse toResponse(Transaction transaction);
}
