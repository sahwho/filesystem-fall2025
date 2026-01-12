import java.time.LocalDateTime;

// Starter for File class

public class File {
	private Directory parent;
	private String filename;
	private LocalDateTime lastAccessed;
	private LocalDateTime lastModified;
	
	public File(String fileName, Directory parentReference) {
		
	}
	
	public String getFilename() {
		return filename;
	}
	
	public Directory getParent() {
		return parent;
	}
	
	public void setParent(Directory parentReference) {
		this.parent = parentReference;
	}
	
	public LocalDateTime getLastAccessed() {
		return lastAccessed;
	}
	
	public void setLastAccessed(LocalDateTime newLastAccessed) {
		this.lastAccessed = newLastAccessed;
	}
	
	public LocalDateTime getLastModified() {
		return lastModified;
	}
	
	public void setLastModified(LocalDateTime newLastModified) {
		this.lastModified = newLastModified;
	}
	
	//TODO: override toString()!
}
