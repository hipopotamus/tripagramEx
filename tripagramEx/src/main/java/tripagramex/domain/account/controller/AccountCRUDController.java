package tripagramex.domain.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.account.dto.AccountAddRequest;
import tripagramex.domain.account.dto.AccountDetailsResponse;
import tripagramex.domain.account.dto.IdDto;
import tripagramex.domain.account.dto.LoginAccountDetailsResponse;
import tripagramex.domain.account.service.AccountCRUDService;
import tripagramex.global.argumentresolver.LoginAccountId;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountCRUDController {

    private final AccountCRUDService accountCRUDService;

    @PostMapping
    public ResponseEntity<IdDto> accountAdd(@ModelAttribute @Valid AccountAddRequest accountAddRequest) {
        IdDto idDto = accountCRUDService.addAccount(accountAddRequest);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDetailsResponse> accountDetails(@PathVariable Long accountId) {
        AccountDetailsResponse accountDetailsResponse = accountCRUDService.findAccount(accountId);
        return new ResponseEntity<>(accountDetailsResponse, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<LoginAccountDetailsResponse> loginAccountDetails(@LoginAccountId Long loginAccountId) {
        LoginAccountDetailsResponse loginAccountDetailsResponse = accountCRUDService.findLoginAccount(loginAccountId);
        return new ResponseEntity<>(loginAccountDetailsResponse, HttpStatus.OK);
    }
}
