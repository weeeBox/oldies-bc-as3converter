package bc.flash.geom
{
	import bc.flash.error.IllegalOperationError;
	import bc.flash.utils.MathHelper;
	import bc.flash.error.NotImplementedError;

	[ConvertOnce]
	public final class Matrix extends Object
	{
		/** The value that affects the positioning of pixels along the x axis when scaling or rotating an image. */
		public var a : Number;
		/** The value that affects the positioning of pixels along the y axis when rotating or skewing an image. */
		public var b : Number;
		/** The value that affects the positioning of pixels along the x axis when rotating or skewing an image. */
		public var c : Number;
		/** The value that affects the positioning of pixels along the y axis when scaling or rotating an image. */
		public var d : Number;
		/** The distance by which to translate each point along the x axis. */
		public var tx : Number;
		/** The distance by which to translate each point along the y axis. */
		public var ty : Number;

		/** Creates a new Matrix object with the specified parameters. */
		public function Matrix(a : Number = 1, b : Number = 0, c : Number = 0, d : Number = 1, tx : Number = 0, ty : Number = 0) : void
		{
			setTo(a, b, c, d, tx, ty);
		}

		/** Returns a new Matrix object that is a clone of this matrix, with an exact copy of the contained object. */
		public function clone() : Matrix
		{
			return new Matrix(a, b, c, d, tx, ty);
		}

		/** Concatenates a matrix with the current matrix, effectively combining the geometric effects of the two. 
		 *  In mathematical terms, concatenating two matrixes is the same as combining them using matrix multiplication. 
		 */
		public function concat(m : Matrix) : void
		{
			concatValues(m.a, m.b, m.c, m.d, m.tx, m.ty);
		}

		/** Concatenates matrix based on the values with the current matrix, effectively combining the geometric effects of the two. 
		 *  In mathematical terms, concatenating two matrixes is the same as combining them using matrix multiplication. 
		 */
		public function concatValues(a : Number, b : Number, c : Number, d : Number, tx : Number, ty : Number) : void
		{
			setTo(this.a * a + this.b * c, // a 
				this.a * b + this.b * d, // b 
				this.c * a + this.d * c, // c 
				this.c * b + this.d * d, // d 
				this.tx * a + this.ty * c + tx, // tx 
				this.tx * b + this.ty * d + ty); // ty
		}
		
		/** Copies a Vector3D object into specific column of the calling Matrix3D object. */
		public function copyColumnFrom(column:uint, vector3D:Vector3D) : void
		{
			throw new NotImplementedError();
		}
		
		/** Copies specific column of the calling Matrix object into the Vector3D object. The w element of the Vector3D object will not be changed. */
		public function copyColumnTo(column:uint, vector3D:Vector3D) : void
		{
			throw new NotImplementedError();
		}

		/** Copies all of the matrix data from the source object into the calling Matrix object. */
		public function copyFrom(sourceMatrix:Matrix) : void
		{
			setTo(sourceMatrix.a, sourceMatrix.b, sourceMatrix.c, sourceMatrix.d, sourceMatrix.tx, sourceMatrix.ty);
		}
		
		/** Copies a Vector3D object into specific row of the calling Matrix object. */
		public function copyRowFrom(row:uint, vector3D:Vector3D) : void
		{
			throw new NotImplementedError();
		}
		
		/** Copies specific row of the calling Matrix object into the Vector3D object. The w element of the Vector3D object will not be changed. */
		public function copyRowTo(row:uint, vector3D:Vector3D) : void
		{
			throw new NotImplementedError();			
		}

		/** Includes parameters for scaling, rotation, and translation. When applied to a matrix it sets the matrix's values based on those parameters. */
		public function createBox(scaleX : Number, scaleY : Number, rotation : Number = 0, tx : Number = 0, ty : Number = 0) : void
		{
			identity();
			rotate(rotation);
			scale(scaleX, scaleY);
			translate(tx, ty);
		}

		/** Creates the specific style of matrix expected by the beginGradientFill() and lineGradientStyle() methods of the Graphics class. 
		 * Width and height are scaled to a scaleX/scaleY pair and the tx/ty values are offset by half the width and height. */
		public function createGradientBox(width : Number, height : Number, rotation : Number = 0, tx : Number = 0, ty : Number = 0) : void
		{
			throw new NotImplementedError();
		}

		/** Given a point in the pretransform coordinate space, returns the coordinates of that point after the transformation occurs. 
		 * Unlike the standard transformation applied using the transformPoint() method, the deltaTransformPoint() 
		 * method's transformation does not consider the translation parameters tx and ty. */
		public function deltaTransformPoint(point : Point) : Point
		{
			return new Point(a * point.x + c * point.y, b * point.x + d * point.y);
		}

		/** Sets each matrix property to a value that causes a null transformation. 
		 * An object transformed by applying an identity matrix will be identical to the original. */
		public function identity() : void
		{
			setTo(1, 0, 0, 1, 0, 0);
		}

		/** Performs the opposite transformation of the original matrix. You can apply an inverted 
		 * matrix to an object to undo the transformation performed when applying the original matrix. */
		public function invert() : void
		{
			var det : Number = a * d - c * b;
			if (MathHelper.epsilonZero(det))
			{
				throw new IllegalOperationError();
			}
			
			var detInv : Number = 1.0 / det;
			setTo(detInv * d, -detInv * b, -detInv * c, detInv * a, detInv * (c * ty - d * tx), -detInv * (a * ty - b * tx));
		}
		
		/** Applies a rotation transformation to the Matrix object. */
		public function rotate(angle : Number) : void
		{
			var cosA : Number = Math.cos(angle);
			var sinA : Number = Math.sin(angle);
			concatValues(cosA, sinA, -sinA, cosA, 0, 0);
		}
		
		/** Applies a scaling transformation to the matrix. The x axis is multiplied by sx, and the y axis it is multiplied by sy. */
		public function scale(sx : Number, sy : Number) : void
		{
			concatValues(sx, 0, 0, sy, 0, 0);
		}

		/** Sets the members of Matrix to the specified values */
		public function setTo(a : Number, b : Number, c : Number, d : Number, tx : Number, ty : Number) : void
		{
			this.a = a;
			this.b = b;
			this.c = c;
			this.d = d;
			this.tx = tx;
			this.ty = ty;
		}
		
		/** Returns the result of applying the geometric transformation represented by the Matrix object to the specified point. */
		public function transformPoint(point : Point) : Point
		{
			return new Point(a * point.x + c * point.y + tx, b * point.x + d * point.y + ty);
		}
		
		public function transformPointCords(x : Number, y : Number, point : Point) : void
		{
			point.x = a * x + c * y + tx;
			point.y = b * x + d * y + ty;
		}
		
		/** Translates the matrix along the x and y axes, as specified by the dx and dy parameters. */
		public function translate(dx : Number, dy : Number) : void
		{
			concatValues(1, 0, 0, 1, dx, dy);			
		}
		
		public function toString() : String 
		{
			return "(a=" + a + ", b=" + b + ", c=" + c + ", d=" + d + ", tx=" + tx + ", ty=" + ty + ")";
		}		
	}
}
