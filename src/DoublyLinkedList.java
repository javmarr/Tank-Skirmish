public class DoublyLinkedList
{
	Node head, tail;

	DoublyLinkedList()
	{
		head = null;
		tail = null;
	}

	public void add(String str, int x)
	{
		orderedInsert(str, x);
	}
	
	public void addFront(String str, int x)
	{
		Node temp = new Node(str, x);
		temp.next = head;
		if(head != null)
			temp.next.prev = temp;
		if(tail == null)
			tail = temp;
		head = temp;
	}

	public void addBack(String str, int x)
	{
		Node temp = new Node(str, x);
		if(head == null)
		{
			addFront(str, x);
		}
		else
		{
			tail.next = temp;
			temp.prev = tail;
			tail = temp;

		}
	}

	public String toString()
	{
		//System.out.println("\nCurrent items on liked list: ");
		String output = "";
		Node current = head;
		while(current.next != null)
		{
			output += current.name + " ";
			output += current.score + ", ";
			current = current.next;
		}
		output += current.name + " ";
		output += current.score;

		return output;
	}

	void orderedInsert(String str, int x)
	{
		//System.out.print("\nInserting " + x + " ");
		
		Node loc = findOrderedLoc(x);
		if(loc == null) //insert x to the back since it's too big or because the list is empty
		{
			addBack(str, x);
			//System.out.println("to the back");
		}
		else if(loc.prev == null) //insert at the front
		{
			addFront(str, x);
			//System.out.println("to the front");
		}
		else //add between two numbers
		{
			//System.out.println("somewhere in the middle");
			Node temp = new Node(str, x);
			temp.prev = loc.prev;
			loc.prev = temp;
			temp.prev.next = temp;
			temp.next = loc;
		}
		//System.out.print("done!\n");
	}

	public Node findOrderedLoc(int x) //location to be inserted in order
	{
		Node loc = null;
		Node current = head;
		
		while(current != null && loc == null)
		{
			if(current.score > x)
				loc = current;
			current = current.next;
		}
		return loc;
	}

	int findSmallestStartingFrom(Node start)
	{
		Node current = start;
		int smallestData = 9999999;//has to be bigger than the rest 
								   //of the numbers in the list
		while(current!=null)
		{
			if(current.score < smallestData)
			{
				smallestData = current.score;
			}

			current = current.next;
		}
		//System.out.println("Smallest d: " + smallestData);
		return smallestData;
	}

	Node findLoc (int x)
	{
		Node current = head;
		Node loc = null;

		while(current != null && loc == null)
		{
			if(current.score == x)
				loc = current;
			current = current.next;
		}

		return loc;
	}

	void sort()
	{
		//System.out.print("\nStarting sort...");
		Node current = head;
		Node loc = null;
		int temp, smallestData;
		while(current != null)
		{
			smallestData = findSmallestStartingFrom(current);
			if(current.score != smallestData)
			{
				//find loc of smallest number
				loc = findLoc(smallestData);
				//save the current data for swap
				temp = current.score;

				//swap
				current.score = smallestData;
				loc.score = temp;
				//----
			}

			current = current.next;
		}
	//	System.out.println("done!");
	}
//	public void sort()
//	{
//		System.out.print("\nStarting sort...");
//		Node current = head;
//		Node loc = null;
//		int temp, smallestData;
//		String strTemp;
//		while(current != null)
//		{
//			smallestData = findSmallestStartingFrom(current);
//			if(current.score != smallestData)
//			{
//				//find loc of smallest number
//				loc = findLoc(smallestData);
//				//save the current data for swap
//				temp = current.score;
//				strTemp = current.name;
//				
//				System.out.println("swapping: " + temp + " with " + loc.score );
//				//swap
//				current.score = smallestData;
//				current.name = loc.name;
//				
//				loc.score = temp;
//				loc.name = strTemp;
//				//----
//			}
//
//			current = current.next;
//		}
//		System.out.println("done!");
//
//	}
	
	public String display()
	{
		String output = "";
		Node current = head;
		//System.out.println(head.name);
		while(current != null)
		{
			output += current.name + ": " + current.score + "\n";
			current = current.next;
		}

		return output;
	}
	
	boolean remove(int x)
	{
		Node loc = findLoc(x); //finds location of the
							   //node with the number x
		
		if(loc != null) //the item was found
		{
			if(loc == head) //front of line on a multiple node list or a single item list
			{
				head = loc.next; //update the head
				if(head == null) //update the tail
					tail = null;
				else
					head.prev = null;
			}
			else if(loc == tail) //back of the line
			{
				tail = loc.prev;
				tail.next = null;
			}
			else //somewhere in the middle
			{
				loc.prev.next = loc.next;
				loc.next.prev = loc.prev;
			}
			//System.out.println("\nRemoved first instance of: " + x);
			return true;
		}
		else
		{
			//System.out.println("\nDIDN'T DELETE");
			return false; //can't remove what's not there
		}
	}

	public String displayBackwards()
	{
		//System.out.println("\nPrinting backwards: ");
		String output = "";
		Node current = head;
		
		//output = current.name + ": " + current.score + "\n" + output;
		//output = current.name + ": " + current.score + "\n";;
		//output += current.score;
		while(current != null)
		{
			//output += current.name + ": " + current.score + "\n";
			output = current.name + ": " + current.score + "\n" + output;
			current = current.next;
		}

		//System.out.println(output);
		return output;
	}
}