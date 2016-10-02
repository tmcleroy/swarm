package swarm.shared;

import java.util.ArrayList;

public class Layout {
    public final Orientation orientation;
    public final Point size;
    public final Point origin;

    public Layout(Orientation orientation, Point size, Point origin) {
        this.orientation = orientation;
        this.size = size;
        this.origin = origin;
    }

    static public Orientation pointy = new Orientation(Math.sqrt(3.0), Math.sqrt(3.0) / 2.0, 0.0, 3.0 / 2.0, Math.sqrt(3.0) / 3.0, -1.0 / 3.0, 0.0, 2.0 / 3.0, 0.5);
    static public Orientation flat = new Orientation(3.0 / 2.0, 0.0, Math.sqrt(3.0) / 2.0, Math.sqrt(3.0), 2.0 / 3.0, 0.0, -1.0 / 3.0, Math.sqrt(3.0) / 3.0, 0.0);

    static public Point hexToPixel(Layout layout, Hex h) {
        Orientation M = layout.orientation;
        Point size = layout.size;
        Point origin = layout.origin;
        double x = (M.f0 * h.q + M.f1 * h.r) * size.x;
        double y = (M.f2 * h.q + M.f3 * h.r) * size.y;
        return new Point(x + origin.x, y + origin.y);
    }


    static public FractionalHex pixelToHex(Layout layout, Point p) {
        Orientation M = layout.orientation;
        Point size = layout.size;
        Point origin = layout.origin;
        Point pt = new Point((p.x - origin.x) / size.x, (p.y - origin.y) / size.y);
        double q = M.b0 * pt.x + M.b1 * pt.y;
        double r = M.b2 * pt.x + M.b3 * pt.y;
        return new FractionalHex(q, r, -q - r);
    }


    static public Point hexCornerOffset(Layout layout, int corner) {
        Orientation M = layout.orientation;
        Point size = layout.size;
        double angle = 2.0 * Math.PI * (M.startAngle - corner) / 6;
        return new Point(size.x * Math.cos(angle), size.y * Math.sin(angle));
    }


    static public ArrayList<Point> polygonCorners(Layout layout, Hex h) {
        ArrayList<Point> corners = new ArrayList<Point>();
        Point center = Layout.hexToPixel(layout, h);
        for (int i = 0; i < 6; i++) {
            Point offset = Layout.hexCornerOffset(layout, i);
            corners.add(new Point(center.x + offset.x, center.y + offset.y));
        }
        return corners;
    }

}

class Orientation {
    public final double f0, f1, f2, f3;
    public final double b0, b1, b2, b3;
    public final double startAngle;

    public Orientation(double f0, double f1, double f2, double f3, double b0, double b1, double b2, double b3, double startAngle) {
        this.f0 = f0;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.b0 = b0;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.startAngle = startAngle;
    }
}