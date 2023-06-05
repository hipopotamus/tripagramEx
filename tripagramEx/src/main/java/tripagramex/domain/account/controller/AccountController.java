package tripagramex.domain.account.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripagramex.domain.account.dto.AccountAddReq;
import tripagramex.domain.account.dto.IdDto;
import tripagramex.domain.account.service.AccountService;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<IdDto> accountAdd(@ModelAttribute @Valid AccountAddReq accountAddReq) {

        IdDto idDto = accountService.addAccount(accountAddReq);

        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }
}
