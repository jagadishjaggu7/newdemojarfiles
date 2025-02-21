package com.springboot.banking.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.banking.dto.AccountDto;
import com.springboot.banking.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
	
	private AccountService service;

	public AccountController(AccountService service) {
		super();
		this.service = service;
	}
	

     //Add Account REST API
	@PostMapping
	public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
		return new ResponseEntity<>(service.createAccount(accountDto),HttpStatus.CREATED);
	}
	
	
	// Get Account REST API
	@GetMapping("/{Id}")
	public ResponseEntity<AccountDto> getAccountById(@PathVariable Integer Id){
		AccountDto accountDto=service.getAccountById(Id);
		return new ResponseEntity<>(accountDto,HttpStatus.OK);
	}
	
	// Deposit REST API
	
	@PutMapping("/{Id}/deposit")
	public ResponseEntity<AccountDto> deposit(@PathVariable Integer Id,
			                                  @RequestBody Map<String,Double>request){
		
		Double amount = request.get("amount");
		AccountDto accountDto=service.deposit(Id, amount);
		return new ResponseEntity<>(accountDto,HttpStatus.OK);
	}

	// Withdraw REST API
	@PutMapping("/{Id}/withdraw")
	public ResponseEntity<AccountDto> withdraw(@PathVariable Integer Id,
			                                  @RequestBody Map<String,Double>request){
		
		Double amount = request.get("amount");
		AccountDto accountDto=service.withdraw(Id, amount);
		return new ResponseEntity<>(accountDto,HttpStatus.OK);
	}
	
	// Get All Accounts REST API
	@GetMapping
	public ResponseEntity<List<AccountDto>> getAllAccounts(){
		
		List<AccountDto> accounts =service.getAllAccounts();
		return new ResponseEntity<>(accounts,HttpStatus.OK);
	}
	
	// Delete Account REST API
	@DeleteMapping("/{Id}")
	public ResponseEntity<String> deleteAccount(@PathVariable Integer Id){
		service.deleteAccount(Id);
		return ResponseEntity.ok("Account Deleted Successfully");
	}
}
