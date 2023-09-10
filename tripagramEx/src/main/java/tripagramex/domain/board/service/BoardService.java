package tripagramex.domain.board.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.board.dto.CreateRequest;
import tripagramex.domain.board.dto.ReadResponse;
import tripagramex.domain.board.dto.UpdateRequest;
import tripagramex.domain.board.entity.Board;
import tripagramex.global.common.dto.IdDto;
import tripagramex.domain.board.repository.BoardRepository;

@Builder
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public IdDto create(Long loginAccountId, CreateRequest createRequest) {
        Board board = getBoardForCreate(loginAccountId, createRequest);
        Board savedBoard = boardRepository.save(board);
        return new IdDto(savedBoard.getId());
    }

    public ReadResponse read(Long boardId) {
        Board board = boardRepository.findWithAccount(boardId).get();
        return ReadResponse.of(board);
    }

    @Transactional
    public void update(Long boardId, UpdateRequest updateRequest) {
        Board board = boardRepository.findById(boardId).get();
        Board boardForUpdate = updateRequest.toBoard();
        board.modify(boardForUpdate);
    }

    @Transactional
    public void delete(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        board.softDelete();
    }

    private Board getBoardForCreate(Long loginAccountId, CreateRequest createRequest) {
        Board board = createRequest.toBoard();
        board.addAccountId(loginAccountId);
        return board;
    }
}
