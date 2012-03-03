package
{
	import bc.tests.DisplayListTest;
	import bc.tests.Vector3DTest;
	import bc.tests.Matrix3DTest;
	import bc.tests.MatrixTest;
	import asunit.framework.TestSuite;
	
	public class Tests extends TestSuite
	{
		public function Tests()
		{
			addTest(new Vector3DTest());
			addTest(new MatrixTest());
			addTest(new Matrix3DTest());
			addTest(new DisplayListTest());
		}
	}
}
