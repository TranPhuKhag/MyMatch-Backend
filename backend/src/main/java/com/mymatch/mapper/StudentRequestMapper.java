package com.mymatch.mapper;
import com.mymatch.dto.request.studentrequest.StudentRequestUpdateRequest;
import com.mymatch.dto.request.studentrequest.StudentRequestCreationRequest;
import com.mymatch.dto.response.studentrequest.StudentRequestResponse;
import com.mymatch.entity.StudentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface    StudentRequestMapper {
    @Mapping(target = "student.user", ignore = true)
    StudentRequestResponse toResponse(StudentRequest entity);
    StudentRequest toEntity(StudentRequestCreationRequest req);
    void updateStudentRequest(@MappingTarget StudentRequest studentRequest, StudentRequestUpdateRequest req);
}
