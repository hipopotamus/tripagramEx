package tripagramex.domain.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.account.dto.CreateRequest;
import tripagramex.domain.account.dto.ReadResponse;
import tripagramex.domain.account.dto.IdDto;
import tripagramex.domain.account.dto.ReadLoginAccountResponse;
import tripagramex.domain.account.service.CRUDService;
import tripagramex.global.argumentresolver.LoginAccountId;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class CRUDController {

    private final CRUDService CRUDService;

    @PostMapping
    public ResponseEntity<IdDto> create(@ModelAttribute @Valid CreateRequest createRequest) {
        IdDto idDto = CRUDService.create(createRequest);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<ReadResponse> read(@PathVariable Long accountId) {
        ReadResponse readResponse = CRUDService.read(accountId);
        return new ResponseEntity<>(readResponse, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ResponseEntity<ReadLoginAccountResponse> readLoginAccount(@LoginAccountId Long loginAccountId) {
        ReadLoginAccountResponse readLoginAccountResponse = CRUDService.readLoginAccount(loginAccountId);
        return new ResponseEntity<>(readLoginAccountResponse, HttpStatus.OK);
    }
}
