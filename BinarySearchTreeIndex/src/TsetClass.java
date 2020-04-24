import java.io.IOException;


public class TestClass {
	int numberOfNodes = 0 ;
	
	public static void main(String[] args) throws IOException {
		TestClass d = new TestClass();
		d.start();
	}

	void start() throws IOException {
		BSTOperations handler = new BSTOperations();
		int numberOfRecords = 10 ;
		String fileName = "BST.bin" ;
		handler.CreateRecordsFile(fileName, numberOfRecords);
		handler.InsertNewRecordAtIndex(fileName,5 , 100);
		handler.InsertNewRecordAtIndex(fileName,12 , 100);
		handler.InsertNewRecordAtIndex(fileName,3 , 100);
		handler.InsertNewRecordAtIndex(fileName,9 , 100);
		handler.InsertNewRecordAtIndex(fileName,8 , 100);
		handler.InsertNewRecordAtIndex(fileName,2 , 100);
		handler.InsertNewRecordAtIndex(fileName,4 , 100);
		handler.InsertNewRecordAtIndex(fileName,10 , 100);
		handler.InsertNewRecordAtIndex(fileName,20 , 100);
		handler.DisplayIndexFileContent(fileName);
		System.out.println("_________________________________________________________________________________________________");
		handler.DisplayBinarySearchTreeInOrder(fileName);
		System.out.println("_________________________________________________________________________________________________");
		int search1 = handler.SearchRecordInIndex(fileName, 13);
		int search2 = handler.SearchRecordInIndex(fileName, 2);
		System.out.println(search1 + "\n" + search2);
	}

}
