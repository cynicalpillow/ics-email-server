public class SLList {
    private SNode head = null;
    private SNode tail = null;
    public SLList(){
	head = null;
	tail = null;
    }
    public void insertNodeInOrder(SNode p){
	if(head == null){
	    head = p;
	    tail = p;
	} else if(head.getName().compareToIgnoreCase(p.getName()) > 0){
	    //insert before head
	    p.setNext(head);
	    head = p;
	} else {
	    SNode it = head;
	    while(it.getNext() != null && it.getNext().getName().compareToIgnoreCase(p.getName()) < 0)
		it = it.getNext();
	    if(it.getNext() == null){
		//Set tail
		it.setNext(p);
		tail = p;
	    } else {
		//insert inbetween
		p.setNext(it.getNext());
		it.setNext(p);
	    }
	}
    }
    public void insertNode(SNode p){
	if(head == null){
	    head = p;
	    tail = p;
	} else {
	    tail.setNext(p);
	    tail = p;
	}
    }
    public void updateNode(String id, String name){
	findNode(id).setName(name);
    }
    public void updateNode(String id, double mark){
	SNode it = findNode(id);
	it.setMark(mark);
    }
    public SNode findNode(String name){
	SNode it = head;
	while(it != null && !it.getName().equals(name))
	    it = it.getNext();
	return it;
    }
    public void removeNode(SNode node){
	if(head == null && tail == null){
	} else if(head == node && head == tail){
	    head = null;
	    tail = null;
	} else if(head == node){
	    head = head.getNext();
	} else {
	    SNode it = head;
	    while(it.getNext() != null && it.getNext() != node)
		it = it.getNext();
	    if(it.getNext() != null && tail == it.getNext()){
		tail = it;
		it.setNext(it.getNext().getNext());
	    } else if(it.getNext() != null){
		it.setNext(it.getNext().getNext());
	    }
	}
    }
    public SNode getHead(){
	return head;
    }
    public SNode getTail(){
	return tail;
    }
    public void setHead(SNode h){
	head = h;
    }
    public void setTail(SNode t){
	tail = t;
    }
    public String toString() {
	String s = "";
	for(SNode p = head; p != null; p = p.getNext())s += p.toString() + "\n";
	return s;
    }
}
