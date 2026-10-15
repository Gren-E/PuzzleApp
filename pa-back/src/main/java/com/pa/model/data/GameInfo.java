package com.pa.model.data;

import com.pa.model.creator.PieceStyle;

import java.time.LocalDateTime;

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

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public int getPictureId() {
        return pictureId;
    }

    public int getRows() {
        return rows;
    }

    public LocalDateTime getStartTimestamp() {
        return startTimestamp;
    }

    public PieceStyle getPieceStyle() {
        return pieceStyle;
    }

    public int getColumns() {
        return columns;
    }

    public boolean isRotation() {
        return rotation;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    @Override
    public String toString() {
        return "GameInfo{" +
                "id=" + id +
                ", startTimestamp=" + startTimestamp +
                ", status=" + status +
                '}';
    }

}
