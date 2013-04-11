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
            func = new FunctionRef(target, "PrivateFunc");
            func = new FunctionRef(target, "ProtectedFunc");
            func = new FunctionRef(target, "PublicFunc");
            func = new FunctionRef(target, "Func", typeof(int));
            func = new FunctionRef(target, "Func", typeof(int), typeof(int));
            func = new FunctionRef(target, "Func", typeof(Foo));
            func = new FunctionRef(target, "Func", typeof(Bar)); // covariant param
        }

        [TestMethod]
        public void TestCalls()
        {
            target.Clear();

            AsFunction func;
            func = new FunctionRef(target, "PrivateFunc");
            func.invoke();

            func = new FunctionRef(target, "ProtectedFunc");
            func.invoke();

            func = new FunctionRef(target, "PublicFunc");
            func.invoke();

            func = new FunctionRef(target, "Func", typeof(int));
            func.invoke(10);

            func = new FunctionRef(target, "Func", typeof(int), typeof(int));
            func.invoke(20, 30);

            func = new FunctionRef(target, "Func", typeof(Foo));
            func.invoke(new Foo());

            func = new FunctionRef(target, "Func", typeof(Bar));
            func.invoke(new Bar());

            AssertResult("PrivateFunc()", "ProtectedFunc()", "PublicFunc()", "Func(10)", "Func(20,30)", "Func(Foo)", "Func(Bar)");
        }

        [TestMethod]
        public void TestInnerFunction()
        {
            target.Clear();

            AsFunction func;
            func = new Anonymous1(delegate(int x, int y)
            {

            });
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

    class Foo 
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
    }

    class Anonymous1 : AsFunction
    {
        public delegate void DelegateType(Object x, Object y);

        private DelegateType type;

        public Anonymous1(DelegateType type)
        {
            this.type = type;
        }

        public override Object apply(Object target, AsArray args)
        {
            throw new Exception("The method or operation is not implemented.");
        }

        public override Object apply(Object target, params Object[] args)
        {
            throw new Exception("The method or operation is not implemented.");
        }

        public override Object invoke()
        {
            throw new Exception("The method or operation is not implemented.");
        }

        public override Object invoke(Object param)
        {
            throw new Exception("The method or operation is not implemented.");
        }

        public override Object invoke(params Object[] parameters)
        {
            throw new Exception("The method or operation is not implemented.");
        }
    }
}
