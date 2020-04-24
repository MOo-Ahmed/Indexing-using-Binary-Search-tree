
public class IndexRecord {
	int Key , Offset ;
	
	public IndexRecord(int key, int offset) {
		Key = key ;
		Offset = offset ;
	}
	
	@Override
	public String toString () {
		return Key + " - " + Offset ;
	}

	

}
