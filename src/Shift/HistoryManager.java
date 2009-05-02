package Shift;

/**
 * This structure is ment to keep track of the history of the player
 * it isn't fully tested yet, so for the time being I'll use an ArrayList like a stack
 * this is here to make sure that it takes constant time to limit the size of the stack by removing from the front
 * to remove off the front of an ArrayList takes linear time, which is slow
 * After some general experimentation this seems to work correctly, so it's being used.
 * @author mattcrain
 *
 */
public class HistoryManager 
{
	private final int MAX_SIZE = 1000;
	
	class Node
	{
		PlayerHistory data;
		Node next, prev;
		public Node(PlayerHistory data, Node next, Node prev)
		{
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
	
	private Node head;
	private Node tail;
	private int size;
	
	public HistoryManager()
	{
		head = null;
		tail = null;
		size = 0;
	}
	
	public boolean isEmpty()
	{
		return size == 0;
	}
	
	public int size()
	{
		return size;
	}
	
	public void add(PlayerHistory timeStamp)
	{
		if(head == null && tail == null)
		{
			Node newData = new Node(timeStamp, null, null);
			head = newData;
			tail = newData;
			size++;
		}
		else
		{
			if(size == 1)
			{
				Node newData = new Node(timeStamp, head, head);
				tail = newData;
				head.next = tail;
				head.prev = tail;
			}
			else
			{
				Node newData = new Node(timeStamp, head, tail);
				tail.next = newData;
				tail = newData;
			}
			size++;
		}
		if(size > MAX_SIZE)
		{
			tail.next = head.next;
			head.next.prev = tail;
			head = head.next;
			size--;
		}
	}
	
	public PlayerHistory remove() throws HistoryEmptyException
	{
		PlayerHistory data;
		if(isEmpty())
		{
			throw new HistoryEmptyException();
		}
		if(size == 1)
		{
			data = head.data;
			head = null;
			tail = null;
		}
		else
		{
			data = tail.data;
			head.prev = tail.prev;
			tail.prev.next = head;
			tail = tail.prev;
		}
		size--;
		return data;
	}
}


