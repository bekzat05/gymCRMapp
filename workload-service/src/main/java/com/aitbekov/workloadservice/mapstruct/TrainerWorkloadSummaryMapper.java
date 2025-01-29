package com.aitbekov.workloadservice.mapstruct;

import com.aitbekov.workloadservice.dto.TrainerWorkloadSummaryDto;
import com.aitbekov.workloadservice.dto.YearlyTrainingSummaryDto;
import com.aitbekov.workloadservice.model.TrainerWorkloadSummary;
import com.aitbekov.workloadservice.model.YearlyTrainingSummary;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TrainerWorkloadSummaryMapper {
    TrainerWorkloadSummaryMapper INSTANCE = Mappers.getMapper(TrainerWorkloadSummaryMapper.class);


    @Mapping(target = "yearlySummaries", source = "yearlySummaries")
    TrainerWorkloadSummaryDto toDTO(TrainerWorkloadSummary summary);

    @Mapping(target = "trainingYear", source = "trainingYear")
    @Mapping(target = "monthlySummary", source = "monthlySummary")
    YearlyTrainingSummaryDto toDTO(YearlyTrainingSummary yearlySummary);

    List<TrainerWorkloadSummaryDto> toDTOList(List<TrainerWorkloadSummary> summaries);
}
