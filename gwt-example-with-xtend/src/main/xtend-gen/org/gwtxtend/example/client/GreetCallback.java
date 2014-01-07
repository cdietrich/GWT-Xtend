package org.gwtxtend.example.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;

@SuppressWarnings("all")
public class GreetCallback implements AsyncCallback<String> {
  private final static String SERVER_ERROR = ("An error occured while attempting to contact the server. " + 
    "Please check your network connection and try again.");
  
  private DialogBox dialogBox;
  
  private HTML serverResponseLabel;
  
  private Button closeButton;
  
  public GreetCallback(final DialogBox dialogBox, final HTML serverResponseLabel, final Button closeButton) {
    this.dialogBox = dialogBox;
    this.serverResponseLabel = serverResponseLabel;
    this.closeButton = closeButton;
  }
  
  public void onFailure(final Throwable arg0) {
    this.dialogBox.setText("Remote Procedure Call - Failure");
    this.serverResponseLabel.addStyleName("serverResponseLabelError");
    this.serverResponseLabel.setHTML(GreetCallback.SERVER_ERROR);
    this.dialogBox.center();
    this.closeButton.setFocus(true);
  }
  
  public void onSuccess(final String result) {
    this.dialogBox.setText("Remote Procedure Call");
    this.serverResponseLabel.removeStyleName("serverResponseLabelError");
    this.serverResponseLabel.setHTML(result);
    this.dialogBox.center();
    this.closeButton.setFocus(true);
  }
}
