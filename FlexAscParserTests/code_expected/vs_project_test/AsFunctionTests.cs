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
        }

        private void PrivateFunc()
        {
        }

        protected void ProtectedFunc()
        {
        }

        public void PublicFunc()
        {
        }

        private void Func(int arg)
        {
        }

        private void Func(int arg1, int arg2)
        {
        }

        private void Func(Foo foo)
        {
        }
    }

    class Foo
    {
    }
}
