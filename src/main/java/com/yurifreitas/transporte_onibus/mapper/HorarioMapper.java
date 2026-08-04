package com.yurifreitas.transporte_onibus.mapper;

import com.yurifreitas.transporte_onibus.dto.HorarioRequestDto;
import com.yurifreitas.transporte_onibus.dto.HorarioResponseDto;
import com.yurifreitas.transporte_onibus.entity.HorarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface HorarioMapper {
	HorarioEntity toEntity(HorarioRequestDto request);

	HorarioResponseDto toResponseDto(HorarioEntity horarioEntity);
}
