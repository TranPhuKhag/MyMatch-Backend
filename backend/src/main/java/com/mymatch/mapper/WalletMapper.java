package com.mymatch.mapper;

import com.mymatch.dto.response.wallet.WalletResponse;
import com.mymatch.entity.Wallet;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WalletMapper {
    WalletResponse toResponse(Wallet wallet);
}
