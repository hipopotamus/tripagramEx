package tripagramex.domain.board.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tripagramex.domain.board.dto.CreateRequest;
import tripagramex.domain.board.dto.ReadResponse;
import tripagramex.domain.board.dto.UpdateRequest;
import tripagramex.domain.board.service.BoardService;
import tripagramex.domain.board.validation.BoardValidator;
import tripagramex.global.argumentresolver.LoginAccountId;
import tripagramex.global.common.dto.IdDto;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardCRUDController {

    private final BoardService boardService;
    private final BoardValidator boardValidator;

    @PostMapping
    public ResponseEntity<IdDto> create(@LoginAccountId Long loginAccountId,
                                        @RequestBody @Valid CreateRequest createRequest) {
        IdDto idDto = boardService.create(loginAccountId, createRequest);
        return new ResponseEntity<>(idDto, HttpStatus.CREATED);
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<ReadResponse> read(@PathVariable Long boardId) {
        boardValidator.verifyExistsById(boardId);

        ReadResponse readResponse = boardService.read(boardId);
        return new ResponseEntity<>(readResponse, HttpStatus.OK);
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<String> update(@LoginAccountId Long loginAccountId,
                                         @PathVariable Long boardId,
                                         @RequestBody @Valid UpdateRequest updateRequest) {
        boardValidator.verifyExistsById(boardId);
        boardValidator.verifyUpdateAuthority(loginAccountId, boardId);

        boardService.update(boardId, updateRequest);
        return new ResponseEntity<>("Success Update Board", HttpStatus.OK);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> delete(@LoginAccountId Long loginAccountId, @PathVariable Long boardId) {
        boardValidator.verifyExistsById(boardId);
        boardValidator.verifyUpdateAuthority(loginAccountId, boardId);

        boardService.delete(boardId);
        return new ResponseEntity<>("Success Delete Board", HttpStatus.OK);
    }
}
