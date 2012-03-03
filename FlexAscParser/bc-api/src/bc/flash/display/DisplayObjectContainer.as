package bc.flash.display
{
    import flash.utils.getQualifiedClassName;
    import bc.flash.geom.Matrix;
    import bc.flash.utils.transformCoords;
    import bc.flash.core.RenderSupport;
    import bc.flash.error.AbstractClassError;
    import bc.flash.events.Event;
    import bc.flash.geom.Point;
    import bc.flash.geom.Rectangle;

    public class DisplayObjectContainer extends InteractiveObject
    {
        // members
        private var mChildren : Vector.<DisplayObject>;
        /** Helper objects. */
        private static var sHelperMatrix : Matrix = new Matrix();
        private static var sHelperPoint : Point = new Point();
        
        /** @private */
        public function DisplayObjectContainer()
        {
            if (getQualifiedClassName(this) == "DisplayObjectContainer")
                throw new AbstractClassError();            
            
            mChildren = new Vector.<DisplayObject>();
        }
        
        /** Disposes the resources of all children. */
        public override function dispose():void
        {
            var numChildren:int = mChildren.length;
            
            for (var i:int=0; i<numChildren; ++i)
                mChildren[i].dispose();
                
            super.dispose();
        }
        
        // child management
        
        /** Adds a child to the container. It will be at the frontmost position. */
        public function addChild(child:DisplayObject):void
        {
            addChildAt(child, numChildren);
        }
        
        /** Adds a child to the container at a certain index. */
        public function addChildAt(child:DisplayObject, index:int):void
        {
            if (index >= 0 && index <= numChildren)
            {
                child.removeFromParent();
                mChildren.splice(index, 0, child);
                child.setParent(this);                
                child.dispatchEvent(new Event(Event.ADDED, true));
                if (stage) child.dispatchEventOnChildren(new Event(Event.ADDED_TO_STAGE));
            }
            else
            {
                throw new RangeError("Invalid child index");
            }
        }
        
        /** Removes a child from the container. If the object is not a child, nothing happens. 
         *  If requested, the child will be disposed right away. */
        public function removeChild(child:DisplayObject, dispose:Boolean=false):void
        {
            var childIndex:int = getChildIndex(child);
            if (childIndex != -1) removeChildAt(childIndex, dispose);
        }
        
        /** Removes a child at a certain index. Children above the child will move down. If
         *  requested, the child will be disposed right away. */
        public function removeChildAt(index:int, dispose:Boolean=false):void
        {
            if (index >= 0 && index < numChildren)
            {
                var child:DisplayObject = mChildren[index];
                child.dispatchEvent(new Event(Event.REMOVED, true));
                if (stage) child.dispatchEventOnChildren(new Event(Event.REMOVED_FROM_STAGE));
                child.setParent(null);
                mChildren.splice(index, 1);
                if (dispose) child.dispose();
            }
            else
            {
                throw new RangeError("Invalid child index");
            }
        }
        
        /** Removes a range of children from the container (endIndex included). 
         *  If no arguments are given, all children will be removed. */
        public function removeChildren(beginIndex:int=0, endIndex:int=-1, dispose:Boolean=false):void
        {
            if (endIndex < 0 || endIndex >= numChildren) 
                endIndex = numChildren - 1;
            
            for (var i:int=beginIndex; i<=endIndex; ++i)
                removeChildAt(beginIndex, dispose);
        }
        
        /** Returns a child object at a certain index. */
        public function getChildAt(index:int):DisplayObject
        {
            if (index >= 0 && index < numChildren)
                return mChildren[index];
            else
                throw new RangeError("Invalid child index");
        }
        
        /** Returns a child object with a certain name (non-recursively). */
        public function getChildByName(name:String):DisplayObject
        {
            var numChildren:int = mChildren.length;
            for (var i:int=0; i<numChildren; ++i)
                if (mChildren[i].name == name) return mChildren[i];

            return null;
        }
        
        /** Returns the index of a child within the container, or "-1" if it is not found. */
        public function getChildIndex(child:DisplayObject):int
        {
            return mChildren.indexOf(child);
        }
        
        /** Moves a child to a certain index. Children at and after the replaced position move up.*/
        public function setChildIndex(child:DisplayObject, index:int):void
        {
            var oldIndex:int = getChildIndex(child);
            if (oldIndex == -1) throw new ArgumentError("Not a child of this container");
            mChildren.splice(oldIndex, 1);
            mChildren.splice(index, 0, child);
        }
        
        /** Swaps the indexes of two children. */
        public function swapChildren(child1:DisplayObject, child2:DisplayObject):void
        {
            var index1:int = getChildIndex(child1);
            var index2:int = getChildIndex(child2);
            if (index1 == -1 || index2 == -1) throw new ArgumentError("Not a child of this container");
            swapChildrenAt(index1, index2);
        }
        
        /** Swaps the indexes of two children. */
        public function swapChildrenAt(index1:int, index2:int):void
        {
            var child1:DisplayObject = getChildAt(index1);
            var child2:DisplayObject = getChildAt(index2);
            mChildren[index1] = child2;
            mChildren[index2] = child1;
        }
        
        /** Determines if a certain object is a child of the container (recursively). */
        public function contains(child:DisplayObject):Boolean
        {
            if (child == this) return true;
            
            var numChildren:int = mChildren.length;
            for (var i:int=0; i<numChildren; ++i)
            {
                var currentChild:DisplayObject = mChildren[i];
                var currentChildContainer:DisplayObjectContainer = currentChild as DisplayObjectContainer;
                
                if (currentChildContainer && currentChildContainer.contains(child)) return true;
                else if (currentChild == child) return true;
            }
            
            return false;
        }
        
        /** @inheritDoc */ 
        public override function getBounds(targetSpace:DisplayObject, resultRect:Rectangle=null):Rectangle
        {
            if (resultRect == null) resultRect = new Rectangle();
            
            var numChildren:int = mChildren.length;
            
            if (numChildren == 0)
            {
                getTransformationMatrix(targetSpace, sHelperMatrix);
                transformCoords(sHelperMatrix, 0.0, 0.0, sHelperPoint);
                
                resultRect.x = sHelperPoint.x;
                resultRect.y = sHelperPoint.y;
                resultRect.width = resultRect.height = 0;
                
                return resultRect;
            }
            else if (numChildren == 1)
            {
                return mChildren[0].getBounds(targetSpace, resultRect);
            }
            else
            {
                var minX:Number = Number.MAX_VALUE, maxX:Number = -Number.MAX_VALUE;
                var minY:Number = Number.MAX_VALUE, maxY:Number = -Number.MAX_VALUE;
                
                for (var i:int=0; i<numChildren; ++i)
                {
                    mChildren[i].getBounds(targetSpace, resultRect);
                    minX = minX < resultRect.x ? minX : resultRect.x;
                    maxX = maxX > resultRect.right ? maxX : resultRect.right;
                    minY = minY < resultRect.y ? minY : resultRect.y;
                    maxY = maxY > resultRect.bottom ? maxY : resultRect.bottom;
                }
                
                resultRect.x = minX;
                resultRect.y = minY;
                resultRect.width  = maxX - minX;
                resultRect.height = maxY - minY;
                
                return resultRect;
            }                
        }
        
        /** @inheritDoc */
        public override function hitTest(localPoint:Point, forTouch:Boolean=false):DisplayObject
        {
            if (forTouch && (!visible || !touchable))
                return null;
            
            var localX:Number = localPoint.x;
            var localY:Number = localPoint.y;
            
            var numChildren:int = mChildren.length;
            for (var i:int=numChildren-1; i>=0; --i) // front to back!
            {
                var child:DisplayObject = mChildren[i];
                getTransformationMatrix(child, sHelperMatrix);
                
                transformCoords(sHelperMatrix, localX, localY, sHelperPoint);
                var target:DisplayObject = child.hitTest(sHelperPoint, forTouch);
                
                if (target) return target;
            }
            
            return null;
        }
        
        /** @inheritDoc */
        public override function render(support:RenderSupport, alpha:Number):void
        {
            alpha *= this.alpha;
            var numChildren:int = mChildren.length;
            
            for (var i:int=0; i<numChildren; ++i)
            {
                var child:DisplayObject = mChildren[i];
                if (child.alpha != 0.0 && child.visible && child.scaleX != 0.0 && child.scaleY != 0.0)
                {
                    support.pushMatrix();
                    support.transform(child.transform.matrix);
                    child.render(support, alpha);
                    support.popMatrix();
                }
            }
        }
        
        /** Dispatches an event on all children (recursively). The event must not bubble. */
        public function broadcastEvent(event:Event):void
        {
            if (event.bubbles) 
                throw new ArgumentError("Broadcast of bubbling events is prohibited");
            
            dispatchEventOnChildren(event);
        }
        
        /** @private */
        internal override function dispatchEventOnChildren(event:Event):void 
        { 
            // the event listeners might modify the display tree, which could make the loop crash. 
            // thus, we collect them in a list and iterate over that list instead.
            
            var listeners:Vector.<DisplayObject> = new <DisplayObject>[];
            getChildEventListeners(this, event.type, listeners);
            var numListeners:int = listeners.length;
            
            for (var i:int=0; i<numListeners; ++i)
                listeners[i].dispatchEvent(event);
        }
        
        private function getChildEventListeners(object:DisplayObject, eventType:String, 
                                                listeners:Vector.<DisplayObject>):void
        {
            var container:DisplayObjectContainer = object as DisplayObjectContainer;
            
            if (object.hasEventListener(eventType))
                listeners.push(object);
            
            if (container)
            {
                var children:Vector.<DisplayObject> = container.mChildren;
                var numChildren:int = children.length;
                
                for (var i:int=0; i<numChildren; ++i)
                    getChildEventListeners(children[i], eventType, listeners);
            }
        }
        
        /** The number of children of this container. */
        public function get numChildren():int { return mChildren.length; }        
    }
}
