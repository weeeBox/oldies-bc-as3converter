using System;

using bc.flash;
using bc.test.objects;

namespace bc.test.objects
{
    public class AsObjectTypeTest : AsObject
    {
        public virtual void testNullAssignemnt()
        {
            Object obj = null;
        }
        public virtual void testLiteralObject()
        {
            Object obj = AsObject.createLiteralObject("a", "This is string", "b", 10);
            String a = (String)(obj.getOwnProperty("a"));
            int b = (int)(obj.getOwnProperty("b"));
            obj.setOwnProperty("a", a);
            obj.setOwnProperty("b", b);
        }
        public virtual void testLiteralObject2()
        {
            Object obj = AsObject.createLiteralObject("a", "This is string", "b", 10);
            String a = (String)(obj.getOwnProperty("a"));
            int b = (int)(obj.getOwnProperty("b"));
            obj.setOwnProperty("a", a);
            obj.setOwnProperty("b", b);
        }
        public virtual void testLiteralObject3()
        {
            Object obj = null;
            obj = AsObject.createLiteralObject("a", "This is string", "b", 10);
            String a = (String)(obj.getOwnProperty("a"));
            int b = (int)(obj.getOwnProperty("b"));
            obj.setOwnProperty("a", a);
            obj.setOwnProperty("b", b);
        }
        public virtual void testLiteralObject4()
        {
            Object obj = null;
            obj = AsObject.createLiteralObject("a", "This is string", "b", 10);
            String a = (String)(obj.getOwnProperty("a"));
            int b = (int)(obj.getOwnProperty("b"));
            obj.setOwnProperty("a", a);
            obj.setOwnProperty("b", b);
        }
        public virtual void testObjectAsDictionary1()
        {
            String a = "This is string";
            int b = 10;
            Object foo = new AsObject();
            foo.setOwnProperty("a", a);
            foo.setOwnProperty("b", b);
            a = (String)(foo.getOwnProperty("a"));
            b = (int)(foo.getOwnProperty("b"));
        }
        public virtual void testObjectAsDictionary2()
        {
            String a = "This is string";
            int b = 10;
            AsFooObjectClass foo = new AsFooObjectClass();
            foo.setOwnProperty("a", a);
            foo.setOwnProperty("b", b);
            a = (String)(foo.getOwnProperty("a"));
            b = (int)(foo.getOwnProperty("b"));
        }
        public virtual void testBasicAssignment()
        {
            Object obj = null;
            uint uinteger = (uint)(10);
            int integer = -10;
            float number = 3.14f;
            bool boolean = true;
            obj = uinteger;
            obj = integer;
            obj = number;
            obj = boolean;
        }
        public virtual void testMovieClipCall()
        {
            AsMovieClip mc = null;
            AsMovieClip mc1 = mc._("childClip");
            AsMovieClip mc2 = mc._("childClip")._("anotherClip");
            mc._("childClip")._("anotherClip").play();
        }
    }
}