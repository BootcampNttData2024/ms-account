package com.vasquez.msaccount.web.mapper;

import com.vasquez.msaccount.entity.Account;
import com.vasquez.msaccount.model.AccountRequest;
import com.vasquez.msaccount.model.AccountResponse;
import org.springframework.beans.BeanUtils;

/**
 * Account mapper.
 *
 * @author Vasquez
 * @version 1.0.
 */
public class AccountMapper {

  /**
   * To entity.
   *
   * @param accountRequest accountRequest
   * @return Account
   */
  public static Account toEntity(AccountRequest accountRequest) {
    Account account = new Account();
    BeanUtils.copyProperties(accountRequest, account);
    return account;
  }

  /**
   * To response.
   *
   * @param account account
   * @return AccountResponse
   */
  public static AccountResponse toResponse(Account account) {
    AccountResponse accountResponse = new AccountResponse();
    BeanUtils.copyProperties(account, accountResponse);
    return accountResponse;
  }

}
