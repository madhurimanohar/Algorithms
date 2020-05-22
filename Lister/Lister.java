package recursion;

import java.io.File;

public class Lister {

	public File file;
	public boolean showHidden;
	
	public Lister(File file, boolean showHidden) {
		this.file = file;
		this.showHidden = showHidden;
	}
	
	public void list() {	
		listRecurse(file, " ");
	}

	private void listRecurse(File file, String indent) {
		File[] path;
		path = file.listFiles();
		
		for(int i = 0; i < path.length; i++) {

			if(!path[i].isDirectory()) {
				if(showHidden && ((path[i].getName()).substring(0, 1)) == ".") {
				}
				else {
					System.out.println(indent+path[i].getName());
					
				}
			}
			else if(path[i].isDirectory()){
				if(showHidden && ((path[i].getName()).charAt(1)) == '.') {
				}
				else {
					System.out.println(indent+path[i].getName() + ":");
					
					listRecurse(path[i], indent + "  ");
				}
			}
		}
	}
	
	public static void main(String[] args) {
		File f = new File("/Users/../.."); // add your personal directories here
		Lister l = new Lister(f,false);
		l.list();	
	}
}
