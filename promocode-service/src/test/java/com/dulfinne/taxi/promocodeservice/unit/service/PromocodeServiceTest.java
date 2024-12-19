package com.dulfinne.taxi.promocodeservice.unit.service;

import com.dulfinne.taxi.promocodeservice.dto.request.DiscountRequest;
import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.DiscountResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PaginatedResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeResponse;
import com.dulfinne.taxi.promocodeservice.exception.ActionNotAllowedException;
import com.dulfinne.taxi.promocodeservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.promocodeservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.promocodeservice.mapper.PaginatedMapper;
import com.dulfinne.taxi.promocodeservice.mapper.PromocodeMapper;
import com.dulfinne.taxi.promocodeservice.model.Promocode;
import com.dulfinne.taxi.promocodeservice.repository.PromocodeRepository;
import com.dulfinne.taxi.promocodeservice.repository.PromocodeUsageRepository;
import com.dulfinne.taxi.promocodeservice.service.impl.PromocodeServiceImpl;
import com.dulfinne.taxi.promocodeservice.util.PaginationTestData;
import com.dulfinne.taxi.promocodeservice.util.PromocodeTestData;
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
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class PromocodeServiceTest {

  @InjectMocks
  private PromocodeServiceImpl service;
  
  @Mock 
  private PromocodeRepository promocodeRepository;
  @Mock
  private PromocodeUsageRepository usageRepository;
  @Spy
  private PromocodeMapper mapper = Mappers.getMapper(PromocodeMapper.class);
  @Spy
  private PaginatedMapper paginatedMapper = new PaginatedMapper();

  @Test
  void getAllPromocodes_whenValidParams_thenReturnAllPaginatedResponsePage() {
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    Page<Promocode> promocodesPage = new PageImpl<>(List.of(promocode, promocode));
    PromocodeResponse response = PromocodeTestData.getFirstPromocodeResponse();
    PaginatedResponse<PromocodeResponse> expected =
        PaginationTestData.getPaginatedResponse(List.of(response, response));

    // Arrange
    when(promocodeRepository.findAll(any(Pageable.class))).thenReturn(promocodesPage);

    // Act
    PaginatedResponse<PromocodeResponse> result =
        service.getAllPromocodes(
            PaginationTestData.DEFAULT_OFFSET,
            PaginationTestData.DEFAULT_LIMIT,
            PaginationTestData.PROMOCODE_SORT_FIELD,
            PaginationTestData.SORT_ORDER);

    // Assert
    assertEquals(expected, result);
    assertEquals(expected.totalElements(), result.totalElements());
    assertEquals(expected.content(), result.content());

    verify(promocodeRepository, times(1)).findAll(any(Pageable.class));
    verify(paginatedMapper, times(1)).toPaginatedResponse(any(Page.class));
    verify(mapper, times(expected.content().size())).toResponse(any(Promocode.class));
  }

  @Test
  void getPromocodeByCode_whenValidParams_thenReturnPromocodeResponse() {
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    PromocodeResponse expected = PromocodeTestData.getFirstPromocodeResponse();

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.of(promocode));

    // Act
    PromocodeResponse result = service.getPromocodeByCode(PromocodeTestData.FIRST_CODE);

    // Assert
    assertEquals(expected, result);

    verify(promocodeRepository, times(1)).findByCode(any(String.class));
    verify(mapper, times(1)).toResponse(any(Promocode.class));
  }

  @Test
  void getPromocodeByCode_whenPromocodeNotFound_thenThrowEntityNotFoundException() {
    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        EntityNotFoundException.class,
        () -> service.getPromocodeByCode(PromocodeTestData.FIRST_CODE));
  }

  @Test
  void createPromocode_whenValidParams_thenReturnPromocodeResponse() {
    PromocodeRequest request = PromocodeTestData.getCreateRequest();
    Promocode createdPromocode = PromocodeTestData.getCreatedPromocode();
    PromocodeResponse expected = PromocodeTestData.getCreatedPromocodeResponse();
    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.empty());
    when(promocodeRepository.save(any(Promocode.class))).thenReturn(createdPromocode);

    // Act
    PromocodeResponse result = service.createPromocode(request);

    // Assert
    assertEquals(expected, result);

    verify(promocodeRepository, times(1)).findByCode(any(String.class));
    verify(promocodeRepository, times(1)).save(any(Promocode.class));
    verify(mapper, times(1)).toResponse(any(Promocode.class));
  }

  @Test
  void createPromocode_whenNotUniqueCode_thenThrowEntityAlreadyExistsException() {
    PromocodeRequest request = PromocodeTestData.getCreateRequest();
    Promocode createdPromocode = PromocodeTestData.getCreatedPromocode();

    // Arrange
    when(promocodeRepository.findByCode(any(String.class)))
        .thenReturn(Optional.of(createdPromocode));

    // Act & Assert
    assertThrows(EntityAlreadyExistsException.class, () -> service.createPromocode(request));
  }

  @Test
  void updatePromocode_whenUpdateToSameFieldsValid_thenReturnPromocodeResponse() {
    String code = PromocodeTestData.FIRST_CODE;
    PromocodeRequest request = PromocodeTestData.getCreateRequest();
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    PromocodeResponse expected = PromocodeTestData.getFirstPromocodeResponse();

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.of(promocode));
    when(promocodeRepository.save(any(Promocode.class))).thenReturn(promocode);

    // Act
    PromocodeResponse result = service.updatePromocode(code, request);

    // Assert
    assertEquals(expected, result);
    verify(promocodeRepository, times(1)).save(any(Promocode.class));
    verify(mapper, times(1)).toResponse(any(Promocode.class));
  }

  @Test
  void updatePromocode_whenUsageCountExceedMaxUsages_thenThrowActionNotAllowedException() {
    String code = PromocodeTestData.FIRST_CODE;
    PromocodeRequest request = PromocodeTestData.getCreateRequest();
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    promocode.setUsageCount(promocode.getMaxUsages());

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.of(promocode));

    // Act & Assert
    assertThrows(ActionNotAllowedException.class, () -> service.updatePromocode(code, request));
  }

  @Test
  void updatePromocode_whenPromocodeNotFound_thenThrowEntityNotFoundException() {
    String code = PromocodeTestData.FIRST_CODE;
    PromocodeRequest request = PromocodeTestData.getCreateRequest();

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> service.updatePromocode(code, request));
  }

  @Test
  void updatePromocode_whenUpdateToOtherFieldsValid_thenReturnPromocodeResponse() {
    String code = PromocodeTestData.FIRST_CODE;
    PromocodeRequest request = PromocodeTestData.getUpdateRequest();
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    Promocode updatedPromocode = PromocodeTestData.getUpdatedPromocode();
    PromocodeResponse expected = PromocodeTestData.getUpdatedPromocodeResponse();

    // Arrange
    when(promocodeRepository.findByCode(code)).thenReturn(Optional.of(promocode));
    when(promocodeRepository.findByCode(request.code())).thenReturn(Optional.empty());
    when(promocodeRepository.save(any(Promocode.class))).thenReturn(updatedPromocode);

    // Act
    PromocodeResponse result = service.updatePromocode(code, request);

    // Assert
    assertEquals(expected, result);
    verify(promocodeRepository, times(1)).save(any(Promocode.class));
    verify(mapper, times(1)).toResponse(any(Promocode.class));
  }

  @Test
  void getDiscount_whenValidAndTypePercentage_thenReturnDiscountResponse() {
    DiscountRequest request = PromocodeTestData.getPercentageDiscountRequest();
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    DiscountResponse expected = new DiscountResponse(PromocodeTestData.PERCENTAGE_DISCOUNT);

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.of(promocode));
    when(usageRepository.existsByUsernameAndCode(request.username(), promocode.getCode()))
        .thenReturn(false);
    // Act
    DiscountResponse result = service.getDiscount(request);

    // Assert
    assertEquals(expected, result);
    verify(promocodeRepository, times(1)).findByCode(any(String.class));
  }

  @Test
  void getDiscount_whenValidAndTypeFixedAmount_thenReturnDiscountResponse() {
    DiscountRequest request = PromocodeTestData.getFixedAmountDiscountRequest();
    Promocode promocode = PromocodeTestData.getSecondPromocode();
    DiscountResponse expected = new DiscountResponse(PromocodeTestData.FIXED_DISCOUNT);

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.of(promocode));
    when(usageRepository.existsByUsernameAndCode(request.username(), promocode.getCode()))
        .thenReturn(false);
    // Act
    DiscountResponse result = service.getDiscount(request);

    // Assert
    assertEquals(expected, result);
    verify(promocodeRepository, times(1)).findByCode(any(String.class));
  }

  @Test
  void getDiscount_whenOtherDiscountType_thenReturnZeroDiscountResponse() {
    DiscountRequest request = PromocodeTestData.getFixedAmountDiscountRequest();
    Promocode promocode = PromocodeTestData.getSecondPromocode();
    DiscountResponse expected = new DiscountResponse(PromocodeTestData.FIXED_DISCOUNT);

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.of(promocode));
    when(usageRepository.existsByUsernameAndCode(request.username(), promocode.getCode()))
        .thenReturn(false);
    // Act
    DiscountResponse result = service.getDiscount(request);

    // Assert
    assertEquals(expected, result);
    verify(promocodeRepository, times(1)).findByCode(any(String.class));
  }

  @Test
  void getDiscount_whenPromocodeNotFound_thenThrowEntityNotFoundException() {
    DiscountRequest request = PromocodeTestData.getPercentageDiscountRequest();

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(EntityNotFoundException.class, () -> service.getDiscount(request));
  }

  @Test
  void getDiscount_whenPersonAlreadyUsedPromocode_thenThrowActionNotAllowedException() {
    DiscountRequest request = PromocodeTestData.getPercentageDiscountRequest();
    Promocode promocode = PromocodeTestData.getFirstPromocode();

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.of(promocode));
    when(usageRepository.existsByUsernameAndCode(request.username(), promocode.getCode()))
        .thenReturn(true);

    // Act & Assert
    assertThrows(ActionNotAllowedException.class, () -> service.getDiscount(request));
  }

  @Test
  void getDiscount_whenNotActivePromocode_thenThrowActionNotAllowedException() {
    DiscountRequest request = PromocodeTestData.getPercentageDiscountRequest();
    Promocode promocode = PromocodeTestData.getFirstPromocode();
    promocode.setIsActive(false);

    // Arrange
    when(promocodeRepository.findByCode(any(String.class))).thenReturn(Optional.of(promocode));

    // Act & Assert
    assertThrows(ActionNotAllowedException.class, () -> service.getDiscount(request));
  }
}
