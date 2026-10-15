package com.pa.model.data;

import com.pa.model.creator.PieceStyle;

public record GameTemplate(int id, int pictureId, PieceStyle style, int rows, int columns, boolean canPiecesRotate) {

}
