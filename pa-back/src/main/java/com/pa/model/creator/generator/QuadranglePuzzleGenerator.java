package com.pa.model.creator.generator;

import com.pa.model.creator.PieceShapeOutline;
import com.pa.model.puzzle.Piece;
import com.pa.model.puzzle.PuzzleData;

import java.awt.Image;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

/**
 * A puzzle generator which supports all types of piece shapes built on a right angle based grid.
 * @author Ewelina Gren
 * @version 1.0
 */
public class QuadranglePuzzleGenerator extends PuzzleGenerator {

    public static final int MINIMAL_EDGE_LENGTH = 20;

    private final EdgeGenerator edgeGenerator;

    /**
     * Creates a new generator instance based on a specified piece edge type.
     * @param edgeGenerator the type of piece edge that define the target {@code PieceShape}
     */
    public QuadranglePuzzleGenerator(EdgeGenerator edgeGenerator) {
       this.edgeGenerator = edgeGenerator;
    }

    /**
     * Splits an {@code Image} into rectangular {@code Pieces}, with a specified number of rows and columns.
     * @param rows how many rows of {@code Pieces} should there be in the puzzle
     * @param columns how many columns of {@code Pieces} should there be in the puzzle
     * @param image what is the final {@code Image} of the complete puzzle
     * @return a {@code PuzzleData} instance storing information about the puzzle {@code Pieces}
     */
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

    /**
     * Divides the image dimensions into equal parts based on the number of rows and columns,
     * in order to create a grid of {@code Points} that represent the four corners of each {@code Piece}.
     * @param rows how many rows of {@code Pieces} should there be in the puzzle
     * @param columns how many columns of {@code Pieces} should there be in the puzzle
     * @param imageWidth what is the total width of the puzzle {@code Image}
     * @param imageHeight what is the total height of the puzzle {@code Image}
     * @return a grid of {@code Points} representing the corners of the puzzle {@code Pieces}
     */
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

    /**
     * Generates {@code Shapes} to be assigned to the {@code Pieces} of the puzzle.
     * @param pointsGrid a grid of {@code Points} representing the corners of all the puzzle {@code Pieces}
     * @return a two-dimensional array of {@code Shapes} to be assigned to the puzzle {@code Pieces}
     */
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

    /**
     * Generates a line representing the side of the {@code Piece} that falls at the edge of the {@code Image},
     * and therefore is not shared with any other {@code Piece}.
     * @param start a point where the {@code Path2D} starts
     * @param end a point where the {@code Path2D} ends
     * @return the edge of the {@code Piece} as {@code Path2D}
     */
    protected Path2D generateOuterEdge(Point2D start, Point2D end) {
        EdgeGenerator generator = new EdgeGenerator();
        return generator.generateEdge(start, end);
    }

    /**
     * Generates an edge of a puzzle {@code Piece} that's shared with another {@code Piece}.
     * @param start a point where the {@code Path2D} starts
     * @param end a point where the {@code Path2D} ends
     * @return the edge of the {@code Piece} as {@code Path2D}
     */
    protected Path2D generateInnerEdge(Point2D start, Point2D end) {
        return edgeGenerator.generateEdge(start, end);
    }

    /**
     * Calculates the ordinal of a {@code Piece} based on the row and the column it belongs to.
     * @param columns a total number of columns in the puzzle
     * @param row the row the specific {@code Piece} belongs to
     * @param column the column the specific {@code Piece} belongs to
     * @return the {@code Piece's} ordinal as an int value
     */
    protected static int getOrdinal(int columns, int row, int column) {
        return row * columns + column;
    }

    /**
     * Returns an array of ordinals of the {@code Pieces} that share an edge with the selected {@code Piece}
     * @param rows the total number of rows in the puzzle
     * @param columns the total number of columns in the puzzle
     * @param row the row the selected {@code Piece} belongs to
     * @param column the columns the selected {@code Piece} belongs to
     * @return an array of {@code Integers} representing the ordinals of neighbouring {@code Pieces}
     */
    protected static Integer[] getNeighbouringOrdinals(int rows, int columns, int row, int column) {
        Integer[] neighbouringOrdinals = new Integer[4];
        neighbouringOrdinals[0] = row == 0 ? null : getOrdinal(columns, row - 1, column);
        neighbouringOrdinals[1] = column == 0 ? null : getOrdinal(columns, row, column - 1);
        neighbouringOrdinals[2] = row == rows - 1 ? null : getOrdinal(columns, row + 1, column);
        neighbouringOrdinals[3] = column == columns - 1 ? null : getOrdinal(columns, row, column + 1);
        return neighbouringOrdinals;
    }

}
