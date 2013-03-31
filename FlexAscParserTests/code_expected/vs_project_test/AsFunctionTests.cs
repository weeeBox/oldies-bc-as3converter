using System;
using System.Text;
using System.Collections.Generic;
using System.Linq;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using bc.flash;

namespace vs_project_test
{
    [TestClass]
    public class AsFunctionTests
    {
        private List<String> result = new List<String>();

        [TestMethod]
        public void TestAssignmet()
        {
            AsFunction func;
            func = new AsFunction(this, "PrivateFunc");
            func = new AsFunction(this, "ProtectedFunc");
            func = new AsFunction(this, "PublicFunc");
            func = new AsFunction(this, "Func", typeof(int));
            func = new AsFunction(this, "Func", typeof(int), typeof(int));
            func = new AsFunction(this, "Func", typeof(Foo));
            func = new AsFunction(this, "Func", typeof(Bar)); // covariant param
        }

        [TestMethod]
        public void TestCalls()
        {
            result.Clear();

            AsFunction func;
            func = new AsFunction(this, "PrivateFunc");
            func.Invoke();

            func = new AsFunction(this, "ProtectedFunc");
            func.Invoke();

            func = new AsFunction(this, "PublicFunc");
            func.Invoke();

            func = new AsFunction(this, "Func", typeof(int));
            func.Invoke(10);

            func = new AsFunction(this, "Func", typeof(int), typeof(int));
            func.Invoke(20, 30);

            func = new AsFunction(this, "Func", typeof(Foo));
            func.Invoke(new Foo());

            func = new AsFunction(this, "Func", typeof(Bar));
            func.Invoke(new Bar());

            AssertResult("PrivateFunc()", "ProtectedFunc()", "PublicFunc()", "Func(10)", "Func(20,30)", "Func(Foo)", "Func(Bar)");
        }

        private void PrivateFunc()
        {
            result.Add("PrivateFunc()");
        }

        protected void ProtectedFunc()
        {
            result.Add("ProtectedFunc()");
        }

        public void PublicFunc()
        {
            result.Add("PublicFunc()");
        }

        private void Func(int arg)
        {
            result.Add("Func(" + arg + ")");
        }

        private void Func(int arg1, int arg2)
        {
            result.Add("Func(" + arg1 + "," + arg2 + ")");
        }

        private void Func(Foo foo)
        {
            result.Add("Func(" + foo.GetType().Name + ")");
        }

        private void AssertResult(params String[] data)
        {
            Assert.AreEqual(data.Length, result.Count);

            for (int i = 0; i < data.Length; ++i)
            {
                Assert.AreEqual(data[i], result[i]);
            }
        }
    }

    class Foo { }
    class Bar : Foo { }
}
