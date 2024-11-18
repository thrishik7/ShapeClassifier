import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShapeClassifierTest {
    private ShapeClassifier classifier;

    @BeforeEach
    public void setUp() {
        classifier = new ShapeClassifier();
    }

    @Test
    public void testLine() {
        // Small line with perimeter 5 (odd)
        assertEquals("Wrong Size,Wrong Even/Odd", classifier.evaluateGuess("Line,Large,Yes,5"));
        // Large line with perimeter 200 (even)
        assertEquals("Yes", classifier.evaluateGuess("Line,Large,Yes,200"));
    }

    @Test
    public void testCircle() {
        // Small circle
        assertEquals("Wrong Size,Wrong Even/Odd", classifier.evaluateGuess("Circle,Large,Yes,5,5"));
        // Large circle with equal radius
        assertEquals("Yes", classifier.evaluateGuess("Circle,Large,Yes,50,50"));
        // Should be an ellipse
        assertEquals("No: Suggestion=Ellipse", classifier.evaluateGuess("Circle,Large,Yes,20,30"));
    }

    @Test
    public void testEllipse() {
        // Valid ellipse (different radii)
        assertEquals("Yes", classifier.evaluateGuess("Ellipse,Large,Yes,50,30"));
        // Should be a circle
        assertEquals("No: Suggestion=Circle", classifier.evaluateGuess("Ellipse,Large,Yes,50,50"));
    }

    @Test
    public void testTriangles() {
        // Equilateral triangle
        assertEquals("Yes", classifier.evaluateGuess("Equilateral,Large,Yes,100,100,100"));
        // Isosceles triangle
        assertEquals("Yes", classifier.evaluateGuess("Isosceles,Large,Yes,100,100,50"));
        // Scalene triangle
        assertEquals("Yes", classifier.evaluateGuess("Scalene,Large,Yes,60,70,80"));
        // Invalid triangle
        assertEquals("No: Suggestion=Not A Triangle", classifier.evaluateGuess("Scalene,Large,Yes,1,1,100"));
    }

    @Test
    public void testRectangle() {
        // Valid rectangle
        assertEquals("Yes", classifier.evaluateGuess("Rectangle,Large,Yes,100,50,100,50"));
        // Should be a square
        assertEquals("No: Suggestion=Square", classifier.evaluateGuess("Rectangle,Large,Yes,100,100,100,100"));
    }

    @Test
    public void testSquare() {
        // Valid square
        assertEquals("Yes", classifier.evaluateGuess("Square,Large,Yes,100,100,100,100"));
        // Should be a rectangle
        assertEquals("No: Suggestion=Rectangle", classifier.evaluateGuess("Square,Large,Yes,100,50,100,50"));
    }

    @Test
    public void testSizeGuesses() {
        // Small shape (perimeter < 100)
        assertEquals("Wrong Size", classifier.evaluateGuess("Line,Large,Yes,50"));
        // Large shape (perimeter >= 100)
        assertEquals("Yes", classifier.evaluateGuess("Line,Large,Yes,200"));
    }

    @Test
    public void testEvenOddGuesses() {
        // Even perimeter
        assertEquals("Yes", classifier.evaluateGuess("Line,Large,Yes,200"));
        // Odd perimeter
        assertEquals("Yes", classifier.evaluateGuess("Line,Large,No,201"));
        // Wrong even/odd guess
        assertEquals("Wrong Even/Odd", classifier.evaluateGuess("Line,Large,Yes,201"));
    }

    @Test
    public void testInvalidInputs() {
        assertEquals("No: Invalid input", classifier.evaluateGuess(""));
        assertEquals("No: Invalid input", classifier.evaluateGuess("Invalid,Input,Yes"));
        assertEquals("No: Invalid input", classifier.evaluateGuess("Square,Large,Yes,abc,def,ghi,jkl"));
    }
}