package com.vasquez.msaccount.entity;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Account entity.
 *
 * @author Vasquez
 * @version 1.0.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Document(collection = "account")
public class Account {

  @Id
  private String accountId;

  @NotNull
  private String clientId;

  @NotNull
  private String productId;

  @NotNull
  private String productBusinessRuleId;

  private String businessEntity;

  @NotNull
  private String accountNumber;

  private Double minOpening;

  private Double minMonthlyAmount;

  private Double availableBalance;

  private String headlinesIds;

  private String signatoriesIds;

}
