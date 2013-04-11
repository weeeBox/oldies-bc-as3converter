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
        private Bar target = new Bar();
        
        [TestMethod]
        public void TestAssignmet()
        {
            AsFunction func;
            func = target.__function("PrivateFunc");
            func = target.__function("ProtectedFunc");
            func = target.__function("PublicFunc");
            func = target.__function("Func1");
            func = target.__function("Func2");
            func = target.__function("Func3");
        }

        [TestMethod]
        public void TestCalls()
        {
            target.Clear();

            AsFunction func;
            func = target.__function("PrivateFunc");
            func.invoke();

            func = target.__function("ProtectedFunc");
            func.invoke();

            func = target.__function("PublicFunc");
            func.invoke();

            func = target.__function("Func1");
            func.invoke(10);

            func = target.__function("Func2");
            func.invoke(20, 30);

            func = target.__function("Func3");
            func.invoke(new Foo());

            func = target.__function("Func3");
            func.invoke(new Bar());

            AssertResult("PrivateFunc()", "ProtectedFunc()", "PublicFunc()", "Func1(10)", "Func2(20,30)", "Func3(Foo)", "Func3(Bar)");
        }

        [TestMethod]
        public void TestInnerFunction()
        {
            target.Clear();

            AsFunction func;
            func = new Anonymous1(delegate()
            {
                target.result.Add("delegate()");
            });
            func.invoke();

            func = new Anonymous2(delegate(int x, int y)
            {
                target.result.Add("delegate(" + x + "," + y + ")");
            });
            func.invoke(10, 20);

            AssertResult("delegate()", "delegate(10,20)");
        }

        private void AssertResult(params String[] data)
        {
            Assert.AreEqual(data.Length, target.result.Count);

            for (int i = 0; i < data.Length; ++i)
            {
                Assert.AreEqual(data[i], target.result[i]);
            }
        }
    }

    class Foo : AsObject
    {
        public List<String> result = new List<String>();

        protected void ProtectedFunc()
        {
            result.Add("ProtectedFunc()");
        }

        public void PublicFunc()
        {
            result.Add("PublicFunc()");
        }

        public void Clear()
        {
            result.Clear();
        }
    }

    class Bar : Foo 
    {
        private void PrivateFunc()
        {
            result.Add("PrivateFunc()");
        }

        private void Func1(int arg)
        {
            result.Add("Func1(" + arg + ")");
        }

        private void Func2(int arg1, int arg2)
        {
            result.Add("Func2(" + arg1 + "," + arg2 + ")");
        }

        private void Func3(Foo foo)
        {
            result.Add("Func3(" + foo.GetType().Name + ")");
        }
    }

    class Anonymous1 : AsFunction
    {
        public delegate void DelegateType();

        private DelegateType type;

        public Anonymous1(DelegateType type)
        {   
            this.type = type;
        }

        public override Object invoke()
        {
            type();
            return null;
        }
    }

    class Anonymous2 : AsFunction
    {
        public delegate void DelegateType(int x, int y);

        private DelegateType type;

        public Anonymous2(DelegateType type)
        {
            this.type = type;
        }

        public override Object invoke(params Object[] parameters)
        {
            type((int)parameters[0], (int)parameters[1]);
            return null;
        }
    }
}
