using System;

using bc.flash;
using System.Collections.Generic;
using System.Collections;
using System.Diagnostics;

namespace bc.flash
{
    public delegate float AsVectorSorter<T>(T a, T b);

    public class AsVector<T> : AsObject, IEnumerable<T>
    {
        public List<T> internalList;

        public AsVector(params T[] elements)
        {
            internalList = new List<T>(elements.Length);
            init(elements);
        }

        public AsVector(uint length, bool _fixed)
        {
            internalList = new List<T>((int)length);
            setLength(length);
        }

        public AsVector(uint length)
            : this(length, false)
        {
        }

        public AsVector()
        {
            internalList = new List<T>();
        }

        public void init(params T[] values)
        {
            foreach (T obj in values)
            {
                internalList.Add(obj);
            }
        }

        public T this[int i]
        {
            get
            {
                Debug.Assert(i >= 0 && i < internalList.Count, i + ">=" + 0 + "&&" + i + "<" + internalList.Count);
                return internalList[i];
            }
            set
            {
                Debug.Assert(i >= 0 && i < internalList.Count, i + ">=" + 0 + "&&" + i + "<" + internalList.Count);
                internalList[i] = value;
            }
        }

        public T this[uint i]
        {
            get
            {
                Debug.Assert(i >= 0 && i < internalList.Count, i + ">=" + 0 + "&&" + i + "<" + internalList.Count);
                return internalList[(int)i];
            }
            set
            {
                Debug.Assert(i >= 0 && i < internalList.Count, i + ">=" + 0 + "&&" + i + "<" + internalList.Count);
                internalList[(int)i] = value;
            }
        }

        public virtual uint getLength()
        {
            return (uint)internalList.Count;
        }

        public virtual void setLength(uint newLength)
        {
            if (internalList.Count > newLength)
            {
                List<T> newData = new List<T>((int)newLength);
                for (int i = 0; i < newLength; ++i)
                {
                    newData.Add(internalList[i]);
                }
                internalList = newData;
            }
            else if (internalList.Count < newLength)
            {
                int diff = ((int)newLength) - internalList.Count;
                for (int i = 0; i < diff; ++i)
                {
                    internalList.Add(default(T));
                }
            }
        }

        public virtual int indexOf(T searchElement, int fromIndex)
        {
            return internalList.IndexOf(searchElement, fromIndex);
        }

        public virtual int indexOf(T searchElement)
        {
            return indexOf(searchElement, 0);
        }

        public virtual String _join(String sep)
        {
            throw new NotImplementedException();
        }

        public virtual String _join()
        {
            return _join(",");
        }

        public virtual int lastIndexOf(T searchElement, int fromIndex)
        {
            return internalList.LastIndexOf(searchElement, fromIndex);
        }

        public virtual int lastIndexOf(T searchElement)
        {
            return lastIndexOf(searchElement);
        }

        public virtual T pop()
        {
            Debug.Assert(internalList.Count > 0);
            int lastIndex = internalList.Count - 1;
            T lastElement = internalList[lastIndex];
            internalList.RemoveAt(lastIndex);
            return lastElement;
        }
        public virtual int push(T arg)
        {
            internalList.Add(arg);
            return internalList.Count;
        }
        public virtual AsVector<T> reverse()
        {
            throw new NotImplementedException();
        }
        public virtual AsVector<T> slice(int startIndex, int endIndex)
        {
            int count = endIndex - startIndex;
            if (count < 0)
            {
                throw new ArgumentOutOfRangeException();
            }
            T[] data = new T[count];
            for (int i = startIndex; i < endIndex; ++i)
            {
                data[i] = internalList[i];
            }

            return new AsVector<T>(data);
        }
        public virtual AsVector<T> slice(int startIndex)
        {
            return slice(0, (int)getLength());
        }
        public virtual AsVector<T> slice()
        {
            return new AsVector<T>(internalList.ToArray());
        }
        public AsVector<T> concat()
        {
            throw new NotImplementedException();
        }
        public virtual AsVector<T> sort(AsVectorSorter<T> sorter)
        {
            ComparationHelper comparation = new ComparationHelper(sorter);
            internalList.Sort(comparation.compare);
            return this;
        }
        public virtual AsVector<T> splice(int startIndex)
        {
            return splice(startIndex, (uint)(getLength() - startIndex));
        }

        public virtual AsVector<T> splice(int startIndex, uint deleteCount)
        {
            if (startIndex < 0)
            {
                throw new NotImplementedException();
            }

            if (deleteCount > 0)
            {
                internalList.RemoveRange(startIndex, (int)deleteCount);
            }            
            return this;
        }
        public virtual AsVector<T> splice(int startIndex, uint deleteCount, params T[] elements)
        {
            if (startIndex < 0)
            {
                throw new NotImplementedException();
            }

            if (deleteCount > 0)
            {
                internalList.RemoveRange(startIndex, (int)deleteCount);
            }

            if (elements.Length > 0)
            {
                internalList.InsertRange(startIndex, elements);
            }

            return this;
        }
        public IEnumerator<T> GetEnumerator()
        {
            return internalList.GetEnumerator();
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }

        private class ComparationHelper
        {
            private AsVectorSorter<T> sorter;

            public ComparationHelper(AsVectorSorter<T> sorter)
            {
                this.sorter = sorter;
            }

            public int compare(T x, T y)
            {
                int result = (int)sorter(x, y);
                return result;
            }
        }
    }    
}
