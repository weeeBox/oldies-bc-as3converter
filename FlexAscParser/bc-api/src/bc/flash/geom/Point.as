package bc.flash.geom
{
    public class Point extends Object
    {
        public var x : Number;
        public var y : Number;

        function Point(x : Number = 0, y : Number = 0) : void
        {
            this.x = x;
            this.y = y;
        }

        public function add(v : Point) : Point
        {
            return new Point(x + v.x, y + v.y);
        }

        public function setTo(xa : Number, ya : Number) : void
        {
            x = xa;
            y = ya;
        }

        public function clone() : Point
        {
            return new Point(x, y);
        }

        public static function distance(pt1 : Point, pt2 : Point) : Number
        {
            var dx : Number = pt1.x - pt2.x;
            var dy : Number = pt1.y - pt2.y;

            return Math.sqrt(dx * dx + dy * dy);
        }

        public function equals(toCompare : Point) : Boolean
        {
            return toCompare != null && toCompare.x == x && toCompare.y == y;
        }

        public static function interpolate(pt1 : Point, pt2 : Point, ratio : Number) : Point
        {
            var invRatio : Number = 1.0 - ratio;
            return new Point(invRatio * pt1.x + ratio * pt2.x, invRatio * pt1.y + ratio * pt2.y);
        }

        public function get length() : Number
        {
            return Math.sqrt(x * x + y * y);
        }

        public function normalize(thickness : Number) : void
        {
            var inverseLength : Number = thickness / length;
            x = x * inverseLength;
            y = y * inverseLength;
        }

        public function offset(dx : Number, dy : Number) : void
        {
            x += dx;
            y += dy;
        }

        public static function polar(len : Number, angle : Number) : Point
        {
            return new Point(Math.cos(angle) * len, Math.sin(angle) * len);
        }

        public function subtract(v : Point) : Point
        {
            return new Point(x - v.x, y - v.y);
        }
    }
}
