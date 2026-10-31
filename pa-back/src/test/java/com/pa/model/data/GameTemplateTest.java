package com.pa.model.data;

import com.pa.model.creator.PieceStyle;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameTemplateTest {

    @Test
    public void gameTemplateTest() {
        GameTemplate template = new GameTemplate(1, 2, PieceStyle.CLASSIC, 10, 15, false);
        Assertions.assertEquals(1, template.id());
        Assertions.assertEquals(2, template.pictureId());
        Assertions.assertEquals(PieceStyle.CLASSIC, template.style());
        Assertions.assertEquals(10, template.rows());
        Assertions.assertEquals(15, template.columns());
        Assertions.assertFalse(template.canPiecesRotate());
    }
}
