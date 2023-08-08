package tripagramex.domain.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.account.dto.*;
import tripagramex.domain.account.service.AccountCRUDService;
import tripagramex.domain.account.validation.CreateRequestValidator;
import tripagramex.domain.account.validation.UpdateRequestValidator;
import tripagramex.global.argumentresolver.LoginAccountId;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountCRUDController {

    private final AccountCRUDService AccountCRUDService;
    private final CreateRequestValidator createRequestValidator;
    private final UpdateRequestValidator updateRequestValidator;

    @InitBinder("createRequest")
    public void initCreateRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(createRequestValidator);
    }

    @InitBinder("updateRequest")
    public void initUpdateRequest(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(updateRequestValidator);
    }

    @PostMapping
    public ResponseEntity<IdDto> create(@RequestBody @Valid CreateRequest createRequest) {
        IdDto idDto = AccountCRUDService.create(createRequest);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ReadResponse> read(@PathVariable Long accountId) {
        ReadResponse readResponse = AccountCRUDService.read(accountId);
        return new ResponseEntity<>(readResponse, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<ReadLoginAccountResponse> readLoginAccount(@LoginAccountId Long loginAccountId) {
        ReadLoginAccountResponse readLoginAccountResponse = AccountCRUDService.readLoginAccount(loginAccountId);
        return new ResponseEntity<>(readLoginAccountResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<IdDto> update(@LoginAccountId Long loginAccountId,
                                        @RequestBody @Valid UpdateRequest updateRequest) {
        IdDto idDto = AccountCRUDService.update(loginAccountId, updateRequest);
        return new ResponseEntity<>(idDto, HttpStatus.OK);
    }
}
