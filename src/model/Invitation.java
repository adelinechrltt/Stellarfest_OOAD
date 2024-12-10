package model;

public class Invitation {
	private String InvitationID;
	private String UserID;
	private String EventID;
	private String status;
	private String UserRole;
	
	public Invitation(String invitationID, String userID, String eventID, String status, String userRole) {
		super();
		InvitationID = invitationID;
		UserID = userID;
		EventID = eventID;
		this.status = status;
		UserRole = userRole;
	}

	public String getInvitationID() {
		return InvitationID;
	}

	public void setInvitationID(String invitationID) {
		InvitationID = invitationID;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getEventID() {
		return EventID;
	}

	public void setEventID(String eventID) {
		EventID = eventID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUserRole() {
		return UserRole;
	}

	public void setUserRole(String userRole) {
		UserRole = userRole;
	}
	
}
