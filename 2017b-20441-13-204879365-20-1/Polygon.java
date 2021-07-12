/**
 * Polygon represents a polygon in the cooardinate system,
 * by using class Point that represents the vertices of the polygon.
 *
 * @author Britt Twig
 * @version 06-05-2017
 */
public class Polygon
{
    private Point[] _vertices;
    private int _noOfVertices;
    private final int MAX_VERTICES = 10;

    /**
     * Constructs a new polygon using class Point.
     * @param _vertices Represent a Point in the array of the polygon
     * @param _noOfVertices Represent the number of vertices in the polygon
     */
    public Polygon()
    {
        _vertices = new Point[MAX_VERTICES];
        _noOfVertices = 0;
    }
    
    /**
     * Added a new vertex to the polygon and returns true if it succeeded.
     * @param x The x coordinate of the new vertex
     * @param y The y coordinate of the new vertex
     * @Return True if the added is done correctly
     */
    public boolean addVertex(double x, double y)
    {
        if (_noOfVertices >= MAX_VERTICES)
            return false;

        _vertices[_noOfVertices++] = new Point(x,y);
        return true;
    }

    /**
     * Returns a copy of the first vertex that is highest than the other vertices.
     * @param Highest Represent the highest vertex in the polygon
     */
    public Point highestVertex()
    {
        if (_noOfVertices == 0) //for empty array
            return null;

        Point highest = new Point (_vertices[0]);
        for (int i=1; i < _noOfVertices; i++)
        {
            if (_vertices[i].isAbove(highest))
                highest = new Point(_vertices[i]);
        }
        return highest;
    }
    
    /**
     * Returns a string of Points that represent the polygon.
     * @Return A string of point that represent the polygon
     */
    public String toString()
    {
        String toString = "";
        if (_noOfVertices == 0)
            toString = "The polygon has 0 vertices.";
        else
        {
            // REMARK: you missed the "\n" in the print
            // Points: -1
            toString = "The polygon has " + _noOfVertices + " vertices:" + "(" + _vertices[0].toString();
            for (int i=1; i < _noOfVertices; i++)
            {
                toString += "," + _vertices[i].toString();
            }
            toString += ")";
        }
        return toString;
    }
    
    /**
     * Returns the perimeter of the polygon.
     * @param Perimeter The perimeter of the polygon.
     */
    public double calcPerimeter()
    {
        double perimeter = 0;
        if (_noOfVertices <= 1)
            perimeter = 0;
        else if (_noOfVertices == 2)
            perimeter = _vertices[0].distance(_vertices[1]);
        else
        {
            for (int i=0; i < _noOfVertices-1; i++)
            {
                perimeter += _vertices[i].distance(_vertices[i+1]);
            }
            perimeter += _vertices[_noOfVertices-1].distance(_vertices[0]);
        }
        return perimeter;
    }
        
    /**
     * Returns a number that represent the triangular area that we want to calculate.
     * @param TriangulArea The triangular area
     */
    private double calcTriangulArea(Point A, Point B, Point C)
    {     
        Polygon triangulPolygon = new Polygon();
        triangulPolygon.addVertex(A.getX(), A.getY());
        triangulPolygon.addVertex(B.getX(), B.getY());
        triangulPolygon.addVertex(C.getX(), C.getY());
        
        double triangulArea = 0;
        double s = (triangulPolygon.calcPerimeter()/2);
        double a = A.distance(B);
        double b = B.distance(C);
        double c = C.distance(A);
        triangulArea = Math.sqrt(s*(s-a)*(s-b)*(s-c));
        return triangulArea;
    }
    
    /**
     * Returns a number that represent the polygon area.
     * @param Area The polygon area
     */
    public double calcArea()
    {
        double area = 0;
        if (_noOfVertices < 3)
            area = 0;
        else
        {
            for (int i=1; i < _noOfVertices-1; i++)
            {
                area += calcTriangulArea(_vertices[0], _vertices[i], _vertices[i+1]);      
            }
        }
        return area;
    }
    
    /**
     * Check if this polygon is bigger than the reference polygon (other).
     * @Return True if this polygon is bigger than other polygon
     */
    public boolean isBigger(Polygon other)  
    {   
        return (this.calcArea() > other.calcArea());
    }
    
    /**
     * Returns the location of the point in the array.
     * @param p Point object
     * @param location The location of the point in  the array
     */
    public int findVertex(Point p)
    {
        int findVertex = -1;
        for (int i=0; i < _noOfVertices; i++)
        {
            if (_vertices[i].equals(p))
                findVertex = i;
        }
        return findVertex;
    }

    /**
     * Returns a copy of the point that represent the next point in the polygon (after the point that entered).
     * @param P Represent the point we are looking at
     * @return The next point in the polygon
     */
    public Point getNextVertex(Point p)
    {
        if (_noOfVertices == 0)
            return null;
        
        Point nextP = new Point(_vertices[0]);
        if (_noOfVertices == 1)
            return nextP;
        
        for (int i=0; i < _noOfVertices; i++)
        {
            if (p.equals(_vertices[i]))
            {
                if (i == _noOfVertices-1)
                    return nextP;
                else
                    return new Point(_vertices[i+1]);
            }
        }
        return null;
    }
    
    /**
     * Returns the rightmost vertex in the polygon.
     * @Return Rightmost vertex
     */
    private Point rightmostVertex()
    { 
        if (_noOfVertices == 0) //for empty array
            return null;
        Point rightmost = new Point(_vertices[0]);
        for (int i=1; i < _noOfVertices; i++)
        {
            if (_vertices[i].isRight(rightmost))
                rightmost = _vertices[i];
        }
        return new Point(rightmost);
    }
    
    /**
     * Returns the leftmost vertex in the polygon. 
     * @Return Leftmost vertex
     */
    private Point leftmostVertex()
    { 
        if (_noOfVertices == 0) //for empty array
            return null;
        Point leftmost = new Point(_vertices[0]);
        for (int i=1; i < _noOfVertices; i++)
        {
            if (_vertices[i].isLeft(leftmost))
                leftmost = _vertices[i];
        }
        return new Point(leftmost);
    }
    
    /**
     * Returns the lowest vertex in the polygon. 
     * @Return lowest vertex
     */
    private Point lowestVertex()
    {
        if (_noOfVertices == 0) //for empty array
            return null;
        Point lowest = new Point(_vertices[0]);
        for (int i=1; i < _noOfVertices; i++)
        {
            if (_vertices[i].isUnder(lowest))
                lowest = _vertices[i];
        }
        return new Point(lowest);
    }
    
    /**
     * Returns the frame that block the polygon. 
     * @Return The frame
     */
    // REMARK: you replaced the order between the 3rd and the forth point of the bounding polygon
    // Points: -1
    public Polygon getBoundingBox()
    {
        if (_noOfVertices < 3)
            return null;
        
        Polygon boundingBox = new Polygon();

        boundingBox.addVertex(leftmostVertex().getX(), lowestVertex().getY());
        boundingBox.addVertex(rightmostVertex().getX(), lowestVertex().getY());
        boundingBox.addVertex(leftmostVertex().getX(), highestVertex().getY());
        boundingBox.addVertex(rightmostVertex().getX(), highestVertex().getY());

        return boundingBox;
    }
}