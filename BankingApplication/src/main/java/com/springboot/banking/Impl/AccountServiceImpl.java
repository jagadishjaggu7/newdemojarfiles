package com.springboot.banking.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springboot.banking.Mapper.AccountMapper;
import com.springboot.banking.dto.AccountDto;
import com.springboot.banking.entity.Account;
import com.springboot.banking.repository.AccountRepo;
import com.springboot.banking.service.AccountService;


@Service
public class AccountServiceImpl implements AccountService {
	
	private AccountRepo repo;
	

	public AccountServiceImpl(AccountRepo repo) {
		super();
		this.repo = repo;
	}
	

	@Override
	public AccountDto createAccount(AccountDto accountDto) {
		Account account =AccountMapper.mapToAccount(accountDto);
		Account savedAccount=repo.save(account);
		return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public AccountDto getAccountById(Integer Id) {
		Account account=repo.findById(Id).orElseThrow(()->new RuntimeException("Account Does Not Exists"));
		return AccountMapper.mapToAccountDto(account);
	}


	@Override
	public AccountDto deposit(Integer Id, double amount) {
		Account account=repo.findById(Id).orElseThrow(()->new RuntimeException("Account Does Not Exists"));
		double total=account.getBalance()+ amount;
		account.setBalance(total);
		Account savedAccount=repo.save(account);
	    return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public AccountDto withdraw(Integer Id, double amount) {
		Account account=repo.findById(Id).orElseThrow(()->new RuntimeException("Account Does Not Exists"));
		
		if(account.getBalance()< amount) {
			throw new RuntimeException("Insufficient Balance");
		}
		double total=account.getBalance()- amount;
		account.setBalance(total);
		Account savedAccount=repo.save(account);
	    return AccountMapper.mapToAccountDto(savedAccount);
	}


	@Override
	public List<AccountDto> getAllAccounts() {
		List<Account> accounts=repo.findAll();
		return accounts.stream().map((account)->AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
	}


	@Override
	public void deleteAccount(Integer Id) {
		Account account =repo.findById(Id)
							 .orElseThrow(()->new RuntimeException("Account Does Not Exists"));
		repo.deleteById(Id);
		
	}

}
