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
    public void insertNode(TNode t){
	if(root == null){
	    root = t;
	} else if(t.getId().compareTo(root.getId()) < 0){
	    if(root.getLeft() == null){
		root.setLeft(t);
		t.setParent(root);
	    } else {
		Tree tr = new Tree(root.getLeft());
		tr.insertNode(t);
	    }
	} else if(t.getId().compareTo(root.getId()) > 0){
	    if(root.getRight() == null){
		root.setRight(t);
		t.setParent(root);
	    } else {
		Tree tr = new Tree(root.getRight());
		tr.insertNode(t);
	    }
	} else {
	    System.out.println("Attempting to insert an identification that already exists in tree");
	}
    }
    public void printTree() {
	if(root != null){
	    Tree t = new Tree(root.getLeft());
	    t.printTree();
	    System.out.println(root);
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
	    System.out.println("Id: " + root.getId() + " in level " + level);
	    t = new Tree(root.getRight());
	    t.printTree(level+1);
	}
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
    public static void main(String args[]){
	Tree t = new Tree();
	//t.buildFromMessagesFile(Globals.SENDER_ID);
	//t.printTree();
	t.insertNode(new TNode("034", 5));
	t.insertNode(new TNode("023", 76));
	t.insertNode(new TNode("164", 12));
	t.insertNode(new TNode("115", 30));
	t.insertNode(new TNode("137", 12));
	t.insertNode(new TNode("151", 12));
	t.insertNode(new TNode("128", 12));
	t.insertNode(new TNode("172", 12));
	t.insertNode(new TNode("004", 10));
	t.insertNode(new TNode("170", 12));
	t.insertNode(new TNode("120", 12));
	
	t.printTree(0);
	System.out.println(t.findNode("1", 1));
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
    }
}
