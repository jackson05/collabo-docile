package message;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message{

	public void error(String string) {
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR,"Information",string));
		
	}

	public void errorFatal(String string) {
		
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_FATAL,"Information",string));
	}

	public void information(String string) {
		// TODO Auto-generated method stub
		FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO,"Information",string));
		
	}

	public void warnig(String string) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"Information", string));
		
	}

}
