package bc.flash.text
{
	import bc.flash.geom.Rectangle;
	import bc.flash.errors.NotImplementedError;
	import bc.flash.display.InteractiveObject;
	import bc.flash.display.DisplayObject;

	/**
	 * @author weee
	 */
	public class TextField extends InteractiveObject
	{
		public function get alwaysShowSelection() : Boolean
		{			
			throw new NotImplementedError();
		}

		public function set alwaysShowSelection(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public function get antiAliasType() : String
		{
			throw new NotImplementedError();
		}

		public function set antiAliasType(antiAliasType : String) : void
		{
			throw new NotImplementedError();
		}

		public function appendText(newText : String) : void
		{
			throw new NotImplementedError();
		}

		public function get autoSize() : String
		{
			throw new NotImplementedError();
		}

		public function set autoSize(value : String) : void
		{
			throw new NotImplementedError();
		}

		public function get background() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set background(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public function get backgroundColor() : uint
		{
			throw new NotImplementedError();
		}

		public function set backgroundColor(value : uint) : void
		{
			throw new NotImplementedError();
		}

		public function get border() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set border(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public function get borderColor() : uint
		{
			throw new NotImplementedError();
		}

		public function set borderColor(value : uint) : void
		{
			throw new NotImplementedError();
		}

		public function get bottomScrollV() : int
		{
			throw new NotImplementedError();
		}

		public function get caretIndex() : int
		{
			throw new NotImplementedError();
		}

		public function get condenseWhite() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set condenseWhite(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		internal function copyRichText() : String
		{
			throw new NotImplementedError();
		}

		public function get defaultTextFormat() : TextFormat
		{
			throw new NotImplementedError();
		}

		public function set defaultTextFormat(format : TextFormat) : void
		{
			throw new NotImplementedError();
		}

		public function get displayAsPassword() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set displayAsPassword(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public function get embedFonts() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set embedFonts(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public function getCharBoundaries(charIndex : int) : Rectangle
		{
			throw new NotImplementedError();
		}

		public function getCharIndexAtPoint(x : Number, y : Number) : int
		{
			throw new NotImplementedError();
		}

		public function getFirstCharInParagraph(charIndex : int) : int
		{
			throw new NotImplementedError();
		}

		public function getImageReference(id : String) : DisplayObject
		{
			throw new NotImplementedError();
		}

		public function getLineIndexAtPoint(x : Number, y : Number) : int
		{
			throw new NotImplementedError();
		}

		public function getLineIndexOfChar(charIndex : int) : int
		{
			throw new NotImplementedError();
		}

		public function getLineLength(lineIndex : int) : int
		{
			throw new NotImplementedError();
		}

		public function getLineMetrics(lineIndex : int) : TextLineMetrics
		{
			throw new NotImplementedError();
		}

		public function getLineOffset(lineIndex : int) : int
		{
			throw new NotImplementedError();
		}

		public function getLineText(lineIndex : int) : String
		{
			throw new NotImplementedError();
		}

		public function getParagraphLength(charIndex : int) : int
		{
			throw new NotImplementedError();
		}

		[Inspectable(environment="none")]
		public function getRawText() : String
		{
			throw new NotImplementedError();
		}

		public function getTextFormat(beginIndex : int = -1, endIndex : int = -1) : TextFormat
		{
			throw new NotImplementedError();
		}

		[Inspectable(environment="none")]
		public function getTextRuns(beginIndex : int = 0, endIndex : int = 2147483647) : Array
		{
			throw new NotImplementedError();
		}

		[Inspectable(environment="none")]
		public function getXMLText(beginIndex : int = 0, endIndex : int = 2147483647) : String
		{
			throw new NotImplementedError();
		}

		public function get gridFitType() : String
		{
			throw new NotImplementedError();
		}

		public function set gridFitType(gridFitType : String) : void
		{
			throw new NotImplementedError();
		}

		public function get htmlText() : String
		{
			throw new NotImplementedError();
		}

		public function set htmlText(value : String) : void
		{
			throw new NotImplementedError();
		}

		[Inspectable(environment="none")]
		public function insertXMLText(beginIndex : int, endIndex : int, richText : String, pasting : Boolean = false) : void
		{
			throw new NotImplementedError();
		}

		[Version("10")]
		public static function isFontCompatible(fontName : String, fontStyle : String) : Boolean
		{
			throw new NotImplementedError();
		}

		public function get length() : int
		{
			throw new NotImplementedError();
		}

		public function get maxChars() : int
		{
			throw new NotImplementedError();
		}

		public function set maxChars(value : int) : void
		{
			throw new NotImplementedError();
		}

		public function get maxScrollH() : int
		{
			throw new NotImplementedError();
		}

		public function get maxScrollV() : int
		{
			throw new NotImplementedError();
		}

		public function get mouseWheelEnabled() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set mouseWheelEnabled(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public function get multiline() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set multiline(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public function get numLines() : int
		{
			throw new NotImplementedError();
		}

		internal function pasteRichText(richText : String) : Boolean
		{
			throw new NotImplementedError();
		}

		public function replaceSelectedText(value : String) : void
		{
			throw new NotImplementedError();
		}

		public function replaceText(beginIndex : int, endIndex : int, newText : String) : void
		{
			throw new NotImplementedError();
		}

		public function get restrict() : String
		{
			throw new NotImplementedError();
		}

		public function set restrict(value : String) : void
		{
			throw new NotImplementedError();
		}

		public function get scrollH() : int
		{
			throw new NotImplementedError();
		}

		public function set scrollH(value : int) : void
		{
			throw new NotImplementedError();
		}

		public function get scrollV() : int
		{
			throw new NotImplementedError();
		}

		public function set scrollV(value : int) : void
		{
			throw new NotImplementedError();
		}

		public function get selectable() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set selectable(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		[Inspectable(environment="none")]
		public function get selectedText() : String
		{
			throw new NotImplementedError();
		}

		public function get selectionBeginIndex() : int
		{
			throw new NotImplementedError();
		}

		public function get selectionEndIndex() : int
		{
			throw new NotImplementedError();
		}

		public function setSelection(beginIndex : int, endIndex : int) : void
		{
			throw new NotImplementedError();
		}

		public function setTextFormat(format : TextFormat, beginIndex : int = -1, endIndex : int = -1) : void
		{
			throw new NotImplementedError();
		}

		public function get sharpness() : Number
		{
			throw new NotImplementedError();
		}

		public function set sharpness(value : Number) : void
		{
			throw new NotImplementedError();
		}

//		public function get styleSheet() : StyleSheet
//		{
//			throw new NotImplementedError();
//		}
//
//		public function set styleSheet(value : StyleSheet) : void
//		{
//			throw new NotImplementedError();
//		}

		public function get text() : String
		{
			throw new NotImplementedError();
		}

		public function set text(value : String) : void
		{
			throw new NotImplementedError();
		}

		public function get textColor() : uint
		{
			throw new NotImplementedError();
		}

		public function set textColor(value : uint) : void
		{
			throw new NotImplementedError();
		}

		public function get textHeight() : Number
		{
			throw new NotImplementedError();
		}

		[API("670")]
		public function get textInteractionMode() : String
		{
			throw new NotImplementedError();
		}

		public function get textWidth() : Number
		{
			throw new NotImplementedError();
		}

		public function get thickness() : Number
		{
			throw new NotImplementedError();
		}

		public function set thickness(value : Number) : void
		{
			throw new NotImplementedError();
		}

		public function get type() : String
		{
			throw new NotImplementedError();
		}

		public function set type(value : String) : void
		{
			throw new NotImplementedError();
		}

		public function get useRichTextClipboard() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set useRichTextClipboard(value : Boolean) : void
		{
			throw new NotImplementedError();
		}

		public function get wordWrap() : Boolean
		{
			throw new NotImplementedError();
		}

		public function set wordWrap(value : Boolean) : void
		{
			throw new NotImplementedError();
		}
	}
}
