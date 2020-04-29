package chatserver;


public class node {
	
	int frequency;
	char data;
	node left=null;
	node right=null;

	public node(int f,char ch) {
		frequency = f;
		data = ch;
	}
	
	public node(int f)
	{
		frequency = f;
		data = '$';
	}

}
