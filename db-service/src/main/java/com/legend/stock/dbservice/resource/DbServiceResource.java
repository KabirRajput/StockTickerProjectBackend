package com.legend.stock.dbservice.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.legend.stock.dbservice.model.Quote;
import com.legend.stock.dbservice.model.User;
import com.legend.stock.dbservice.model.exception.UserBalanceException;
import com.legend.stock.dbservice.model.exception.UserNotFoundException;
import com.legend.stock.dbservice.model.exception.UserStockException;
import com.legend.stock.dbservice.repository.UserRepository;

@RestController
@RequestMapping("/users")
public class DbServiceResource {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public User saveUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@GetMapping("/")
	public List<User> findAllUser() {
		return userRepository.findAll();
	}
	
	@GetMapping("/{username}") 
	public User findUserByUsername(@PathVariable(name = "username") final String username) {
		User returnedUser = userRepository.findByUsername(username);
		if(returnedUser == null) {
			throw new UserNotFoundException(username + " was not found");
		}
		return returnedUser; 
	}
	
	@PatchMapping("/{username}")
	public User updateBalanceByUsername(@RequestBody User user) {
		User returnedUser = userRepository.findByUsername(user.getUsername());
		returnedUser.setBalance(user.getBalance());
		return userRepository.save(returnedUser);
	}
	
	@DeleteMapping("/{username}")
	public void deleteUser(@PathVariable(value = "username") final String username) {
		User returnedUser = userRepository.findByUsername(username);
		userRepository.delete(returnedUser.getId());
	}
	
	@PostMapping("/{username}/stocks")
	@ResponseStatus(HttpStatus.CREATED)
	public Quote saveUserStock(@PathVariable(value = "username") final String username, @RequestBody Quote quote) {
		User returnedUser = userRepository.findByUsername(username);
		double balance = returnedUser.getBalance();
		double acquisitionPrice = quote.getAcquisitionPrice();
		
		if(balance <= acquisitionPrice) {
			throw new UserBalanceException(username + "'s balance is lower than stock price");
		}
		
		returnedUser.setBalance(balance - acquisitionPrice);
		
		for(Quote el : returnedUser.getQuotes()) {
			if(el.getTicker().equals(quote.getTicker())) {
				throw new UserStockException(username + " has already the stock " + quote.getTicker());
			}
		}
		
		returnedUser.addQuote(quote);
		userRepository.save(returnedUser);
		return returnedUser.getQuotes().get(returnedUser.getQuotes().indexOf(quote));
	}
	
	@GetMapping("/{username}/stocks")
	public List<Quote> findAllStocksByUsername(@PathVariable(value = "username") final String username) {
		User returnedUser = userRepository.findByUsername(username);
		return returnedUser.getQuotes();
	}
	
	@DeleteMapping("/{username}/stocks")
	public Quote deleteUserStock(@PathVariable(value = "username") final String username, @RequestBody Quote quote) {
		User returnedUser = userRepository.findByUsername(username);
		List<Quote> userStocks = returnedUser.getQuotes();
		Quote returnedQuote = null;
		
		for(int i = 0; i < userStocks.size(); i++) {
			if(userStocks.get(i).getTicker().equals(quote.getTicker())) {
				returnedQuote = userStocks.get(i);
				userStocks.remove(i);
				break;
			}
		}
		
		returnedUser.setBalance(returnedUser.getBalance() + quote.getAcquisitionPrice());
		userRepository.save(returnedUser);
		
		return returnedQuote;
	}
//
//	@Autowired
//	private QuotesRepository quotesRepository;
//
//	@GetMapping("/{username}")
//	public List<Quote> getQuotes(@PathVariable("username") final String username) {
//		return quotesRepository.findByUsername(username);
//	}
//	
//	@PostMapping("/add")
//	public List<Quote> add(@RequestBody final Quotes quotes) {
//		quotes.getQuotes()
//			.stream()
//			.map(quote -> new Quote(quotes.getUsername(), quote, getStockPrice(quote).getQuote().getPrice()))
//			.forEach(quote -> {
//				quotesRepository.save(quote);
//			});
//		return quotesRepository.findByUsername(quotes.getUsername());
//	}
//	
//	@DeleteMapping("/delete/{username}")
//	public List<Quote> delete(@PathVariable("username") final String username) {
//		List<Quote> quotes = quotesRepository.findByUsername(username);
//		quotesRepository.delete(quotes);
//		return quotesRepository.findByUsername(username);
//	}
//	
//
//	private Stock getStockPrice(String quote) {
//		try {
//			return YahooFinance.get(quote);
//		} catch (IOException e) {
//			e.printStackTrace();
//			return new Stock(quote);
//		}
//	}
}
