package org.gwtxtend.example.client

import com.google.gwt.event.dom.client.KeyCodes
import com.google.gwt.user.client.rpc.AsyncCallback
import com.google.gwt.user.client.ui.Button
import com.google.gwt.user.client.ui.DialogBox
import com.google.gwt.user.client.ui.HTML
import com.google.gwt.user.client.ui.Label
import com.google.gwt.user.client.ui.RootPanel
import com.google.gwt.user.client.ui.TextBox
import com.google.gwt.user.client.ui.VerticalPanel
import org.gwtxtend.annotations.GwtModule

@GwtModule(moduleName="GwtXtendExample")
class GwtXtendExample {

    private TextBox nameField
    private Button sendButton
    private Label errorLabel
    private DialogBox dialogBox
    private Label textToServerLabel
    private HTML serverResponseLabel
    private Button closeButton

    //    private val GreetingServiceAsync greetingService = GreetingService.create
    override onModuleLoad() {
        createNeccessaryWidgets()
        focusOnNameField()
    }

    def private void createNeccessaryWidgets() {
        createNameField()
        createSendButton()
        createErrorLabel()
        createDialogBox()
    }
    
    def private void createNameField() {
        nameField = new TextBox() => [
            text = 'Insert your name'
            addKeyUpHandler[
                if(it.nativeKeyCode == KeyCodes.KEY_ENTER) {
                   sendNameToServer()       
                }
            ]
        ]
        RootPanel.get('nameFieldContainer').add(nameField)
    }

    def private void createSendButton() {
        sendButton = new Button("Send") => [
            addStyleName('sendButton')
            addClickHandler[
                sendNameToServer()
            ]
        ]
        RootPanel.get('sendButtonContainer').add(sendButton)
    }

    def private void createErrorLabel() {
        errorLabel = new Label()
        RootPanel.get('errorLabelContainer').add(errorLabel)
    }
    
    def private void createDialogBox() {
        textToServerLabel = new Label()
        serverResponseLabel = new HTML()
        
        closeButton = new Button('Close') => [
            element.id = 'closeButton'
            addClickHandler[
                dialogBox.hide()
                sendButton.enabled = true
                sendButton.focus = true
            ]   
        ]
        
        dialogBox = new DialogBox() => [
            text = 'Remote Procedure Call'
            animationEnabled = true
            
            widget = new VerticalPanel() => [
                horizontalAlignment = VerticalPanel.ALIGN_RIGHT
                addStyleName('dialogVPanel')
                
                add(new HTML('<b>Sending name to the server:</b>'))
                add(textToServerLabel)
                add(new HTML('<br><b>Server replies: </b>'))
                add(serverResponseLabel)
                add(closeButton)                
            ]
        ]
    }

    def private void focusOnNameField() {
        nameField.focus = true
        nameField.selectAll()
    }
    
    def private void sendNameToServer() {
        errorLabel.text = ''
        val name = nameField.text
//        if(name.notValid) {
//            errorLabel.text = 'Please enter at least four characters'
//        }

        sendButton.enabled = false
        textToServerLabel.text = name
        serverResponseLabel.text = ''
        
//        greetingService.greetServer(name, new GreetCallback(dialogBox, serverResponseLabel, closeButton))
    }

}

class GreetCallback implements AsyncCallback<String> {
    
    private static val SERVER_ERROR = 'An error occured while attempting to contact the server. ' +
        'Please check your network connection and try again.'
    
    private DialogBox dialogBox
    private HTML serverResponseLabel
    private Button closeButton
    
    new(DialogBox dialogBox, HTML serverResponseLabel, Button closeButton) {
        this.dialogBox = dialogBox
        this.serverResponseLabel = serverResponseLabel
        this.closeButton = closeButton
    }
    
    override onFailure(Throwable arg0) {
        dialogBox.text = 'Remote Procedure Call - Failure'
        serverResponseLabel.addStyleName('serverResponseLabelError')
        serverResponseLabel.HTML = SERVER_ERROR
        dialogBox.center()
        closeButton.focus = true
    }
    
    override onSuccess(String result) {
        dialogBox.text = 'Remote Procedure Call'
        serverResponseLabel.removeStyleName('serverResponseLabelError')
        serverResponseLabel.HTML = result
        dialogBox.center()
        closeButton.focus = true
    }
    
}
