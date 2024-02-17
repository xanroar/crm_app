package com.example.crm.service

import org.springframework.stereotype.Service
import org.modelmapper.ModelMapper

@Service
class MapperService(private val modelMapper: ModelMapper) {
    fun <T, U> convertEntityToDto(entity: T, dtoClass: Class<U>): U {
        return try {
            modelMapper.map(entity, dtoClass)
        } catch (e: Exception) {
            throw RuntimeException("Failed to convert entity to DTO", e)
        }
    }

    fun <T, U> convertDtoToEntity(dto: T, entityClass: Class<U>): U {
        return try {
            modelMapper.map(dto, entityClass)
        } catch (e: Exception) {
            throw RuntimeException("Failed to convert DTO to entity", e)
        }
    }
}
