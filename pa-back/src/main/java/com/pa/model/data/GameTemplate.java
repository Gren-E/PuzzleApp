package com.pa.model.data;

import com.pa.model.creator.PieceStyle;

/**
 * Class describing a common game template. Templates can be used for easier game creation,
 * since they allow the user to pick one of the popular settings configuration, rather than setting each parameter individually.
 * @author Ewelina Gren
 * @version 1.0
 */
public record GameTemplate(int id, int pictureId, PieceStyle style, int rows, int columns, boolean canPiecesRotate) {

}
