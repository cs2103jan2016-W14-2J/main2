import java.util.*;

	/*
	 *@author Pay Hao Jie
	 *@Description: This class sets and returns the attributes of a task. 
	 */
public class Task {
	private TASK_TYPE type;
	private TASK_STATUS status;
	private String name;
	private String tag;
	private String description;
	private boolean flag;
	private boolean isComplete;
	
	public Task(TASK_TYPE type, String name, String tag) {
		this.type = type; // parser-determined
		this.name = name; // must have
		this.tag = tag; // may be null
		this.isComplete = false;
		this.flag = false;
	}
	
	protected boolean getFlag() {
		return this.flag;
	}
	
	protected void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	protected String getTag() {
		return this.tag;
	}
	
	protected void setTag(String tag) {
		this.tag = tag;
	}
	
	protected String getDescription() {
		return this.description;
	}
	
	protected void setTaskDescription(String description) {
		this.description = description;
	}
	
	protected boolean getIsComplete() {
		return this.isComplete;
	}
	
	protected void setIsComplete(boolean isComplete) {
		this.isComplete = isComplete;
	}
}
