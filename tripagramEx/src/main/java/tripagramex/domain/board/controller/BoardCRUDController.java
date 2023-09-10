package tripagramex.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tripagramex.domain.board.dto.CreateRequest;
import tripagramex.domain.board.service.BoardService;
import tripagramex.global.argumentresolver.LoginAccountId;
import tripagramex.global.common.dto.IdDto;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardCRUDController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<IdDto> create(@LoginAccountId Long loginAccountId,
                                        @RequestBody @Valid CreateRequest createRequest) {
        IdDto idDto = boardService.create(loginAccountId, createRequest);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }
}
