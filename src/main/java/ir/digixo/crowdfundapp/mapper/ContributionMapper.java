package ir.digixo.crowdfundapp.mapper;

import ir.digixo.crowdfundapp.dto.ContributionDto;
import ir.digixo.crowdfundapp.entity.Contribution;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ContributionMapper {

    ContributionMapper INSTANCE = Mappers.getMapper(ContributionMapper.class);

    ContributionDto toDto(Contribution contribution);
    Contribution toEntity(ContributionDto contributionDto);
}
