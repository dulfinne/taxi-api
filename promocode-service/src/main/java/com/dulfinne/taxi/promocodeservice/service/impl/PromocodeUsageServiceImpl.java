package com.dulfinne.taxi.promocodeservice.service.impl;

import com.dulfinne.taxi.promocodeservice.dto.request.PromocodeUsageRequest;
import com.dulfinne.taxi.promocodeservice.dto.response.PromocodeUsageResponse;
import com.dulfinne.taxi.promocodeservice.exception.EntityNotFoundException;
import com.dulfinne.taxi.promocodeservice.mapper.PromocodeUsageMapper;
import com.dulfinne.taxi.promocodeservice.model.Promocode;
import com.dulfinne.taxi.promocodeservice.model.PromocodeUsage;
import com.dulfinne.taxi.promocodeservice.repository.PromocodeRepository;
import com.dulfinne.taxi.promocodeservice.repository.PromocodeUsageRepository;
import com.dulfinne.taxi.promocodeservice.service.PromocodeUsageService;
import com.dulfinne.taxi.promocodeservice.util.ExceptionKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PromocodeUsageServiceImpl implements PromocodeUsageService {

  private final PromocodeUsageRepository usageRepository;
  private final PromocodeRepository promocodeRepository;
  private final PromocodeUsageMapper mapper;

  @Transactional(readOnly = true)
  @Override
  public PromocodeUsageResponse getPromocodeUsageById(String id) {
    PromocodeUsage usage = getUsageIfExists(id);
    return mapper.toResponse(usage);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<PromocodeUsageResponse> getAllPromocodeUsages(
      Integer offset, Integer limit, String sortField, String sortOrder) {

    Sort.Direction direction = Sort.Direction.fromString(sortOrder);
    Page<PromocodeUsage> usages =
        usageRepository.findAll(PageRequest.of(offset, limit, Sort.by(direction, sortField)));
    return usages.map(mapper::toResponse);
  }

  @Transactional(readOnly = true)
  @Override
  public Page<PromocodeUsageResponse> getPromocodeUsagesByUsername(
      String username, Integer offset, Integer limit, String sortField, String sortOrder) {

    Sort.Direction direction = Sort.Direction.fromString(sortOrder);
    Page<PromocodeUsage> usages =
        usageRepository.findByUsername(
            username, PageRequest.of(offset, limit, Sort.by(direction, sortOrder)));
    return usages.map(mapper::toResponse);
  }

  @Transactional
  @Override
  public PromocodeUsageResponse createPromocodeUsage(PromocodeUsageRequest request) {
    PromocodeUsage usage = mapper.toEntity(request);
    usage.setUsageDate(Instant.now());
    usage = usageRepository.save(usage);

    Promocode promocode = getPromocodeIfExists(usage.getCode());
    updateUsageCount(promocode);
    promocodeRepository.save(promocode);
    return mapper.toResponse(usage);
  }

  private PromocodeUsage getUsageIfExists(String id) {
    return usageRepository
        .findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ExceptionKeys.USAGE_NOT_FOUND_ID, id));
  }

  private Promocode getPromocodeIfExists(String code) {
    return promocodeRepository
        .findByCode(code)
        .orElseThrow(
            () -> new EntityNotFoundException(ExceptionKeys.PROMOCODE_NOT_FOUND_CODE, code));
  }

  private void updateUsageCount(Promocode promocode) {
    promocode.setUsageCount(promocode.getUsageCount() + 1);
    if (promocode.getUsageCount().equals(promocode.getMaxUsages())) {
      promocode.setIsActive(false);
    }
  }
}
