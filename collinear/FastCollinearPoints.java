import java.util.ArrayList;
import java.util.Arrays;



public class FastCollinearPoints {
    private int numberOfSegmentsCount;
    private Point[] herePoints;
    private ArrayList<LineSegment> allSegments;



    private int recursiveSegment (Point[] sortedArray, int index, Point refPoint) {



        if (index < sortedArray.length - 1) {
            if (sortedArray[index].slopeTo(refPoint) == sortedArray[index + 1].slopeTo(refPoint))
                return recursiveSegment(sortedArray, index + 1, refPoint) + 1;
            else return 1;

        }
        else return 1;

    }

    // Function head Segment count
    private void headSegment (Point[] mainArray, Point refPoint) {

        int j = 0;
        while(j < mainArray.length) {
            int c = recursiveSegment(mainArray, j, refPoint) + 1;
            if (c >= 4) {
                Point smallest = refPoint;
                Point largest = refPoint;
                for (int loopCount = 0; loopCount < c - 1; loopCount++) {
                    if (smallest.compareTo(mainArray[j + loopCount]) == 1) smallest = mainArray[j + loopCount];
                    if (largest.compareTo(mainArray[j + loopCount]) == - 1) largest = mainArray[j + loopCount];
                }

                boolean found = false;
                for (LineSegment a : this.allSegments) {
                    if (a == null) break;

                    String[] currentLineSplit = a.toString().split("->");


                    if (currentLineSplit[0].replaceAll("\\s", "").equals(
                            smallest.toString().replaceAll("\\s", "")
                    ) && currentLineSplit[1].replaceAll("\\s", "").equals(
                            largest.toString().replaceAll("\\s", "")
                    ))
                    {
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    this.allSegments.add(new LineSegment(smallest, largest));
                    this.numberOfSegmentsCount += 1;
                }
            }
            j += c - 1;
        }
    }


    public FastCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
        if (points == null) throw new IllegalArgumentException("Wrong Argument");

        if (points[points.length -1] == null) throw new IllegalArgumentException("Point cannot contain a null value");
        Arrays.sort(points);
        for (int w = 0; w < points.length - 1; w++) {
            if (points[w] == null) throw new IllegalArgumentException("Point cannot contain a null value");
            if (points[w].compareTo(points[w + 1]) == 0) throw new IllegalArgumentException("Contains More than one same point");
        }

        this.numberOfSegmentsCount = 0;
        this.herePoints = new Point[points.length];
        for (int consCounter = 0; consCounter < points.length; consCounter++) {
            this.herePoints[consCounter] = points[consCounter];
        }
        this.allSegments = new ArrayList<LineSegment>();


        for (Point constructorPoint : this.herePoints) {
            Arrays.sort(points, constructorPoint.slopeOrder());
            headSegment(points, constructorPoint);
        }

    }

    public int numberOfSegments() {     // the number of line segments
        return this.numberOfSegmentsCount;
    }




    public LineSegment[] segments() {              // the line segments

        LineSegment[] finalSegmentArray = new LineSegment[this.allSegments.size()];
        for (int z = 0; z < finalSegmentArray.length; z++) {
            finalSegmentArray[z] = this.allSegments.get(z);
        }

        return finalSegmentArray;

    }

    /*


    public static void main(String[] args) {

        try {
            File theFile = new File("D:\\Courses\\algorithms part 1\\sort\\javaProjectFinal2\\src\\readFile.txt");
            Scanner pointer = new Scanner(theFile);
            String data = pointer.nextLine();
            int n = Integer.parseInt(data);

            Point[] points = new Point[n];


            for (int i = 0; i < n; i++) {
                data = pointer.nextLine();
                String[] dataSplit = data.split(",");
                int x = Integer.parseInt(dataSplit[0]);
                int y = Integer.parseInt(dataSplit[1]);



                points[i] = new Point(x, y);




            }
            pointer.close();

            FastCollinearPoints myFastCollinearPoints = new FastCollinearPoints(points);
            // StdOut.println(myFastCollinearPoints.numberOfSegments());
            LineSegment[] myLineSegmentTest = myFastCollinearPoints.segments();
            StdOut.println("I printing thing");
            for (LineSegment a : myLineSegmentTest) {
                StdOut.println("The Segments Are: " + a.toString());
            }


        } catch (
                FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

     */
}

