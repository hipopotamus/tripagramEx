package tripagramex.domain.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.account.dto.*;
import tripagramex.domain.account.service.AccountCRUDService;
import tripagramex.domain.account.validation.AccountValidator;
import tripagramex.global.argumentresolver.LoginAccountId;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountCRUDController {

    private final AccountCRUDService accountCRUDService;
    private final AccountValidator accountValidator;

    @PostMapping
    public ResponseEntity<IdDto> create(@RequestBody @Valid CreateRequest createRequest) {
        accountValidator.verifyDuplicateEmail(createRequest.getEmail());
        accountValidator.verifyDuplicateNickname(createRequest.getNickname());

        IdDto idDto = accountCRUDService.create(createRequest);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ReadResponse> read(@PathVariable Long accountId) {
        accountValidator.verifyExistsById(accountId);

        ReadResponse readResponse = accountCRUDService.read(accountId);
        return new ResponseEntity<>(readResponse, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<ReadLoginAccountResponse> readLoginAccount(@LoginAccountId Long loginAccountId) {
        ReadLoginAccountResponse readLoginAccountResponse = accountCRUDService.readLoginAccount(loginAccountId);
        return new ResponseEntity<>(readLoginAccountResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<IdDto> update(@LoginAccountId Long loginAccountId,
                                        @RequestBody @Valid UpdateRequest updateRequest) {
        accountValidator.verifyDuplicateNickname(updateRequest.getNickname());

        IdDto idDto = accountCRUDService.update(loginAccountId, updateRequest);
        return new ResponseEntity<>(idDto, HttpStatus.OK);
    }

    @DeleteMapping
    public void delete(@LoginAccountId Long loginAccountId) {
        accountCRUDService.delete(loginAccountId);
    }
}
