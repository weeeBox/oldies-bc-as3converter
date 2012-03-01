package bc.tests
{
	import asunit.framework.TestCase;

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
			assertEquals(vectorsEquals(), true);
		}
		
		public function testAdd() : void
		{
			fVector.add(new flash.geom.Vector3D(10, 20, 30, 1));
			bVector.add(new bc.flash.geom.Vector3D(10, 20, 30, 1));
			assertEquals(vectorsEquals(), true);
		}
		
		public function testAngleBetween1() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = flash.geom.Vector3D.angleBetween(fVector, new flash.geom.Vector3D(10, 20, 30));
			var ba : Number = bc.flash.geom.Vector3D.angleBetween(bVector, new bc.flash.geom.Vector3D(10, 20, 30));
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		public function testAngleBetween2() : void
		{
			fVector.x = bVector.x = 0.1;
			fVector.y = bVector.y = -0.07;
			fVector.z = bVector.z = 2;
			
			var fa : Number = flash.geom.Vector3D.angleBetween(fVector, new flash.geom.Vector3D(-0.1, -0.07, 2));
			var ba : Number = bc.flash.geom.Vector3D.angleBetween(bVector, new bc.flash.geom.Vector3D(-0.1, -0.07, 2));
			
			assertEquals(equalsEpsilon(fa, ba), true);
		}
		
		private function vectorsEquals() : Boolean
		{
			return equalsEpsilon(fVector.x, bVector.x) && equalsEpsilon(fVector.y, bVector.y) && equalsEpsilon(fVector.z, bVector.z); 
		}
		
		private function equalsEpsilon(a : Number, b : Number) : Boolean
		{
			return Math.abs(a - b) < 0.0000001;
		}
	}
}
