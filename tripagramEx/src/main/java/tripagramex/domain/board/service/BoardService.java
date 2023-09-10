package tripagramex.domain.board.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tripagramex.domain.board.dto.CreateRequest;
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

    private Board getBoardForCreate(Long loginAccountId, CreateRequest createRequest) {
        Board board = createRequest.toBoard();
        board.addAccountId(loginAccountId);
        return board;
    }
}
