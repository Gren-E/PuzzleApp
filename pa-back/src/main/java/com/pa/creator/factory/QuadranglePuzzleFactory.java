package com.pa.creator.factory;

import com.pa.creator.PieceShapeOutline;
import com.pa.puzzle.Piece;
import com.pa.puzzle.PuzzleData;

import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

public class QuadranglePuzzleFactory extends PuzzleFactory {

    public static final int MINIMAL_EDGE_LENGTH = 20;

    @Override
    public PuzzleData generatePuzzle(int rows, int columns, Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);

        Point[][] pointsGrid = generatePointsOnGrid(rows, columns, width, height);
        Shape[][] pieceShapes = generatePieceShapes(pointsGrid);

        Piece[][] pieces = new Piece[rows][columns];
        setEach(pieces, (row, column) -> new Piece(
                getOrdinal(columns, row, column),
                getNeighbouringOrdinals(rows, columns, row, column),
                pieceShapes[row][column]));

        PuzzleData data = new PuzzleData();
        data.setImage(image);
        data.setPieces(pieces);
        return data;
    }

    protected Point[][] generatePointsOnGrid(int rows, int columns, int imageWidth, int imageHeight) {
        if (rows * MINIMAL_EDGE_LENGTH >= imageWidth) {
            throw new IllegalArgumentException(String.format("Too many rows %d for given width %d.", rows, imageWidth));
        }

        if (columns * MINIMAL_EDGE_LENGTH >= imageHeight) {
            throw new IllegalArgumentException(String.format("Too many columns %d for given height %d.", columns, imageHeight));
        }

        double cellWidth = imageWidth / (double) columns;
        double cellHeight = imageHeight / (double) rows;

        double currentX = 0;
        double currentY = 0;
        
        Point[][] pointsGrid = new Point[rows + 1][columns + 1];
        for (int row = 0 ; row < rows + 1; row++) {
            for (int column = 0 ; column < columns + 1; column++) {
                pointsGrid[row][column] = new Point((int) currentX, (int) currentY);
                currentX += cellWidth;
            }

            currentX = 0;
            currentY += cellHeight;
        }

        return pointsGrid;
    }

    private Shape[][] generatePieceShapes(Point[][] pointsGrid) {
        PieceShapeOutline[][] outlines = new PieceShapeOutline[pointsGrid.length - 1][pointsGrid[0].length - 1];
        setEach(outlines, (row, column) -> new PieceShapeOutline((row + column) % 2 != 1));

        for (int i = 0; i < pointsGrid.length; i++) {
            for (int j = 0; j < pointsGrid[i].length; j++) {
                if (i < pointsGrid.length - 1) {
                    Point start = (i + j) % 2 == 0 ? pointsGrid[i + 1][j] : pointsGrid[i][j];
                    Point end = (i + j) % 2 == 0 ? pointsGrid[i][j] : pointsGrid[i + 1][j];

                    Path2D pathVertical = (j == 0 || j == pointsGrid[i].length - 1) ? generateOuterEdge(start, end) : generateInnerEdge(start, end);

                    if (j != 0) {
                        outlines[i][j - 1].setEast(pathVertical);
                    }

                    if (j != pointsGrid[i].length - 1) {
                        outlines[i][j].setWest(pathVertical);
                    }
                }

                if (j < pointsGrid[i].length - 1) {
                    Point start = (i + j) % 2 == 0 ? pointsGrid[i][j] : pointsGrid[i][j + 1];
                    Point end = (i + j) % 2 == 0 ? pointsGrid[i][j + 1] : pointsGrid[i][j];

                    Path2D pathHorizontal = (i == 0 || i == pointsGrid.length - 1) ? generateOuterEdge(start, end) : generateInnerEdge(start, end);

                    if (i != 0) {
                        outlines[i - 1][j].setSouth(pathHorizontal);
                    }

                    if (i != pointsGrid.length - 1) {
                        outlines[i][j].setNorth(pathHorizontal);
                    }
                }
            }
        }

        Shape[][] shapes = new Shape[pointsGrid.length - 1][pointsGrid[0].length - 1];
        setEach(shapes, (row, column) -> outlines[row][column].createShape());
        return shapes;
    }

    protected Path2D generateOuterEdge(Point2D start, Point2D end) {
        Path2D path = new Path2D.Double();
        path.moveTo(start.getX(), start.getY());

        path.lineTo(end.getX(), end.getY());
        return path;
    }

    protected Path2D generateInnerEdge(Point2D start, Point2D end) {
        return generateOuterEdge(start, end);
    }

    protected static int getOrdinal(int columns, int row, int column) {
        return row * columns + column;
    }

    protected static Integer[] getNeighbouringOrdinals(int rows, int columns, int row, int column) {
        Integer[] neighbouringOrdinals = new Integer[4];
        neighbouringOrdinals[0] = row == 0 ? null : getOrdinal(columns, row - 1, column);
        neighbouringOrdinals[1] = column == 0 ? null : getOrdinal(columns, row, column - 1);
        neighbouringOrdinals[2] = row == rows - 1 ? null : getOrdinal(columns, row + 1, column);
        neighbouringOrdinals[3] = column == columns - 1 ? null : getOrdinal(columns, row, column + 1);
        return neighbouringOrdinals;
    }

}
