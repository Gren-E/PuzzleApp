package com.pa.model.data;

import com.pa.model.creator.PieceStyle;

import java.time.LocalDateTime;

/**
 * Class representing a single game's data.
 * @author Ewelina Gren
 * @version 1.0
 */
public class GameInfo {

    private final int id;
    private final int pictureId;
    private final int rows;
    private final int columns;

    private final boolean rotation;

    private final Integer templateId;

    private final LocalDateTime startTimestamp;

    private final PieceStyle pieceStyle;

    private GameStatus status;

    private int durationInSeconds;

    /**
     * Creates an instance of the class, based on the provided parameters.
     * @param id {@code int} value representing the game's id
     * @param pictureId {@code int} value representing the id of a picture assigned to the game
     * @param rows the number of rows in the puzzle
     * @param columns the number of columns in the puzzle
     * @param rotation can the pieces change their orientation
     * @param templateId {@code Integer} value representing the template's id if one is used, null otherwise
     * @param startTimestamp {@code LocalDateTime} value of when the game started
     * @param status is the game finished or still in progress
     * @param pieceStyle the style of the {@code Piece} shape used in the game
     */
    public GameInfo(int id, int pictureId, int rows, int columns, boolean rotation, Integer templateId, LocalDateTime startTimestamp, GameStatus status, PieceStyle pieceStyle) {
        this.id = id;
        this.pictureId = pictureId;
        this.rows = rows;
        this.columns = columns;
        this.rotation = rotation;
        this.templateId = templateId;
        this.startTimestamp = startTimestamp;
        this.status = status;
        this.pieceStyle = pieceStyle;
    }

    /**
     * Returns the total time the player spent playing since the start of the game.
     * @return the duration of the game in seconds
     */
    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    /**
     * Sets the total time the player spent playing since the start of the game.
     * @param durationInSeconds the duration of the game in seconds
     */
    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    /**
     * Returns the current status of the game.
     * @return {@code FINISHED} if the player won the game, {@code IN_PROGRESS} if they are still playing,
     * or {@code CANCELLED} if the game was cancelled before the puzzle was solved
     */
    public GameStatus getStatus() {
        return status;
    }

    /**
     * Sets the current status of the game.
     * @param status {@code FINISHED} if the player won the game, {@code IN_PROGRESS} if they are still playing,
     * or {@code CANCELLED} if the game was cancelled before the puzzle was solved
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * Returns the id of the game.
     * @return {@code int} value that represents the game's id
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the id of the picture used in the game.
     * @return {@code int} value that represents the picture's id
     */
    public int getPictureId() {
        return pictureId;
    }

    /**
     * Returns the number of rows in the puzzle.
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the puzzle
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns the exact time when the game started.
     * @return {@code LocalDateTime} instance that describes when the game started
     */
    public LocalDateTime getStartTimestamp() {
        return startTimestamp;
    }

    /**
     * Returns the style of the puzzle pieces.
     * @return {@code PieceStyle} of the pieces in the puzzle
     */
    public PieceStyle getPieceStyle() {
        return pieceStyle;
    }

    /**
     * Checks if the pieces are allowed to change their orientation or if it is fixed.
     * @return {@code true} if the {@code Pieces} can be rotated, {@code false} otherwise
     */
    public boolean isRotation() {
        return rotation;
    }

    /**
     * Returns the template's id if one was used, {@code null} otherwise
     * @return template's id or {@code null} if there is none
     */
    public Integer getTemplateId() {
        return templateId;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "id=" + id +
                ", startTimestamp=" + startTimestamp +
                ", status=" + status +
                "}";
    }

}
