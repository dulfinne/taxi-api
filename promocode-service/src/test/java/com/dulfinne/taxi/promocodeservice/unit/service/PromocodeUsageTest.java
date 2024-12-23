package com.dulfinne.taxi.promocodeservice.unit.service;

import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeUsageRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;
import com.dulfinne.taxi.promocodeservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.promocodeservice.mapper.PaginatedMapper;
import com.dulfinne.taxi.promocodeservice.mapper.PromocodeUsageMapper;
import com.dulfinne.taxi.promocodeservice.model.Promocode;
import com.dulfinne.taxi.promocodeservice.model.PromocodeUsage;
import com.dulfinne.taxi.promocodeservice.repository.PromocodeRepository;
import com.dulfinne.taxi.promocodeservice.repository.PromocodeUsageRepository;
import com.dulfinne.taxi.promocodeservice.service.impl.PromocodeUsageServiceImpl;
import com.dulfinne.taxi.promocodeservice.util.PaginationTestData;
import com.dulfinne.taxi.promocodeservice.util.PromocodeTestData;
import com.dulfinne.taxi.promocodeservice.util.UsageTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PromocodeUsageTest {

  @InjectMocks
  private PromocodeUsageServiceImpl service;

  @Mock
  private PromocodeUsageRepository usageRepository;
  @Mock
  private PromocodeRepository promocodeRepository;
  @Spy
  private PromocodeUsageMapper mapper = Mappers.getMapper(PromocodeUsageMapper.class);
  @Spy
  private PaginatedMapper paginatedMapper = new PaginatedMapper();

  @Test
  void getAllPromocodeUsages_whenValidParams_thenReturnUsageResponsePage() {
    PromocodeUsage usage = UsageTestData.getUsage();
    Page<PromocodeUsage> usagePage = new PageImpl<>(List.of(usage, usage));
    PromocodeUsageResponse response = UsageTestData.getUsageResponse();
    PaginatedResponse<PromocodeUsageResponse> expected =
        PaginationTestData.getPaginatedResponse(List.of(response, response));

    // Arrange
    when(usageRepository.findAll(any(Pageable.class))).thenReturn(usagePage);

    // Act
    PaginatedResponse<PromocodeUsageResponse> result =
        service.getAllPromocodeUsages(
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.USAGE_SORT_FIELD,
            PaginationTestData.SORT_ORDER);

    // Assert
    assertEquals(expected, result);
    assertEquals(expected.totalElements(), result.totalElements());
    assertEquals(expected.content(), result.content());

    verify(usageRepository, times(1)).findAll(any(Pageable.class));
    verify(paginatedMapper, times(1)).toPaginatedResponse(any(Page.class));
    verify(mapper, times(expected.content().size())).toResponse(any(PromocodeUsage.class));
  }

  @Test
  void getPromocodeUsagesByUsername_whenValidParams_thenReturnUsageResponsePage() {
    String username = PromocodeTestData.USERNAME;
    PromocodeUsage usage = UsageTestData.getUsage();
    Page<PromocodeUsage> usagePage = new PageImpl<>(List.of(usage, usage));
    PromocodeUsageResponse response = UsageTestData.getUsageResponse();
    PaginatedResponse<PromocodeUsageResponse> expected =
        PaginationTestData.getPaginatedResponse(List.of(response, response));

    // Arrange
    when(usageRepository.findByUsername(any(String.class), any(Pageable.class)))
        .thenReturn(usagePage);

    // Act
    PaginatedResponse<PromocodeUsageResponse> resultPage =
        service.getPromocodeUsagesByUsername(
            username,
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.USAGE_SORT_FIELD,
            PaginationTestData.SORT_ORDER);

    // Assert
    assertEquals(expected, resultPage);
    assertEquals(expected.totalElements(), resultPage.totalElements());
    assertEquals(expected.content(), resultPage.content());

    verify(usageRepository, times(1)).findByUsername(any(String.class), any(Pageable.class));
    verify(paginatedMapper, times(1)).toPaginatedResponse(any(Page.class));
    verify(mapper, times(expected.content().size())).toResponse(any(PromocodeUsage.class));
  }

  @Test
  void getPromocodeUsageById_whenValidRequest_thenReturnUsageResponse() {
    PromocodeUsage usage = UsageTestData.getUsage();
    String usageId = usage.getId();
    PromocodeUsageResponse expected = UsageTestData.getUsageResponse();

    // Arrange
    when(usageRepository.findById(any(String.class))).thenReturn(Optional.of(usage));

    // Act
    PromocodeUsageResponse response = service.getPromocodeUsageById(usageId);

    // Assert
    assertEquals(expected, response);
    verify(usageRepository, times(1)).findById(any(String.class));
    verify(mapper, times(1)).toResponse(any(PromocodeUsage.class));
  }

  @Test
  void getPromocodeUsageById_whenUsageNotFound_thenThrowEntityNotFoundException() {
    PromocodeUsage usage = UsageTestData.getUsage();
    String usageId = usage.getId();

    // Arrange
    when(usageRepository.findById(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> service.getPromocodeUsageById(usageId));
  }

  @Test
  void createPromocodeUsage_whenValidRequest_thenReturnUsageResponse() {
    PromocodeUsage usage = UsageTestData.getUsage();
    PromocodeUsageRequest request = UsageTestData.getUsageRequest();
    PromocodeUsageResponse expected = UsageTestData.getUsageResponse();
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    Integer expectedUsageCount = promocode.getUsageCount() + 1;

    // Arrange
    when(usageRepository.save(any(PromocodeUsage.class))).thenReturn(usage);
    when(promocodeRepository.findByCode(request.code())).thenReturn(Optional.of(promocode));

    // Act
    PromocodeUsageResponse response = service.createPromocodeUsage(request);

    // Assert
    assertEquals(expected, response);
    assertEquals(expectedUsageCount, promocode.getUsageCount());
    verify(promocodeRepository, times(1)).save(any(Promocode.class));
    verify(usageRepository, times(1)).save(any(PromocodeUsage.class));
    verify(mapper, times(1)).toResponse(any(PromocodeUsage.class));
  }

  @Test
  void createPromocodeUsage_whenLastPromocodeUsage_thenMakePromocodeDisabled() {
    PromocodeUsage usage = UsageTestData.getUsage();
    PromocodeUsageRequest request = UsageTestData.getUsageRequest();
    PromocodeUsageResponse expected = UsageTestData.getUsageResponse();
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    promocode.setUsageCount(promocode.getMaxUsages() - 1);
    Integer expectedUsageCount = promocode.getMaxUsages();
    boolean expectedIsActive = false;

    // Arrange
    when(usageRepository.save(any(PromocodeUsage.class))).thenReturn(usage);
    when(promocodeRepository.findByCode(request.code())).thenReturn(Optional.of(promocode));

    // Act
    PromocodeUsageResponse response = service.createPromocodeUsage(request);

    // Assert
    assertEquals(expected, response);
    assertEquals(expectedUsageCount, promocode.getUsageCount());
    assertEquals(expectedIsActive, promocode.getIsActive());
    verify(promocodeRepository, times(1)).save(any(Promocode.class));
    verify(usageRepository, times(1)).save(any(PromocodeUsage.class));
    verify(mapper, times(1)).toResponse(any(PromocodeUsage.class));
  }

  @Test
  void createPromocodeUsage_whenPromocodeNotFound_thenEntityNotFoundException() {
    PromocodeUsageRequest request = UsageTestData.getUsageRequest();
    PromocodeUsage usage = UsageTestData.getUsage();

    // Arrange
    when(usageRepository.save(any(PromocodeUsage.class))).thenReturn(usage);
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> service.createPromocodeUsage(request));
  }
}
