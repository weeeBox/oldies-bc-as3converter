package bc.flash.utils
{
	import bc.flash.geom.Matrix;
	import bc.flash.geom.Point;
	
	/** Uses a matrix to transform 2D coordinates into a different space. If you pass a 
     *  'resultPoint', the result will be stored in this point instead of creating a new object.*/
    public function transformCoords(matrix:Matrix, x:Number, y:Number,
                                    resultPoint:Point=null):Point
    {
        if (resultPoint == null) resultPoint = new Point();   
        
        resultPoint.x = matrix.a * x + matrix.c * y + matrix.tx;
        resultPoint.y = matrix.d * y + matrix.b * x + matrix.ty;
        
        return resultPoint;
    }
}
