package bc.tests
{
	import asunit.framework.TestCase;
	import bc.flash.utils.MathHelper;
	import bc.flash.geom.Vector3D;
	import flash.geom.Vector3D;
	
	/**
	 * @author weee
	 */
	public class Vector3DTest extends TestCase
	{
		private var fVector : flash.geom.Vector3D = new flash.geom.Vector3D(4, 3, 2, 1);
		private var bVector : bc.flash.geom.Vector3D = new bc.flash.geom.Vector3D(4, 3, 2, 1);
		
		public function testInit() : void
		{
			fVector = new flash.geom.Vector3D(4, 3, 2, 1);
			bVector = new bc.flash.geom.Vector3D(4, 3, -2, 1);
			assertEquals(vectorsEquals(), false);
			
			fVector = new flash.geom.Vector3D(4, 3, 2, 1);
			bVector = new bc.flash.geom.Vector3D(4, 3, 2, 1);
			
			assertEquals(vectorsEquals(), true);
		}
		
		public function testAdd() : void
		{
			fVector = new flash.geom.Vector3D(4, 3, 2, 1);
			bVector = new bc.flash.geom.Vector3D(4, 3, 2, 1);
			
			fVector = fVector.add(new flash.geom.Vector3D(10, 20, 30.1, 1));
			bVector = bVector.add(new bc.flash.geom.Vector3D(10, 20, 30, 1));
			
			assertEquals(vectorsEquals(), false);
			
			fVector = new flash.geom.Vector3D(4, 3, 2, 1);
			bVector = new bc.flash.geom.Vector3D(4, 3, 2, 1);
			
			fVector = fVector.add(new flash.geom.Vector3D(10, 20, 30, 1));
			bVector = bVector.add(new bc.flash.geom.Vector3D(10, 20, 30, 1));
			
			assertEquals(vectorsEquals(), true);
		}
		
		public function testAngleBetween1() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = flash.geom.Vector3D.angleBetween(fVector, new flash.geom.Vector3D(10.01, 20.01, 30.01));
			var ba : Number = bc.flash.geom.Vector3D.angleBetween(bVector, new bc.flash.geom.Vector3D(10, 20, 30));
			
			assertEquals(equalsEpsilon(fa, ba), false);
			
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			fa = flash.geom.Vector3D.angleBetween(fVector, new flash.geom.Vector3D(10, 20, 30));
			ba = bc.flash.geom.Vector3D.angleBetween(bVector, new bc.flash.geom.Vector3D(10, 20, 30));
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		public function testAngleBetween2() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = flash.geom.Vector3D.angleBetween(fVector, new flash.geom.Vector3D(-0.1, -0.07, 2.001));
			var ba : Number = bc.flash.geom.Vector3D.angleBetween(bVector, new bc.flash.geom.Vector3D(-0.1, -0.07, 2));
			
			assertEquals(equalsEpsilon(fa, ba), false);
			
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			fa = flash.geom.Vector3D.angleBetween(fVector, new flash.geom.Vector3D(-0.1, -0.07, 2));
			ba = bc.flash.geom.Vector3D.angleBetween(bVector, new bc.flash.geom.Vector3D(-0.1, -0.07, 2));
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		public function testClone() : void
		{
			fVector = fVector.clone();
			bVector = bVector.clone();
			
			assertEquals(vectorsEquals(), true);
		}
		
		public function testCopyFrom() : void
		{
			fVector.copyFrom(fVector);
			bVector.copyFrom(bVector);
			
			assertEquals(vectorsEquals(), true);
		}
		
		public function testCross() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			fVector.w = bVector.w = 1;
			
			fVector = fVector.crossProduct(new flash.geom.Vector3D(0.47, 1.25, 8.43, 1));
			bVector = bVector.crossProduct(new bc.flash.geom.Vector3D(0.47, 1.25, 8.43, 1));
			
			assertEquals(vectorsEquals(), true);
		}
		
		public function testDecrementBy() : void
		{
			fVector.decrementBy(new flash.geom.Vector3D(0.47, 1.25, 8.43, 0));
			bVector.decrementBy(new bc.flash.geom.Vector3D(0.47, 1.25, 8.43, 0));
			
			assertEquals(vectorsEquals(), true);
		}
		
		public function testDistance() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = flash.geom.Vector3D.distance(fVector, new flash.geom.Vector3D(0.17, 15, 0));
			var ba : Number = bc.flash.geom.Vector3D.distance(bVector, new bc.flash.geom.Vector3D(0.17, 15, 0));
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		public function testDot() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = fVector.dotProduct(new flash.geom.Vector3D(0.17, 15, 0));
			var ba : Number = bVector.dotProduct(new bc.flash.geom.Vector3D(0.17, 15, 0));
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		public function testLenght() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = fVector.length;
			var ba : Number = bVector.length;
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		public function testLenghtSquared() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = fVector.lengthSquared;
			var ba : Number = bVector.lengthSquared;
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		public function testNearEquals() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = fVector.lengthSquared;
			var ba : Number = bVector.lengthSquared;
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		private function vectorsEquals() : Boolean
		{
			return equalsEpsilon(fVector.x, bVector.x) && equalsEpsilon(fVector.y, bVector.y) && equalsEpsilon(fVector.z, bVector.z) && equalsEpsilon(fVector.w, bVector.w);
		}
		
		private function equalsEpsilon(a : Number, b : Number) : Boolean
		{
			return MathHelper.epsilonEquals(a, b);
		}
	}
}
