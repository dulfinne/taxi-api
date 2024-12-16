package com.dulfinne.taxi.promocodeservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;

@Document(collection = "promocode")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Promocode {
  @Id private String id;

  @Indexed(unique = true)
  private String code;

  private BigDecimal discount;
  private Boolean isActive;
  private Integer maxUsages;
  private Integer usageCount;
  private Integer type;
}
