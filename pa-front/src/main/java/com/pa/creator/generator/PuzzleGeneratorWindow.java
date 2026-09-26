package com.pa.creator.generator;

import com.pa.creator.PieceShape;
import com.pa.puzzle.Piece;
import com.pa.puzzle.PuzzleData;
import com.pa.util.ImageUtil;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;

public class PuzzleGeneratorWindow extends JFrame {

    private PuzzleData puzzleData;

    public PuzzleGeneratorWindow() {
        setSize(500, 900);
        setTitle("PuzzleGenerator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

        generatePuzzle();

        JPanel panel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setColor(Color.BLACK);
                g2.translate(100, 100);
                for (Piece[] row : puzzleData.getPieces()) {
                    for (Piece piece : row) {
                        Shape shape = piece.getShape();
                        g2.draw(shape);
                    }
                }
                g2.translate(-100, -100);
            }
        };

        add(panel);
        repaint();
    }

    private void generatePuzzle() {
        PuzzleGenerator generator = PuzzleGenerator.getGenerator(PieceShape.CLASSIC);
        puzzleData = generator.generatePuzzle(8, 6, ImageUtil.getBlankImage(Color.BLUE, 300, 600));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            PuzzleGeneratorWindow window = new PuzzleGeneratorWindow();
        });
    }

}

