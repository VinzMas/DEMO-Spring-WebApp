package com.crmwebapp.demo.controller.message;

public class Status implements Cloneable {

	public enum Operation {ADD, UPDATE, DELETE, FILTER}

	private Operation lastOperation = null;
	private Boolean status=null;
	private String msg="";
	
	
	public Status() {

	}

	public Status(Operation lastOperation, Boolean status) {
		this.lastOperation = lastOperation;
		this.status = status;
	}

	public void setSuccessfullAdd(boolean status) {
		this.lastOperation = Operation.ADD;
		this.status = status;
		this.msg = status ? "Successfull Add" : "Failure Add";
	}
	
	public void setSuccessfullUpdate(boolean status) {
		this.lastOperation = Operation.UPDATE;
		this.status = status; 
		this.msg = status ? "Successfull Update" : "Failure Update";
	}
	
	public void setSuccessfullDelete(boolean status) {
		this.lastOperation = Operation.DELETE;
		this.status = status; 
		this.msg = status ? "Successfull Delete" : "Failure Delete";
	}
	
	public void setSuccessfullFilter(boolean status) {
		this.lastOperation = Operation.FILTER;
		this.status = status; 
		this.msg = status ? "Successfull filtering" : "Failure filtering";
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String appendMsg(String msg) {
		String str = this.msg + ": " + msg;
		this.msg = str;
		return str;
	}

	public Operation getLastOperation() {
		return lastOperation;
	}

	public Boolean getStatus() {
		return status;
	}
	
	@Deprecated
	public void setLastOperation(Operation lastOperation) {
		this.lastOperation = lastOperation;
		this.msg += " " + lastOperation;
	}

	@Deprecated
	public void setStatus(boolean status) {
		this.msg += " " + (status ? "successfull" : "failure");
		this.status = status;
	}

	public void reset() {
		this.lastOperation = null;
		this.status = null;
		this.msg = "";
	}
	
	public boolean isActive() {
		return this.status != null;
	}
	
	//TODO: hashCode() e equals() non tengono conto di msg, da scrivere nell documentazione
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastOperation == null) ? 0 : lastOperation.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Status other = (Status) obj;
		if (lastOperation != other.lastOperation)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Status [lastOperation=" + lastOperation + ", status=" + status + ", msg=" + msg + "]";
	}
	
	@Override
	public Status clone() {
		Status cloned = new Status();
		cloned.lastOperation = this.lastOperation;
		cloned.status = this.status;
		cloned.msg = this.msg;
		
		return cloned;
	}
	
}
