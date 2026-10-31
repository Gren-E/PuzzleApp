package com.pa.model.data;

import com.pa.model.creator.PieceStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class GameInfoTest {

    @Test
    public void gameInfoTest() {
        LocalDateTime timestamp = LocalDateTime.now();
        GameInfo info = new GameInfo(1, 2, 15, 25, true,
                2, timestamp, GameStatus.IN_PROGRESS, PieceStyle.RECTANGLE);
        info.setDurationInSeconds(1000000);

        Assertions.assertEquals(1, info.getId());
        Assertions.assertEquals(2, info.getPictureId());
        Assertions.assertEquals(15, info.getRows());
        Assertions.assertEquals(25, info.getColumns());
        Assertions.assertTrue(info.isRotation());
        Assertions.assertEquals(2, info.getTemplateId());
        Assertions.assertEquals(timestamp, info.getStartTimestamp());
        Assertions.assertEquals(GameStatus.IN_PROGRESS, info.getStatus());
        Assertions.assertEquals(PieceStyle.RECTANGLE, info.getPieceStyle());
        Assertions.assertEquals(1000000, info.getDurationInSeconds());

        info.setStatus(GameStatus.FINISHED);
        Assertions.assertEquals(GameStatus.FINISHED, info.getStatus());

        Assertions.assertEquals("GameInfo{id=1, startTimestamp=" + timestamp +
                ", status=FINISHED}", info.toString());
    }
}
