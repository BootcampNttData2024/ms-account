package com.vasquez.msaccount.web.mapper;

import com.vasquez.msaccount.entity.Account;
import com.vasquez.msaccount.model.AccountRequest;
import com.vasquez.msaccount.model.AccountResponse;
import org.springframework.beans.BeanUtils;

public class AccountMapper {

    public static Account toEntity(AccountRequest AccountRequest) {
        Account account = new Account();
        BeanUtils.copyProperties(AccountRequest, account);
        return account;
    }

    public static AccountResponse toResponse(Account Account) {
        AccountResponse accountResponse = new AccountResponse();
        BeanUtils.copyProperties(Account, accountResponse);
        return accountResponse;
    }

}
