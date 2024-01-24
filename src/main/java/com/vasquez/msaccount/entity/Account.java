package com.vasquez.msaccount.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "account")
public class Account {

    @Id
    private String accountId;

    @NotNull
    private String clientId;

    @NotNull
    private String productId;

    private String businessEntity;

    @NotNull
    private String accountNumber;

    @NotNull
    private Double availableBalance;

    private LocalDate createdAt;

}
