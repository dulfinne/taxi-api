package com.dulfinne.taxi.promocodeservice.service.impl;

import com.dulfinne.taxi.promocodeservice.dto.request.DiscountRequest;
import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.DiscountResponse;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeResponse;
import com.dulfinne.taxi.promocodeservice.exception.ActionNotAllowedException;
import com.dulfinne.taxi.promocodeservice.exception.EntityAlreadyExistsException;
import com.dulfinne.taxi.promocodeservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.promocodeservice.mapper.PromocodeMapper;
import com.dulfinne.taxi.promocodeservice.model.DiscountType;
import com.dulfinne.taxi.promocodeservice.model.Promocode;
import com.dulfinne.taxi.promocodeservice.repository.PromocodeRepository;
import com.dulfinne.taxi.promocodeservice.repository.PromocodeUsageRepository;
import com.dulfinne.taxi.promocodeservice.service.PromocodeService;
import com.dulfinne.taxi.promocodeservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class PromocodeServiceImpl implements PromocodeService {
  private final PromocodeRepository promocodeRepository;
  private final PromocodeUsageRepository usageRepository;
  private final PromocodeMapper mapper;

  @Transactional(readOnly = true)
  @Override
  public Page<PromocodeResponse> getAllPromocodes(
      Integer offset, Integer limit, String sortField, String sortOrder) {

    Sort.Direction direction = Sort.Direction.fromString(sortOrder);
    Page<Promocode> promocodes =
        promocodeRepository.findAll(PageRequest.of(offset, limit, Sort.by(direction, sortField)));

    return promocodes.map(mapper::toResponse);
  }

  @Transactional(readOnly = true)
  @Override
  public PromocodeResponse getPromocodeByCode(String code) {
    Promocode promocode = getPromocodeIfExists(code);
    return mapper.toResponse(promocode);
  }

  @Transactional
  @Override
  public PromocodeResponse createPromocode(PromocodeRequest request) {
    Promocode promocode = mapper.toEntity(request);

    checkCodeUniquiness(promocode.getCode());
    promocode.setUsageCount(0);
    promocode = promocodeRepository.save(promocode);
    return mapper.toResponse(promocode);
  }

  @Transactional
  @Override
  public PromocodeResponse updatePromocode(String code, PromocodeRequest request) {
    Promocode promocode = getPromocodeIfExists(code);

    checkPromocodeCanBeUpdated(promocode, request);
    mapper.updateEntity(request, promocode);

    promocodeRepository.save(promocode);
    return mapper.toResponse(promocode);
  }

  @Transactional(readOnly = true)
  @Override
  public DiscountResponse getDiscount(DiscountRequest request) {
    Promocode promocode = getPromocodeIfExists(request.code());

    checkPromocodeCanBeUsed(request.username(), promocode);

    BigDecimal discountAmount = countDiscount(promocode, request);
    return new DiscountResponse(discountAmount);
  }

  private BigDecimal countDiscount(Promocode promocode, DiscountRequest request) {
    BigDecimal discountAmount = BigDecimal.ZERO;
    BigDecimal price = request.price();

    DiscountType discountType = DiscountType.fromId(promocode.getType());
    switch (discountType) {
      case FIXED_AMOUNT -> {
        discountAmount = promocode.getDiscount();
      }
      case PERCENTAGE -> {
        discountAmount =
            price
                .multiply(promocode.getDiscount())
                .divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);
      }
    }
    return discountAmount;
  }

  private void checkPromocodeCanBeUsed(String username, Promocode promocode) {
    if (!promocode.getIsActive()) {
      throw new ActionNotAllowedException(ExceptionKeys.PROMOCODE_NOT_ACTIVE, promocode.getCode());
    }

    String code = promocode.getCode();
    if (usageRepository.existsByUsernameAndCode(username, code)) {
      throw new ActionNotAllowedException(ExceptionKeys.PROMOCODE_ALREADY_USED, code);
    }
  }

  private void checkPromocodeCanBeUpdated(Promocode promocode, PromocodeRequest request) {
    checkCodeUniquiness(promocode.getCode(), request.code());
    if (promocode.getUsageCount() >= request.maxUsages()) {
      throw new ActionNotAllowedException(
          ExceptionKeys.USAGE_COUNT_EXCEED_MAX,
          promocode.getCode(),
          promocode.getUsageCount(),
          request.maxUsages());
    }
  }

  private Promocode getPromocodeIfExists(String code) {
    return promocodeRepository
        .findByCode(code)
        .orElseThrow(
            () -> new EntityNotFoundException(ExceptionKeys.PROMOCODE_NOT_FOUND_CODE, code));
  }

  private void checkCodeUniquiness(String code) {
    if (promocodeRepository.findByCode(code).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.PROMOCODE_EXISTS_CODE, code);
    }
  }

  private void checkCodeUniquiness(String code, String updatedCode) {
    boolean isCodeModified = !updatedCode.equals(code);
    if (isCodeModified && promocodeRepository.findByCode(updatedCode).isPresent()) {
      throw new EntityAlreadyExistsException(ExceptionKeys.PROMOCODE_EXISTS_CODE, code);
    }
  }
}
