public class Testing {
    public static void main(String[] args) {
	SNode p1 = new SNode("Paul", 0, null);
	SNode p2 = new SNode("Peggy", 0, null);
	SNode p3 = new SNode("Fayyad", 0, null);
	SNode p4 = new SNode("Kohava", 0, null);
	SNode p5 = new SNode("John", 0, null);
	SNode p6 = new SNode("Peter", 0, null);
	SNode p7 = new SNode("Mario", 0, null);
	SNode p8 = new SNode("Nafis", 0, null);
	SLList list = new SLList();
	list.insertNodeInOrder(p1);
	list.insertNodeInOrder(p2);
	list.insertNodeInOrder(p3);
	list.insertNodeInOrder(p4);
	list.insertNodeInOrder(p5);
	list.insertNodeInOrder(p6);
	list.insertNodeInOrder(p7);
	list.insertNodeInOrder(p8);
	System.out.println(list);
	list.removeNode(p2);
	list.removeNode(p1);
	//list.removeNode(p3);
	//list.removeNode(p4);
	list.removeNode(new SNode("hey", 0, null));
	list.removeNode(new SNode("hey", 0, null));
	list.removeNode(new SNode("hey", 0, null));
	//list.removeNode(p5);
	list.removeNode(p6);
	list.removeNode(p7);
	//list.removeNode(p8);
	System.out.println(list);
	System.out.println(list.getHead() + " " + list.getTail());
	System.out.println("Find: " + list.findNode("John"));
    }
}
