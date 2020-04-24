import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class BSTOperations {
	// First Required Function - Done
		void CreateRecordsFile (String filename, int numberOfRecords) throws IOException {
			File tmp = new File(filename);
			tmp.delete(); // Safety step
			
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			file.seek(0);
			BSTNode node ;
			for(int i = 1 ; i <= numberOfRecords ; i++) {
				if(i != numberOfRecords) {
					node = new BSTNode(i, 0, 0, 0);
				}
				else	
				{
					// This is because the last node points to no empty place
					node = new BSTNode(-1, 0, 0, 0);
				}
				writeNode(node, file);
			}
			file.close();
		}

		// Second Required Function
		int InsertNewRecordAtIndex (String filename, int Key, int ByteOffset) throws IOException {
			// insert function should return -1 if there is no place to insert the record 
			// or the index where the new record is inserted if the record was inserted successfully.

			int index = -1 ;
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			file.seek(0); 
			//___________________________________________________________________________________________________________________________
			// read the guide node .. if you find that the next empty place is 1 , this means the tree has no root yet
			// else -> you should read the root and compare its key with your key , then traverse the tree until you 
			// reach the right place for insertion
			// when you reach that place , you should save your offset , then write the index in your parent node
			//___________________________________________________________________________________________________________________________
			BSTNode guideNode = readNode(file);
			if(guideNode.Key == 1) {
				// Empty tree , the node ur gonna insert is the root
				BSTNode newNode = new BSTNode(Key, ByteOffset, -1, -1);
				writeNode(newNode, file);
				file.seek(0);
				file.writeInt(2);
				file.close();
				index =  1 ;
			}
			else if (guideNode.Key == -1) {
				// in this case , the file can't take any more records.
				index = -1 ;
			}
			else {
				BSTNode newNode = new BSTNode(Key, ByteOffset, -1, -1);
				file.seek(guideNode.Key*16); // one record takes 16 bytes
				int next = file.readInt();
				file.seek(guideNode.Key*16);
				writeNode(newNode, file);
				file.seek(0);
				index = guideNode.Key ;
				file.writeInt(next);
				// Until now we updated the next empty place at the guide node 
				// Next step is to link the proper node to the new node
				file.seek(16);
				BSTNode temp = readNode(file);
				while(true) {
					if(Key > temp.Key) {
						if(temp.Right <= 0) { // no children
							int seek = (int) (file.getFilePointer() - 4) ;
							file.seek(seek);
							file.writeInt(index);
							break;
						}
						else {
							file.seek(temp.Right * 16);
							temp.equals(readNode(file));
							continue ;
						}
					}
					else if(Key < temp.Key) {
						if(temp.Left <= 0) { // no children
							int seek = (int) (file.getFilePointer() - 8) ;
							file.seek(seek);
							file.writeInt(index);
							break;
						}
						else {
							file.seek(temp.Left * 16);
							temp.equals(readNode(file));
							continue ;
						}
					}
				}
				file.close();
			}
			return index ;
		}

		// Third Required Function
		int SearchRecordInIndex (String filename, int Key) throws IOException {
			// This method should return the byte offset of the record or -1 if the record is not found
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			file.seek(16);
			int index = -1 ;
			BSTNode temp = readNode(file);
			while(true) {
				if(Key == temp.Key) {
					index = temp.Offset;
					break ;
				}
				else if(Key > temp.Key) {
					if(temp.Right <= 0) { // no children
						index = -1 ;
						break;
					}
					else {
						file.seek(temp.Right * 16);
						temp.equals(readNode(file));
						continue ;
					}
				}
				else if(Key < temp.Key) {
					if(temp.Left <= 0) { // no children
						index = -1 ;
						break;
					}
					else {
						file.seek(temp.Left * 16);
						temp.equals(readNode(file));
						continue ;
					}
				}
				
			}
			file.close();
			return index ;
		} 

		// Fourth Required Function
		void DisplayBinarySearchTreeInOrder (String FileName) throws IOException {
		// InOrder means (Left, Root, Right) 
			RandomAccessFile file = new RandomAccessFile(FileName, "rw");
			file.seek(16);
			BSTNode root = readNode(file);
			Traverse(root, file);


		}
		
		void Traverse(BSTNode node , RandomAccessFile file) throws IOException {
			if(node.Left == -1) {
				System.out.println(node);
				if(node.Right != -1) {
					file.seek(node.Right*16);
					BSTNode temp = readNode(file);
					Traverse(temp, file);
				}
				else	return ;
			}
			else {
				file.seek(node.Left*16);
				BSTNode temp = readNode(file);
				Traverse(temp, file);
				System.out.println(node);
				if(node.Right != -1) {
					file.seek(node.Right*16);
					BSTNode temp2 = readNode(file);
					Traverse(temp2, file);
				}
				else	return ;
			}	
		}
		
		// Fifth Required Function
		void DisplayIndexFileContent (String filename) throws IOException {
			// this method should display content of the file, each node in a line.
			RandomAccessFile file = new RandomAccessFile(filename, "rw");
			file.seek(0);
			int numberOfNodes = (int) (file.length() / 16) ;
			for(int i = 0 ; i < numberOfNodes ; i++) {
				System.out.println(readNode(file));
			}
			file.close();
		}

		//_____________________________________________________________________________________________________________

		IndexRecord readFromIndex(RandomAccessFile file) throws IOException {
			int key, offset;
			key = file.readInt();
			offset = file.readInt();
			IndexRecord idx = new IndexRecord(key, offset);
			return idx ;
		}
		
		BSTNode readNode(RandomAccessFile file) throws IOException {
			int key = file.readInt() , offset = file.readInt() , left = file.readInt() , right = file.readInt() ;
			BSTNode node = new BSTNode(key,offset,left,right);
			return node ;
		}
		
		void writeNode(BSTNode node , RandomAccessFile file) throws IOException {
			file.writeInt(node.Key);
			file.writeInt(node.Offset);
			file.writeInt(node.Left);
			file.writeInt(node.Right);
		}


}
