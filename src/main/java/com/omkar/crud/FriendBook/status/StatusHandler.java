package com.omkar.crud.FriendBook.status;

public class StatusHandler 
{

	private int id;
	private String status;
		
	public StatusHandler(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	
	public StatusHandler(String status) 
	{
		this.status = status;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "StatusHandler [id=" + id + ", status=" + status + "]";
	}
		
}
