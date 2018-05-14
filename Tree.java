import java.io.*;
public class Tree {
    private TNode root = null;
    
    public Tree(){
	root = null;
    }
    public Tree(TNode r){
	root = r;
    }
    public TNode getRoot(){
	return root;
    }
    public void setRoot(TNode r){
	root = r;
    }
    public void updateNodeHeight(TNode p){
	int leftHeight = p.getLeft() != null ? p.getLeft().getHeight() : 0;
	int rightHeight = p.getRight() != null ? p.getRight().getHeight() : 0;
	p.setHeight(Math.max(leftHeight, rightHeight)+1);
    }
    public void updateTreeHeights(){
	if(root.getLeft() != null){
	    Tree leftTree = new Tree(root.getLeft());
	    leftTree.updateTreeHeights();
	}
	if(root.getRight() != null){
	    Tree rightTree = new Tree(root.getRight());
	    rightTree.updateTreeHeights();
	}
	updateNodeHeight(root);
    }
    public void insertNode(TNode p){
	insertNodeRec(p);
	balance(p);
    }
    public void insertNodeRec(TNode t){
	if(root == null){
	    root = t;
	} else if(t.getId().compareTo(root.getId()) < 0){
	    if(root.getLeft() == null){
		root.setLeft(t);
		t.setParent(root);
	    } else {
		Tree tr = new Tree(root.getLeft());
		tr.insertNodeRec(t);
	    }
	} else if(t.getId().compareTo(root.getId()) > 0){
	    if(root.getRight() == null){
		root.setRight(t);
		t.setParent(root);
	    } else {
		Tree tr = new Tree(root.getRight());
		tr.insertNodeRec(t);
	    }
	} else {
	    System.out.println("Attempting to insert an identification that already exists in tree");
	}
	
	updateNodeHeight(root);
    }
    
    private int balanceFactor() {
	return ((root.getLeft()==null?0:root.getLeft().getHeight())-(root.getRight()==null?0:root.getRight().getHeight()));
    }
    // balance the tree starting at this according to AVL self-balancing algorithm
    // if called after insertion, p is the node that has just been inserted
    // if called after deletion, p will have to be one of these two:
    //
    //  1) a leaf that has been deleted: In the calling method, the passed 
    //     parameter will still have the link information even if the node has been
    //     deleted in deleteNode(); thus it can be safely passed into here
    //  2) the node that has been shifted up and has taken q's place since the parent of this node may
    //     experience unbalancing
    //
    //  It might be best to do the call to balance() from within the insertNode() and
    //  the deleteNode() so that all this node information is available

    private void balance(TNode p) { 
	if (p != null) {
	    TNode ancestor = p;
	
	    while (ancestor != null) {
		Tree ancestorTree = new Tree(ancestor); 
		if (ancestorTree.balanceFactor() == -2) {
		    Tree rTree = new Tree(ancestorTree.root.getRight()); 
		    int rTreeBalanceFactor = rTree.balanceFactor();
		    if (rTreeBalanceFactor == -1 || rTreeBalanceFactor == 0) { //0 happens in delete case 7a
			if (ancestor == root){
			    root = ancestorTree.leftRotate();
			    updateTreeHeights();
			} else { 

			    // determine if the ancestor is a left or a right child

			    if (ancestor.getParent().getLeft() == ancestor)
				ancestor.getParent().setLeft(ancestorTree.leftRotate()); 
			    else
				ancestor.getParent().setRight(ancestorTree.leftRotate());
				
			    ancestorTree.updateTreeHeights();
			    for(TNode q = ancestor.getParent(); q != null; q = q.getParent()){
				updateNodeHeight(q);
			    }
			}
		    }
		    else if (rTreeBalanceFactor == 1 || rTreeBalanceFactor == 0) {
			ancestor.setRight(rTree.rightRotate());
			rTree.updateTreeHeights();
			if (ancestor == root){
			    root = ancestorTree.leftRotate();
			    updateTreeHeights();
			} else {

			    // determine if the ancestor is a left or a right child

			    if (ancestor.getParent().getLeft() == ancestor)
				ancestor.getParent().setLeft(ancestorTree.leftRotate());
			    else
				ancestor.getParent().setRight(ancestorTree.leftRotate());
			
			    ancestorTree.updateTreeHeights();
			    for(TNode q = ancestor.getParent(); q != null; q = q.getParent()){
				updateNodeHeight(q);
			    }
			}
		    }
		}
		else if (ancestorTree.balanceFactor() == 2) {
		    Tree lTree = new Tree(ancestorTree.root.getLeft());
		    int lTreeBalanceFactor = lTree.balanceFactor();
		    if (lTreeBalanceFactor == 1 || lTreeBalanceFactor == 0) { // 0 this symmetrical case of 7a does not happen. it's here to make the method symmetric and possible future optimization
		       if (ancestor == root){
			   root = ancestorTree.rightRotate();
			   updateTreeHeights();
		       } else {

			   // determine if the ancestor is a left or a right child

			   if (ancestor.getParent().getLeft() == ancestor)
			       ancestor.getParent().setLeft(ancestorTree.rightRotate());
			   else
			       ancestor.getParent().setRight(ancestorTree.rightRotate());
		       
			   ancestorTree.updateTreeHeights();
			   for(TNode q = ancestor.getParent(); q != null; q = q.getParent()){
			       updateNodeHeight(q);
			   }
		       }
		    }
		    else if (lTreeBalanceFactor == -1 || lTreeBalanceFactor == 0) {
			ancestor.setLeft(lTree.leftRotate());
			lTree.updateTreeHeights();
			if (ancestor == root){
			    root = ancestorTree.rightRotate();
			    updateTreeHeights();
			} else {

			    // determine if the ancestor is a left or a right child

			    if (ancestor.getParent().getLeft() == ancestor)
				ancestor.getParent().setLeft(ancestorTree.rightRotate());
			    else
				ancestor.getParent().setRight(ancestorTree.rightRotate());
			
			    ancestorTree.updateTreeHeights();
			    for(TNode q = ancestor.getParent(); q != null; q = q.getParent()){
				updateNodeHeight(q);
			    }    
			}
		    }
		}
		ancestor = ancestor.getParent(); // ancestor may have changed in rotation. ancestor keeps moving up anyway and will reach the null of the root's parent
	    }
	}
    }
    
    private TNode rightRotate(){
	TNode p = root.getLeft();
	p.setParent(root.getParent());
	
	root.setLeft(p.getRight());
	
	if(root.getLeft() != null)
	    root.getLeft().setParent(root);
	    
	p.setRight(root);
	p.getRight().setParent(p);
	
	return p;
    }
    private TNode leftRotate(){
	TNode p = root.getRight();
	p.setParent(root.getParent());
	
	root.setRight(p.getLeft());
	
	if(root.getRight() != null)
	    root.getRight().setParent(root);
	    
	p.setLeft(root);
	p.getLeft().setParent(p);
	
	return p;
    }
    public void printTree() {
	if(root != null){
	    Tree t = new Tree(root.getLeft());
	    t.printTree();
	    System.out.println(root + " with height " + root.getHeight());
	    t = new Tree(root.getRight());
	    t.printTree();
	}
    }
    public String toString(){
	if(root == null)
	    return "Empty tree";
	else
	    return "Root Id: " + root.getId();
    }
    public TNode findNode(String id){
	if(root == null){
	    return null;
	} else if(id.equals(root.getId())){
	    return root;
	} else if(id.compareTo(root.getId()) < 0){
	    Tree t = new Tree(root.getLeft());
	    return t.findNode(id);
	} else if(id.compareTo(root.getId()) > 0){
	    Tree t = new Tree(root.getRight());
	    return t.findNode(id);
	} else {
	    System.out.println("Error");
	    return null;
	}
    }
    public TNode findNode(String partialKey, int where){
	if(partialKey.length() == Globals.IDENTIFICATION_LEN){
	    return findNode(partialKey);
	} else if(root == null){
	    return null;
	}
	int n = partialKey.length();
	if(partialKey.compareTo(root.getId().substring(0, n)) < 0){
	    Tree t = new Tree(root.getLeft());
	    return t.findNode(partialKey, where);
	} else if(partialKey.compareTo(root.getId().substring(0, n)) > 0){
	    Tree t = new Tree(root.getRight());
	    return t.findNode(partialKey, where);
	} else {
	    TNode p = root;
	    if(where == 0){
		TNode q = p.getLeft();
		while(q != null){
		    if(q.getId().substring(0, n).equals(partialKey)){
			p = q;
			q = q.getLeft();
		    } else {
			q = q.getRight();
		    }
		}
	    } else {
		TNode q = p.getRight();
		while(q != null){
		    if(q.getId().substring(0, n).equals(partialKey)){
			p = q;
			q = q.getRight();
		    } else {
			q = q.getLeft();
		    }
		}
	    }
	    return p;
	}
    }
    public void printTree(int level){
	if(root != null){
	    Tree t = new Tree(root.getLeft());
	    t.printTree(level+1);
	    System.out.println(root + " in level " + level + " Height: " + root.getHeight());
	    t = new Tree(root.getRight());
	    t.printTree(level+1);
	}
    }
    public void breadthFirstRetrieve(String fileName){
	try {
	    RandomAccessFile f = new RandomAccessFile(fileName, "rw");
	    int nodes = (int) (f.length() / (Globals.IDENTIFICATION_LEN + Globals.INT_LEN));
	    TNode p = null;
	    byte[] identification = new byte[Globals.IDENTIFICATION_LEN];
	    String identificationString = Globals.STR_NULL;
	    for(int i = 0; i < nodes; i++){
		identificationString = Globals.STR_NULL;
		f.read(identification);
		for(int j = 0; j < identification.length; j++){
		    identificationString = identificationString + (char) identification[j];
		}
		p = new TNode(identificationString, f.readInt(), null, null, null);
		this.insertNode(p);
	    }
	    f.close();
	} catch (IOException e){
	    System.out.println("***Error: Unable to retrieve tree file " + fileName);
	}
    }
    public void breadthFirstSave(String fileName){
	try {
	    RandomAccessFile f = new RandomAccessFile(fileName, "rw");
	    f.setLength(0);
	    for(int i = 0; i < height(); i++)
		writeLevel(i, f);
	    f.close();
	} catch (IOException e){
	    System.out.println(e);
	}
    }
    public void writeLevel(int level, RandomAccessFile f){
	if(level == 0){
	    try {
		if(root != null){
		    f.write(root.getId().getBytes());
		    f.writeInt(root.getRecordNumber());
		}
	    } catch (IOException e){
		System.out.println(e);
	    }
	} else if(root != null){
	    Tree tree = new Tree(root.getLeft());
	    tree.writeLevel(level-1, f);
	    tree = new Tree(root.getRight());
	    tree.writeLevel(level-1, f);
	}
    }
    public int height(){
	if(root == null)return 0;
	Tree tree = new Tree(root.getLeft());
	Tree tree2 = new Tree(root.getRight());
	return Math.max(tree.height()+1, tree2.height()+1);
    }
    public void buildFromMessagesFile(int whatId){
	Record record = new Record();
	for(int recordNumber = 0; recordNumber < Globals.totalRecordsInMessageFile; recordNumber++){
	    record.readFromMessagesFile(recordNumber);
	    if(record.getData().charAt(Globals.FIRST_RECORD_MARKER_POS) ==
		Globals.FIRST_RECORD_MARKER){
		Message message = new Message();
		message.readFromMessagesFile(recordNumber);
		
		String key = Globals.STR_NULL;
		if(whatId == Globals.SENDER_ID)
		    key = message.getIdSenderFirst();
		else if(whatId == Globals.RECEIVER_ID)
		    key = message.getIdReceiverFirst();
		else
		    System.out.println("***invalid whatKey parameter in buildFromMessagesFile method");
		insertNode(new TNode(key, recordNumber));
	    }
	}
    }
    public void setParentsChildLink(TNode p, TNode q) {
	TNode parent = p.getParent();
	if (p.getId().compareTo(parent.getId()) > 0) {
	    parent.setRight(q);
	} else if (p.getId().compareTo(parent.getId()) < 0) {
	    parent.setLeft(q);
	} else {
	    System.out.println("Error in deleting root setting a parents child link");
	}
    }
    public TNode getRightMostNode(TNode curr){
	TNode it = curr;
	while(it.getRight() != null)it = it.getRight();
	return it;
    }
    public void deleteNode(TNode p) {
	if (p.isLeaf()) {
	    // Case 1
	    if (p == root) {
		root = null;
	    } else {
		setParentsChildLink(p, null);
	    }
	} else if (p.getLeft() == null || p.getRight() == null) {
	    // Cases 2 and 3
	    TNode q = null;
	    if (p.getLeft() != null) q = p.getLeft();
	    else if (p.getRight() != null) q = p.getRight();

	    if (p == root) root = q;
	    else setParentsChildLink(p, q);

	    q.setParent(p.getParent());
	} else {
	    TNode q = p.getLeft();
	    if(q.getRight() == null){
		//Case 4, 5a
		if(p == root){
		    root = q;
		} else {
		    setParentsChildLink(p, q);
		}

		q.setParent(p.getParent());
		q.setRight(p.getRight());
		q.getRight().setParent(q);
	    } else {
		//Case 5b, 5c
		q = getRightMostNode(q);
		q.getParent().setRight(q.getLeft());
		if(q.getLeft() != null){
		    q.getLeft().setParent(q.getParent());
		}

		if(p == root)
		    root = q;
		else
		    setParentsChildLink(p, q);

		q.setParent(p.getParent());
		q.setLeft(p.getLeft());
		p.getLeft().setParent(q);
		q.setRight(p.getRight());
		p.getRight().setParent(q);

	    }
	}

	p.setLeft(null);
	p.setRight(null);
	p.setParent(null);
	p = null;
    }
    public static void main(String args[]){
	Tree t = new Tree();
	//t.buildFromMessagesFile(Globals.SENDER_ID);
	//t.printTree();
	/*t.insertNode(new TNode(Utils.leftPad("12300", 5, '0'), 100));
	System.out.println(t.findNode("123", 0));
	System.out.println(t.height());
	Tree x = new Tree();
	TNode node = new TNode("19", 1);
	x.insertNode(new TNode("27", 1));
	x.insertNode(node);
	x.insertNode(new TNode("14", 1));
	x.insertNode(new TNode("15", 1));
	x.insertNode(new TNode("13", 1));
	x.insertNode(new TNode("16", 1));
	x.insertNode(new TNode("20", 1));
	x.insertNode(new TNode("22", 1));
	x.insertNode(new TNode("21", 1));
	x.insertNode(new TNode("30", 1));
	x.insertNode(new TNode("32", 1));
	x.printTree(1);
	System.out.println();
	x.deleteNode(node);
	x.printTree(1);*/
	/*TNode n;
	Tree test6 = new Tree();
	for(int i = 0; i < 500; i++){
	    boolean found = false;
	    while(!found){
		String x = ""+((char)(Math.random()*26+'a'))+((char)(Math.random()*26+'a'));
		if(test6.findNode(x) == null){
		    n = new TNode();
		    n.setRecordNumber(i);
		    n.setId(x);
		    test6.insertNode(n);
		    found = true;
		}
	    }
	}
	test6.printTree();*/
	Tree x = new Tree();
	x.insertNode(new TNode("00", 1));
	x.insertNode(new TNode("01", 1));
	x.insertNode(new TNode("02", 1));
	x.insertNode(new TNode("03", 1));
	x.insertNode(new TNode("04", 1));
	x.printTree(0);
    }
}
