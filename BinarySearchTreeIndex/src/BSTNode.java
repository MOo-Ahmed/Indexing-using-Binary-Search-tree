
public class BSTNode {
	int Key , Offset , Left , Right ;
	
	public BSTNode(int key, int offset, int left, int right) {
		Key = key ;
		Offset = offset ;
		Right = right ;
		Left = left ;
	}
	
	public String toString () {
		return "<\t" + Key + "\t|\t" + Offset + "\t|\t" + Left + "\t|\t" + Right  + "\t>";
	}

	public void equals (BSTNode node) {
		Key = node.Key ;
		Offset = node.Offset ;
		Right = node.Right ;
		Left = node.Left ;
	}
	
}
