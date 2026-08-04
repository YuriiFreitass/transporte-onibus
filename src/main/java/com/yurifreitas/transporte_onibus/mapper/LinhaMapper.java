package com.yurifreitas.transporte_onibus.mapper;


import com.yurifreitas.transporte_onibus.dto.LinhaRequestDto;
import com.yurifreitas.transporte_onibus.dto.LinhaResponseDto;
import com.yurifreitas.transporte_onibus.entity.LinhaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinhaMapper {
	LinhaEntity toEntity(LinhaRequestDto request);

	LinhaResponseDto toResponseDto(LinhaEntity linhaEntity);


}
