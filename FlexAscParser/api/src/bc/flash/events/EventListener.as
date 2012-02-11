package bc.flash.events
{
	/**
	 * @author weee
	 */
	public class EventListener
	{
		private var m_listener : Function;
		private var m_useCapture : Boolean;
		private var m_priority : int;
		
		public function EventListener(listener : Function, useCapture : Boolean = false, priority : int = 0)
		{
			m_listener = listener;
			m_useCapture = useCapture;
			m_priority = priority;			
		}
		
		public function get listener() : Function
		{
			return m_listener;
		}
		
		public function get useCapture() : Boolean
		{
			return m_useCapture;
		}
		
		public function get priority() : int
		{
			return m_priority;
		}
	}
}
