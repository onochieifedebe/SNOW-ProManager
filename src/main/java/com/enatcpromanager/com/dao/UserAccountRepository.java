package com.enatcpromanager.com.dao;

import org.springframework.data.repository.CrudRepository;

import com.enatcpromanager.com.entities.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Long>{

}
